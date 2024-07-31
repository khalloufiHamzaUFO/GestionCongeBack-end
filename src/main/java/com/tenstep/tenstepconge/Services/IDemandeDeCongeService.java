package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.EtatConge;
import com.tenstep.tenstepconge.dao.entities.Notification;

import java.time.LocalDate;
import java.util.List;

public interface IDemandeDeCongeService {
    DemandeDeConge createDemandeDeConge(DemandeDeConge demandeDeConge,String userId);

    DemandeDeConge editDemandeDeConge(DemandeDeConge demandeDeConge,String userId) ;
    DemandeDeConge editDemandeDeCongeResponsable(DemandeDeConge demandeDeConge) ;

    List<DemandeDeConge> findAllDemands();
    List<DemandeDeConge> findAllDemandsByUser(String userId);
    DemandeDeConge findById(String id);
    void deleteByID(String id);
    void delete(DemandeDeConge demandeDeConge) ;
}
