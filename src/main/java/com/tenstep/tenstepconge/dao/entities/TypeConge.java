package com.tenstep.tenstepconge.dao.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "TypeConge")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeConge {
    @Id
    private String id;

    private String name;
    private String description;
    private int joursAlloues;
    private Boolean isPaid;
    private List<DemandeDeConge> demandeDeConges;
}
