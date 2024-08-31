package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.EtatConge;
import com.tenstep.tenstepconge.dao.entities.Notification;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface IDemandeDeCongeService {
    DemandeDeConge createDemandeDeConge(DemandeDeConge demandeDeConge, String userId, MultipartFile[] files);
    DemandeDeConge addDemandeDeConge(DemandeDeConge demandeDeConge);

    DemandeDeConge editDemandeDeConge(DemandeDeConge demandeDeConge,String userId) ;
    DemandeDeConge editDemandeDeCongeResponsable(DemandeDeConge demandeDeConge) ;

    List<DemandeDeConge> findAllDemands();
    List<DemandeDeConge> findAllDemandsByUser(String userId);
    DemandeDeConge findById(String id);
    void deleteByID(String id);
    void delete(DemandeDeConge demandeDeConge) ;

    DemandeDeConge updateDemandeDeCongeStatus(String id, String status);
    List<DemandeDeConge> findDemandeDeCongeByPeriod(LocalDate dateDebutStart, LocalDate dateDebutEnd);
    List<DemandeDeConge> findByDateDebutBetween(LocalDate startDate, LocalDate endDate);
    List<DemandeDeConge> findByDateFinBetween(LocalDate startDate, LocalDate endDate);
    List<DemandeDeConge> findByEtat(EtatConge etat);
    List<DemandeDeConge> findByMotifContaining(String motif);


    DemandeDeConge approuverDemande(String demandeId);


    // Add methods to include user details
    List<DemandeDeConge> findAllDemandsWithUserDetails();
}
