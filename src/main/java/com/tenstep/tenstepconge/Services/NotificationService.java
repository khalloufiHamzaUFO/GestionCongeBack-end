package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.Notification;
import com.tenstep.tenstepconge.dao.repositories.NotificatinRepository;
import com.tenstep.tenstepconge.dao.repositories.UserRepository;

import java.util.List;

public class NotificationService implements INotificationService{
    private final NotificatinRepository notificatinRepository;
    private final DemandeDeCongeService demandeDeCongeService;
    private final NotificatinRepository notificatinRepository;

    @Override
    public Notification createNotification(Notification notification) {
        return null;
    }

    @Override
    public List<Notification> addAllNotif(List<Notification> notificationList) {
        return null;
    }

    @Override
    public Notification editNotification(Notification notification) {
        return null;
    }

    @Override
    public List<Notification> findAll() {
        return null;
    }

    @Override
    public Notification findById(String id) {
        return null;
    }

    @Override
    public void deleteByID(String id) {

    }

    @Override
    public void delete(Notification notification) {

    }
}
