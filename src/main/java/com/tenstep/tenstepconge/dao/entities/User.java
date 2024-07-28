package com.tenstep.tenstepconge.dao.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Document(collection = "UsersTen")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String id;;
    private String nom;
    private String prenom;
    private Long cin;
    private LocalDate dateDeNaissance;
    private Roles role;
    private String email;
    private String password;
    private Date updatedAt;
    private Date createdAt;
    private boolean enabled = false;
    private LocalDate lastLogin;
    private String verificationToken;

    @DBRef
    private List<DemandeDeConge> demandes;

    @DBRef
    private List<Notification> notifications;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
