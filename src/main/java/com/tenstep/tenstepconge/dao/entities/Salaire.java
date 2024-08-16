package com.tenstep.tenstepconge.dao.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Salaire")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Salaire {
    @Id
    private String id;
    private double montant;
    private String devise;
    private String type; // Mensuel, Annuel, etc.

    @DBRef
    private User employe;
}
