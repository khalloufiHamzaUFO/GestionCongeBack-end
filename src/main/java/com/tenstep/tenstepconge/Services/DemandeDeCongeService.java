package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;

import java.util.List;

public class DemandeDeCongeService implements IDemandeDeCongeService{
    private final DemandeDeCongeService demandeDeCongeService;

    public DemandeDeCongeService(DemandeDeCongeService demandeDeCongeService) {
        this.demandeDeCongeService = demandeDeCongeService;
    }

    @Override
    public DemandeDeConge createDemandeDeConge(DemandeDeConge demandeDeConge) {
        return null;
    }

    @Override
    public List<DemandeDeConge> addAllDemandeDeConge(List<DemandeDeConge> demandeDeConges) {
        return null;
    }

    @Override
    public DemandeDeConge editDemandeDeConge(DemandeDeConge demandeDeConge) {
        return null;
    }

    @Override
    public List<DemandeDeConge> findAll() {
        return null;
    }

    @Override
    public DemandeDeConge findById(String id) {
        return null;
    }

    @Override
    public void deleteByID(String id) {

    }

    @Override
    public void delete(DemandeDeConge demandeDeConge) {

    }
}
