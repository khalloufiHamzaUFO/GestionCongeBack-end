package com.tenstep.tenstepconge.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Documents {
    @Id
    private String idDocuments;

    private byte[] documentContent;
    private String fileName;
    private String fileType;

    @DBRef
    @JsonIgnore
    private DemandeDeConge demandeDeConge;
}

