package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.SoldeConge;

public interface ISoldeCongeService {
    void decrementerSolde(DemandeDeConge demandeDeConge);
    SoldeConge findSoldeByUser(String userId);
}
