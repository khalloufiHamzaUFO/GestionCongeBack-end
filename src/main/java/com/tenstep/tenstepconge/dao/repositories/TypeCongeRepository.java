package com.tenstep.tenstepconge.dao.repositories;

import com.tenstep.tenstepconge.dao.entities.TypeConge;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TypeCongeRepository extends MongoRepository<TypeConge, String> {
}
