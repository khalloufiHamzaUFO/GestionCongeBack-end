package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.SoldeConge;
import com.tenstep.tenstepconge.dao.entities.User;

public interface ISoldeCongeService {

    SoldeConge initializeSolde(User employe, int annee, int joursAlloues);
    SoldeConge updateSolde(SoldeConge soldeConge);
    SoldeConge getSoldeByEmploye(String employe);
    void decrementerSolde(DemandeDeConge demandeDeConge);
    SoldeConge findSoldeByUser(String userId);
}
