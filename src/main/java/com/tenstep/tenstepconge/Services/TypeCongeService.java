package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.TypeConge;
import com.tenstep.tenstepconge.dao.repositories.TypeCongeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TypeCongeService implements ITypeCongeService {
    @Autowired
    private TypeCongeRepository typeCongeRepository;

    @Override
    public TypeConge createTypeConge(TypeConge typeConge) {
        return typeCongeRepository.save(typeConge);    }

    @Override
    public TypeConge updateTypeConge(String id, TypeConge typeConge) {
        if (typeCongeRepository.existsById(id)) {
            typeConge.setId(id);
            return typeCongeRepository.save(typeConge);
        } else {
            throw new RuntimeException("Type de congé non trouvé");
        }
    }

    @Override
    public TypeConge findById(String id) {
        return typeCongeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Type de congé non trouvé"));
    }

    @Override
    public List<TypeConge> findAll() {
        return typeCongeRepository.findAll();    }

    @Override
    public void deleteById(String id) {
        typeCongeRepository.deleteById(id);

    }
}
