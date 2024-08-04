package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.EtatConge;
import com.tenstep.tenstepconge.dao.entities.Notification;
import com.tenstep.tenstepconge.dao.entities.User;
import com.tenstep.tenstepconge.dao.repositories.DemandeCongeRepository;
import com.tenstep.tenstepconge.dao.repositories.UserRepository;
import com.tenstep.tenstepconge.errors.CannotBeEditedException;
import com.tenstep.tenstepconge.errors.PeriodOverlaps;
import com.tenstep.tenstepconge.errors.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DemandeDeCongeService implements IDemandeDeCongeService{
    private DemandeCongeRepository demandeCongeRepository;
    private UserRepository userRepository;
    private NotificationService notificationService;

    @Override
    public DemandeDeConge createDemandeDeConge(DemandeDeConge demandeDeConge, String userId) {
        List<DemandeDeConge> demandeDeCongeList = demandeCongeRepository.findAllByUtilisateurId(userId);
        System.out.println(demandeDeCongeList);
        // New demand period
        LocalDate dd = demandeDeConge.getDateDebut();
        LocalDate df = demandeDeConge.getDateFin();
        System.out.println(dd);
        System.out.println(df);
        // Check for overlapping periods
        for (DemandeDeConge existingDemande : demandeDeCongeList) {
            LocalDate existingDd = existingDemande.getDateDebut();
            LocalDate existingDf = existingDemande.getDateFin();

            if ((dd.isEqual(existingDd) || dd.isAfter(existingDd)) && (dd.isEqual(existingDf) || dd.isBefore(existingDf)) ||
                    (df.isEqual(existingDd) || df.isAfter(existingDd)) && (df.isEqual(existingDf) || df.isBefore(existingDf)) ||
                    (dd.isBefore(existingDd) && df.isAfter(existingDf))) {
                throw new PeriodOverlaps("The new demand overlaps with an existing demand period.");
            }
        }

        demandeDeConge.setId(UUID.randomUUID().toString().split("-")[0]);

        // Add the new demand to the user's demandes list
        User user = userRepository.findUserById(userId);

        // Ensure user's demandes list is not null
        if (user.getDemandes() == null) {
            user.setDemandes(new ArrayList<>());
        }
        user.getDemandes().add(demandeDeConge);
        demandeDeConge.setUtilisateur(user);

        // Create notification
        Notification notification = notificationService.createNotification(demandeDeConge, EtatConge.EN_ATTENTE);

        if (user.getNotifications() == null) {
            user.setNotifications(new ArrayList<>());
        }
        user.getNotifications().add(notification);

        userRepository.save(user);

        if (demandeDeConge.getNotifications() == null) {
            demandeDeConge.setNotifications(new ArrayList<>());
        }
        demandeDeConge.getNotifications().add(notification);
        demandeDeConge.setUtilisateur(user);

        return demandeCongeRepository.save(demandeDeConge);
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

}