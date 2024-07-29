package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.EtatConge;
import com.tenstep.tenstepconge.dao.entities.Notification;

import java.util.List;

public interface IDemandeDeCongeService {
    DemandeDeConge createDemandeDeConge(DemandeDeConge demandeDeConge, String userId);
    List<DemandeDeConge> addAllDemandeDeConge(List<DemandeDeConge> demandeDeConges) ;
    DemandeDeConge editDemandeDeConge(DemandeDeConge demandeDeConge) ;
    DemandeDeConge ChangeState(String s, EtatConge etatConge) ;

    List<DemandeDeConge> findAll();
    DemandeDeConge findById(String id);
    void deleteByID(String id);
    void delete(DemandeDeConge demandeDeConge) ;

    List<DemandeDeConge> findAllByUtilisateurId(String id);

}
