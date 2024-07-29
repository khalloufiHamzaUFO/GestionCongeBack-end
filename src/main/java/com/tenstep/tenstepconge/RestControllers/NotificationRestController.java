package com.tenstep.tenstepconge.RestControllers;

import com.tenstep.tenstepconge.Services.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("NotificationRestController")
public class NotificationRestController {
    INotificationService notificationService;
}
