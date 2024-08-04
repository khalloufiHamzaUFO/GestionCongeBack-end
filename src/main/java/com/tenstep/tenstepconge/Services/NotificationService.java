package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.EtatConge;
import com.tenstep.tenstepconge.dao.entities.Notification;
import com.tenstep.tenstepconge.dao.entities.User;
import com.tenstep.tenstepconge.dao.repositories.DemandeCongeRepository;
import com.tenstep.tenstepconge.dao.repositories.NotificatinRepository;
import com.tenstep.tenstepconge.dao.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService implements INotificationService{
    private final NotificatinRepository notificatinRepository;
    private final DemandeCongeRepository demandeCongeRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificatinRepository notificatinRepository, DemandeCongeRepository demandeCongeRepository, UserRepository userRepository) {
        this.notificatinRepository = notificatinRepository;
        this.demandeCongeRepository = demandeCongeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Notification createNotification(DemandeDeConge demandeDeConge, EtatConge etatConge) {
        User user = demandeDeConge.getUtilisateur();
        System.out.println(user);
        Notification notification = new Notification();
        notification.setIdNotif(UUID.randomUUID().toString().split("-")[0]);
        notification.setUtilisateur(user);
        notification.setDate(LocalDate.now());
        notification.setMessage("HelloTesting");
        notification.setTitre(etatConge.name());
        notification.setDemandeConge(demandeDeConge);
        return notificatinRepository.save(notification);
    }

    @Override
    public Notification editNotification(Notification notification) {
        return notificatinRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationByUser(String uid) {
        return notificatinRepository.findAllByUtilisateurId(uid);
    }

    @Override
    public List<Notification> findAll() {
        return notificatinRepository.findAll();
    }

    @Override
    public Notification findById(String id) {
        return notificatinRepository.findById(id).get();
    }

    @Override
    public void deleteByID(String id) {
        notificatinRepository.deleteById(id);
    }

    @Override
    public void delete(Notification notification) {
        notificatinRepository.delete(notification);
    }
}
