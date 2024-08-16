package com.tenstep.tenstepconge.dao.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "JourFeries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JourFeries {
    @Id
    private String id;
    private String nom;
    private LocalDate date;
}
