package com.tenstep.tenstepconge.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private EtatConge etat;  // "EN ATTENTE", "APPROUVÉ", "REFUSÉ"
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @DBRef
    private User utilisateur;

    @DBRef
    private User responsable;

    @JsonIgnore
    @DBRef
    private List<Notification> notifications;
}
