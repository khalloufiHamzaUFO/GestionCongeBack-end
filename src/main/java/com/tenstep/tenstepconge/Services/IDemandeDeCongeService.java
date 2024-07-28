package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.Notification;

import java.util.List;

public interface IDemandeDeCongeService {
    DemandeDeConge createDemandeDeConge(DemandeDeConge demandeDeConge);
    List<DemandeDeConge> addAllDemandeDeConge(List<DemandeDeConge> demandeDeConges) ;
    DemandeDeConge editDemandeDeConge(DemandeDeConge demandeDeConge) ;
    List<DemandeDeConge> findAll();
    DemandeDeConge findById(String id);
    void deleteByID(String id);
    void delete(DemandeDeConge demandeDeConge) ;
}
