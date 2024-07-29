package com.tenstep.tenstepconge.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "justificatif")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Justificatif {

    @Id
    private long idJustif;

    private DocumentType documentType;
    private byte[] documentContent;

    @JsonIgnore
    private DemandeDeConge demandeDeConge;
}
