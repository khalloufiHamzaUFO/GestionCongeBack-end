package com.tenstep.tenstepconge.Services.email;


import com.tenstep.tenstepconge.dao.entities.EmailDetails;

public interface  IEmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details);
}
