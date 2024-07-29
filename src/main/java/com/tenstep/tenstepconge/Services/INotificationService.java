package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.EtatConge;
import com.tenstep.tenstepconge.dao.entities.Notification;

import java.util.List;

public interface INotificationService {
    Notification createNotification(DemandeDeConge demandeDeConge, EtatConge etatConge);
    List<Notification> GetNotificationByUser(String s) ;
    Notification editNotification(Notification notification) ;
    List<Notification> findAll();
    Notification findById(String id);
    void deleteByID(String id);
    void delete(Notification notification) ;
}
