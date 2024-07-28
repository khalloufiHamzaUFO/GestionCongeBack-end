package com.tenstep.tenstepconge.dao.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "Notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    private String idNotif;
    private String titre;
    private String message;
    private LocalDate date;

    @DBRef
    private User utilisateur;

    @DBRef
    private DemandeDeConge demandeConge;
}
