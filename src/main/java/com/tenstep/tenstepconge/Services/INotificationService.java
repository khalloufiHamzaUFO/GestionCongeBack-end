package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.Notification;

import java.util.List;

public interface INotificationService {
    Notification createNotification(Notification notification);
    List<Notification> addAllNotif(List<Notification> notificationList) ;
    Notification editNotification(Notification notification) ;
    List<Notification> findAll();
    Notification findById(String id);
    void deleteByID(String id);
    void delete(Notification notification) ;
}
