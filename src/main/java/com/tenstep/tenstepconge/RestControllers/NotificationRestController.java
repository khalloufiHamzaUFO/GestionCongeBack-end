package com.tenstep.tenstepconge.RestControllers;

import com.tenstep.tenstepconge.Services.INotificationService;
import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.Notification;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("NotificationRestController")
public class NotificationRestController {

    @Autowired
    INotificationService notificationService;


    @GetMapping("/findAll")
    public ResponseEntity<List<Notification>> findAllNotifs() {
        List<Notification> notifications = notificationService.findAll();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/findAllByUser")
    public ResponseEntity<List<Notification>> findAllNotifs(@RequestParam String userId) {
        List<Notification> notifications = notificationService.findAll();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/by-demande-conge/{demandeCongeId}")
    public ResponseEntity<List<Notification>> getNotificationsByDemandeConge(@PathVariable String demandeCongeId) {
        List<Notification> notifications = notificationService.getNotificationsByDemandeConge(demandeCongeId);
        return ResponseEntity.ok(notifications);
    }
}
