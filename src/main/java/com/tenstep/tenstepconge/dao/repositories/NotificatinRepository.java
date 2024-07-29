package com.tenstep.tenstepconge.dao.repositories;

import com.tenstep.tenstepconge.dao.entities.Notification;
import com.tenstep.tenstepconge.dao.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificatinRepository  extends MongoRepository<Notification, String> {
    List<Notification> findAllByUtilisateurId(String s);
}
