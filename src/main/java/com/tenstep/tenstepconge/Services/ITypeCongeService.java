package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.TypeConge;

import java.util.List;

public interface ITypeCongeService {
    TypeConge createTypeConge(TypeConge typeConge);
    TypeConge updateTypeConge(String id, TypeConge typeConge);
    TypeConge findById(String id);
    List<TypeConge> findAll();
    void deleteById(String id);
}
