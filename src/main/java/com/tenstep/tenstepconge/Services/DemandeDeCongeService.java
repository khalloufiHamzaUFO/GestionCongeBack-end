package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.EmailNotif.EmailSenderService;
import com.tenstep.tenstepconge.EmailNotif.Model.MailStructure;
import com.tenstep.tenstepconge.dao.entities.*;
import com.tenstep.tenstepconge.dao.repositories.DemandeCongeRepository;
import com.tenstep.tenstepconge.dao.repositories.DocumentRepository;
import com.tenstep.tenstepconge.dao.repositories.UserRepository;
import com.tenstep.tenstepconge.errors.CannotBeEditedException;
import com.tenstep.tenstepconge.errors.PeriodOverlaps;
import com.tenstep.tenstepconge.errors.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class DemandeDeCongeService implements IDemandeDeCongeService{
    private DemandeCongeRepository demandeCongeRepository;
    private UserRepository userRepository;
    private NotificationService notificationService;
    private EmailSenderService emailSenderService;
    private DocumentService documentService;
    private DocumentRepository documentRepository;

    @Autowired
    private ISoldeCongeService soldeCongeService;
    @Transactional
    public DemandeDeConge createDemandeDeConge(DemandeDeConge demandeDeConge, String userId, MultipartFile[] files) {
        if (files != null) {
            for (MultipartFile file : files) {
                try {
                    Documents document = Documents.builder()
                            .documentContent(file.getBytes())
                            .fileName(file.getOriginalFilename())
                            .fileType(file.getContentType())
                            .build();
                    document = documentRepository.save(document);
                    if (demandeDeConge.getDocuments() == null) {
                        demandeDeConge.setDocuments(new HashSet<>());
                    }
                    demandeDeConge.getDocuments().add(document);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save file", e);
                }
            }
        }

        // Set default status
        demandeDeConge.setEtat(EtatConge.EN_ATTENTE);

        // Validate overlapping periods
        validateOverlap(demandeDeConge, userId);

        // Set ID and associate user
        demandeDeConge.setId(UUID.randomUUID().toString().split("-")[0]);

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        demandeDeConge.setUtilisateur(user); // Set user on demandeDeConge

        if (user.getDemandes() == null) {
            user.setDemandes(new ArrayList<>());
        }
        user.getDemandes().add(demandeDeConge);

        // Create notification
        Notification notification = notificationService.createNotification(demandeDeConge, EtatConge.EN_ATTENTE);
        if (user.getNotifications() == null) {
            user.setNotifications(new ArrayList<>());
        }
        user.getNotifications().add(notification);

        // Save user
        userRepository.save(user);

        if (demandeDeConge.getNotifications() == null) {
            demandeDeConge.setNotifications(new ArrayList<>());
        }
        demandeDeConge.getNotifications().add(notification);

        // Save demandeDeConge
        demandeDeConge = demandeCongeRepository.save(demandeDeConge);

        // Email sending logic
        List<User> responsables = userRepository.findUsersByRole(Roles.RESPONSABLE); // Assuming you have a method to find all responsible users
        for (User responsable : responsables) {
            MailStructure mailStructure = new MailStructure();
            mailStructure.setSubject("Nouvelle demande de congé");
            String message = "Une nouvelle demande de congé a été créée par " + demandeDeConge.getUtilisateur().getNom() + " " + demandeDeConge.getUtilisateur().getPrenom() + ".\n" +
                    "Motif : " + demandeDeConge.getMotif() + "\n" +
                    "Date de début : " + demandeDeConge.getDateDebut() + "\n" +
                    "Date de fin : " + demandeDeConge.getDateFin();
            mailStructure.setMessage(message);
            emailSenderService.sendEmail(responsable.getEmail(), mailStructure);
        }

        return demandeDeConge;
    }
    private void validateOverlap(DemandeDeConge demandeDeConge, String userId) {
        List<DemandeDeConge> demandeDeCongeList = demandeCongeRepository.findAllByUtilisateurId(userId);
        LocalDate dd = demandeDeConge.getDateDebut();
        LocalDate df = demandeDeConge.getDateFin();

        for (DemandeDeConge existingDemande : demandeDeCongeList) {
            LocalDate existingDd = existingDemande.getDateDebut();
            LocalDate existingDf = existingDemande.getDateFin();

            if (dd.isBefore(existingDf) && df.isAfter(existingDd)) {
                throw new PeriodOverlaps("The new demand overlaps with an existing demand period.");
            }
        }
    }

    @Override
    public DemandeDeConge addDemandeDeConge(DemandeDeConge demandeDeConge) {
        DemandeDeConge savedDemande = demandeCongeRepository.save(demandeDeConge);

        List<User> responsables = userRepository.findUsersByRole(Roles.RESPONSABLE);

        for (User responsable : responsables) {
            MailStructure mailStructure = new MailStructure();
            mailStructure.setSubject("Nouvelle demande de congé");
            String message = "Une nouvelle demande de congé a été créée par " + demandeDeConge.getUtilisateur().getNom() + " " + demandeDeConge.getUtilisateur().getPrenom() + ".\n" +
                    "Motif : " + demandeDeConge.getMotif() + "\n" +
                    "Date de début : " + demandeDeConge.getDateDebut() + "\n" +
                    "Date de fin : " + demandeDeConge.getDateFin();
            mailStructure.setMessage(message);
            emailSenderService.sendEmail(responsable.getEmail(), mailStructure);
        }

        return savedDemande;
    }


    @Override
    public DemandeDeConge editDemandeDeConge(DemandeDeConge demandeDeConge, String userId) {
        DemandeDeConge originalDemande = demandeCongeRepository.findById(demandeDeConge.getId())
                .orElseThrow(() -> new UserNotFoundException("Demand not found"));

        if (!originalDemande.getEtat().equals(EtatConge.EN_ATTENTE)) {
            throw new CannotBeEditedException("Only demands with state 'EN_ATTENTE' can be edited.");
        }

        List<DemandeDeConge> demandeDeCongeList = demandeCongeRepository.findAllByUtilisateurId(userId);

        LocalDate dd = demandeDeConge.getDateDebut();
        LocalDate df = demandeDeConge.getDateFin();

        for (DemandeDeConge existingDemande : demandeDeCongeList) {
            if (existingDemande.getId().equals(originalDemande.getId())) {
                continue;
            }

            LocalDate existingDd = existingDemande.getDateDebut();
            LocalDate existingDf = existingDemande.getDateFin();

            if ((dd.isEqual(existingDd) || dd.isAfter(existingDd)) && (dd.isEqual(existingDf) || dd.isBefore(existingDf)) ||
                    (df.isEqual(existingDd) || df.isAfter(existingDd)) && (df.isEqual(existingDf) || df.isBefore(existingDf)) ||
                    (dd.isBefore(existingDd) && df.isAfter(existingDf))) {
                throw new PeriodOverlaps("The edited demand overlaps with an existing demand period.");
            }
        }

        originalDemande.setMotif(demandeDeConge.getMotif());
        originalDemande.setDateDebut(dd);
        originalDemande.setDateFin(df);
        originalDemande.setEtat(demandeDeConge.getEtat());

        // Create notification
        Notification notification = notificationService.createNotification(originalDemande, demandeDeConge.getEtat());

        User user = userRepository.findUserById(userId);

        // Ensure user's notifications list is not null
        if (user.getNotifications() == null) {
            user.setNotifications(new ArrayList<>());
        }
        user.getNotifications().add(notification);

        userRepository.save(user);

        // Ensure originalDemande notifications list is not null
        if (originalDemande.getNotifications() == null) {
            originalDemande.setNotifications(new ArrayList<>());
        }
        originalDemande.getNotifications().add(notification);

        return demandeCongeRepository.save(originalDemande);
    }


    @Override
    public DemandeDeConge editDemandeDeCongeResponsable(DemandeDeConge demandeDeConge) {
        return null;
    }


    @Override
    public List<DemandeDeConge> findAllDemands() {
        return demandeCongeRepository.findAll();
    }

    @Override
    public List<DemandeDeConge> findAllDemandsByUser(String userId) {
        return demandeCongeRepository.findAllByUtilisateurId(userId);
    }

    @Override
    public DemandeDeConge findById(String id) {
        return demandeCongeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteByID(String id) {
        demandeCongeRepository.deleteById(id);
    }

    @Override
    public void delete(DemandeDeConge demandeDeConge) {
        demandeCongeRepository.delete(demandeDeConge);
    }

    @Override
    public DemandeDeConge updateDemandeDeCongeStatus(String id, String status) {
           try {
            EtatConge etatConge = EtatConge.valueOf(status);
            return demandeCongeRepository.findById(id)
                    .map(demande -> {
                        demande.setEtat(etatConge);
                        return demandeCongeRepository.save(demande);
                    })
                    .orElse(null);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }
    }

    @Override
    public List<DemandeDeConge> findDemandeDeCongeByPeriod(LocalDate dateDebutStart, LocalDate dateDebutEnd) {
        return null;
    }

    @Override
    public List<DemandeDeConge> findByDateDebutBetween(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public List<DemandeDeConge> findByDateFinBetween(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public List<DemandeDeConge> findByEtat(EtatConge etat) {
        return null;
    }

    @Override
    public List<DemandeDeConge> findByMotifContaining(String motif) {
        return null;
    }

    @Override
    public DemandeDeConge approuverDemande(String demandeId) {
        DemandeDeConge demande = demandeCongeRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

        demande.setEtat(EtatConge.APPROUVE);
        demandeCongeRepository.save(demande);

        // Mise à jour du solde de congé
        soldeCongeService.decrementerSolde(demande);

        return demande;
    }

    @Override
    public List<DemandeDeConge> findAllDemandsWithUserDetails() {
        List<DemandeDeConge> demandes = demandeCongeRepository.findAll();
        for (DemandeDeConge demande : demandes) {
            Optional<User> user = userRepository.findById(demande.getUtilisateur().getId());
            user.ifPresent(demande::setUtilisateur); // Set user details if available
        }
        return demandes;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Executes daily at midnight
    public void deleteOldPendingDemands() {
        LocalDate now = LocalDate.now();
        LocalDate thresholdDate = now.minusDays(7);

        List<DemandeDeConge> oldPendingDemands = demandeCongeRepository.findAllByEtatAndDateDebutBefore(EtatConge.EN_ATTENTE, thresholdDate);
        if (!oldPendingDemands.isEmpty()) {
            for (DemandeDeConge demande : oldPendingDemands) {
                log.info("Deleting old pending demand: {}", demande.getId());
                demandeCongeRepository.delete(demande);
            }
        }
    }

}