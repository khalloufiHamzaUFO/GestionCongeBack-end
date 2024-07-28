package com.tenstep.tenstepconge.dao.repositories;

import com.tenstep.tenstepconge.dao.entities.Roles;
import com.tenstep.tenstepconge.dao.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserByCin(long cin);
    List<User> findUserByNomAndPrenom(String nom, String prenom);
    List<User> findUsersByRole(Roles role);

    User findByEmail(String email);

    boolean existsByEmail(String email);

    User findByVerificationToken(String verificationToken);
    List<User> findByLastLoginBefore(LocalDate date);

    User findUserById(String id);
}
