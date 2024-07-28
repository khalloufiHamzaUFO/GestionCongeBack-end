package com.tenstep.tenstepconge.dao.repositories;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DemandeCongeRepository  extends MongoRepository<DemandeDeConge, String> {
}
