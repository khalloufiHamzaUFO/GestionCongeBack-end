package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.EtatConge;

import java.time.LocalDate;
import java.util.List;

public interface IDemandeDeCongeService {
    DemandeDeConge createDemandeDeConge(DemandeDeConge demandeDeConge);
    List<DemandeDeConge> addAllDemandeDeConge(List<DemandeDeConge> demandeDeConges) ;
    DemandeDeConge editDemandeDeConge(DemandeDeConge demandeDeConge) ;
    List<DemandeDeConge> findAll();
    DemandeDeConge findById(String id);
    void deleteByID(String id);
    void delete(DemandeDeConge demandeDeConge) ;

    List<DemandeDeConge> findDemandeDeCongeByPeriod(LocalDate dateDebutStart, LocalDate dateDebutEnd);

    List<DemandeDeConge> findByDateDebutBetween(LocalDate startDate, LocalDate endDate);
    List<DemandeDeConge> findByDateFinBetween(LocalDate startDate, LocalDate endDate);
    List<DemandeDeConge> findByEtat(EtatConge etat);
    List<DemandeDeConge> findByMotifContaining(String motif);


}
