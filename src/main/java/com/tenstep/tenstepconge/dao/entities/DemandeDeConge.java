package com.tenstep.tenstepconge.dao.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Document(collection = "DemandeDeConge")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandeDeConge{
    @Id
    private String id;
    private String motif;   
    private EtatConge etat;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate dateDebut;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate dateFin;

    @DBRef
   // @JsonIgnore
    private User utilisateur;

    @DBRef
    private List<Notification> notifications;

    @DBRef
    private TypeConge typeConge;


    @DBRef
    private Set<Documents> documents = new HashSet<>();
}