package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.EtatConge;
import com.tenstep.tenstepconge.dao.entities.Notification;
import com.tenstep.tenstepconge.dao.entities.User;
import com.tenstep.tenstepconge.dao.repositories.DemandeCongeRepository;
import com.tenstep.tenstepconge.dao.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DemandeDeCongeService implements IDemandeDeCongeService{
    private final DemandeCongeRepository demandeCongeRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public DemandeDeCongeService(DemandeCongeRepository demandeCongeRepository, NotificationService notificationService, UserRepository userRepository) {
        this.demandeCongeRepository = demandeCongeRepository;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    @Override
    public DemandeDeConge createDemandeDeConge(DemandeDeConge demandeDeConge, String userId) {
        demandeDeConge.setId(UUID.randomUUID().toString().split("-")[0]);
        Notification n = notificationService.createNotification(demandeDeConge, EtatConge.EN_ATTENTE);
        User user = userRepository.findUserById(userId);

        if (user.getDemandes() == null) {
            user.setDemandes(new ArrayList<>());
        }
        user.getDemandes().add(demandeDeConge);

        if (user.getNotifications() == null) {
            user.setNotifications(new ArrayList<>());
        }
        user.getNotifications().add(n);

        userRepository.save(user);

        if (demandeDeConge.getNotifications() == null) {
            demandeDeConge.setNotifications(new ArrayList<>());
        }
        demandeDeConge.getNotifications().add(n);

        return demandeCongeRepository.save(demandeDeConge);
    }

    @Override
    public List<DemandeDeConge> addAllDemandeDeConge(List<DemandeDeConge> demandeDeConges) {
        return null;
    }

    @Override
    public DemandeDeConge editDemandeDeConge(DemandeDeConge demandeDeConge) {
        return null;
    }

    @Override
    public DemandeDeConge ChangeState(String s, EtatConge etatConge) {
        DemandeDeConge demandeDeConge = demandeCongeRepository.findById(s).orElse(null);
        if (demandeDeConge == null) {
            System.out.println("Request not found with id: " + s);
            return null;
        }
        demandeDeConge.setEtat(etatConge);

        if (etatConge == EtatConge.EN_ATTENTE) {
            System.out.println("The request is in pending state.");
        } else if (etatConge == EtatConge.APPROUVE) {
            System.out.println("The request has been approved.");
        } else if (etatConge == EtatConge.REFUSE) {
            System.out.println("The request has been refused.");
        }

        notificationService.createNotification(demandeDeConge,etatConge);
        demandeDeConge.getNotifications().add(notificationService.createNotification(demandeDeConge,EtatConge.REFUSE));

        demandeCongeRepository.save(demandeDeConge);
        return demandeDeConge;
    }

    @Override
    public List<DemandeDeConge> findAll() {
        return demandeCongeRepository.findAll();
    }

    @Override
    public DemandeDeConge findById(String id) {
        return demandeCongeRepository.findById(id).get();
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
    public List<DemandeDeConge> findAllByUtilisateurId(String id) {
        return demandeCongeRepository.findAllByUtilisateurId(id);
    }


}
