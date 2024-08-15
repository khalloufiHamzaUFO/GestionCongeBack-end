package com.tenstep.tenstepconge.EmailNotif;

import com.tenstep.tenstepconge.EmailNotif.Model.MailStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendEmail(String toEmail, MailStructure mailStructure) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setSubject(mailStructure.getSubject());
        message.setText(mailStructure.getMessage()); // Set the email body
        message.setTo(toEmail);

        mailSender.send(message);
    }
}
