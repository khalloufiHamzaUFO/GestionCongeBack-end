package com.tenstep.tenstepconge.dao.repositories;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.SoldeConge;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SoldeCongeRepository  extends MongoRepository<SoldeConge, String> {
    SoldeConge findByUserId(String userId);

}
