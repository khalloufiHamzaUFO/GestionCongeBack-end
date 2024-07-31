package com.tenstep.tenstepconge.dao.repositories;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.EtatConge;
import com.tenstep.tenstepconge.dao.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface DemandeCongeRepository  extends MongoRepository<DemandeDeConge, String> {

    List<DemandeDeConge> findAllByUtilisateurId(String id);
    List<DemandeDeConge> findByUtilisateurId(String utilisateurId);
    List<DemandeDeConge> findByDateDebutBetween(LocalDate startDate, LocalDate endDate);
    List<DemandeDeConge> findByDateFinBetween(LocalDate startDate, LocalDate endDate);
    List<DemandeDeConge> findByEtat(EtatConge etat);

    List<DemandeDeConge> findByMotifContaining(String motif);
    List<DemandeDeConge> findByDateDebutBetweenAndDateFinBetween(LocalDate dateDebutStart, LocalDate dateDebutEnd);

}
