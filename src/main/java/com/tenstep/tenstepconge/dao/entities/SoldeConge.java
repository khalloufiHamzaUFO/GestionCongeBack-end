    package com.tenstep.tenstepconge.dao.entities;

    import lombok.*;
    import org.springframework.data.annotation.Id;
    import org.springframework.data.mongodb.core.mapping.DBRef;
    import org.springframework.data.mongodb.core.mapping.Document;

    @Document(collection = "SoldeConge")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class SoldeConge {
        @Id
        private String id;
        private int joursRestants;
        private int annee;

        @DBRef
        private User user;
    }
