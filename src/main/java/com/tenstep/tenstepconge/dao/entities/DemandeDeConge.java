package com.tenstep.tenstepconge.dao.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "DemandeDeConge")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandeDeConge {
    @Id
    private String id;
    private String motif;
    private EtatConge etat;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @DBRef
    private User utilisateur;

    @DBRef
    private User responsable;

    @DBRef
    private List<Notification> notifications;
}
