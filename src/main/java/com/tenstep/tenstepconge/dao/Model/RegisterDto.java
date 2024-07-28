package com.tenstep.tenstepconge.dao.Model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Data
public class RegisterDto {
    private String nom;
    private String prenom;
    private long cin;
    private LocalDate dateNaissance ;

    private String email;
    private String password;
}
