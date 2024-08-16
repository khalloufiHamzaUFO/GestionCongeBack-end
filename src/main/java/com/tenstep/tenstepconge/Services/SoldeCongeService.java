package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.SoldeConge;
import com.tenstep.tenstepconge.dao.repositories.SoldeCongeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SoldeCongeService implements ISoldeCongeService{


    @Autowired
    private SoldeCongeRepository soldeCongeRepository;

    @Override
    public void decrementerSolde(DemandeDeConge demandeDeConge) {
        SoldeConge solde = demandeDeConge.getUtilisateur().getSoldeConge();
        int joursDemandes = (int) java.time.temporal.ChronoUnit.DAYS.between(demandeDeConge.getDateDebut(), demandeDeConge.getDateFin()) + 1;

        if (solde.getJoursRestants() >= joursDemandes) {
            solde.setJoursRestants(solde.getJoursRestants() - joursDemandes);
            soldeCongeRepository.save(solde);
        } else {
            throw new RuntimeException("Solde insuffisant");
        }
    }

    @Override
    public SoldeConge findSoldeByUser(String userId) {
        return soldeCongeRepository.findByUserId(userId);    }
}
