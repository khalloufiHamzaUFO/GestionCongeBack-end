package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.EtatConge;
import com.tenstep.tenstepconge.dao.repositories.DemandeCongeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DemandeDeCongeService implements IDemandeDeCongeService{
    private DemandeCongeRepository demandeCongeRepository;

    @Autowired
    public DemandeDeCongeService(DemandeCongeRepository demandeCongeRepository) {
        this.demandeCongeRepository = demandeCongeRepository;
    }


        @Override
        public DemandeDeConge createDemandeDeConge(DemandeDeConge demandeDeConge) {
            return demandeCongeRepository.save(demandeDeConge);
        }


//    @Override
//    public DemandeDeConge createDemandeDeConge(DemandeDeConge demandeDeConge) {
//        List<DemandeDeConge> existingDemandes = demandeCongeRepository.findByUtilisateurId(demandeDeConge.getUtilisateur().getId());
//        if (!existingDemandes.isEmpty()) {
//            throw new IllegalStateException("L'utilisateur a déjà une demande de congé en cours.");
//        }
//        return demandeCongeRepository.save(demandeDeConge);
//    }

    @Override
    public List<DemandeDeConge> addAllDemandeDeConge(List<DemandeDeConge> demandeDeConges) {
        return demandeCongeRepository.saveAll(demandeDeConges);

    }

    @Override
    public DemandeDeConge editDemandeDeConge(DemandeDeConge demandeDeConge) {
        return demandeCongeRepository.save(demandeDeConge);

    }

    @Override
    public List<DemandeDeConge> findAll() {
        return demandeCongeRepository.findAll();

    }

    @Override
    public DemandeDeConge findById(String id) {
        return demandeCongeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteByID(String id) {
        demandeCongeRepository.deleteById(id);

    }

    @Override
    public void delete(DemandeDeConge demandeDeConge) {
        demandeCongeRepository.delete(demandeDeConge);

    }

    @Override
    public List<DemandeDeConge> findDemandeDeCongeByPeriod(LocalDate dateDebutStart, LocalDate dateDebutEnd) {
            return demandeCongeRepository.findByDateDebutBetweenAndDateFinBetween(dateDebutStart, dateDebutEnd);
        }

    @Override
    public List<DemandeDeConge> findByDateDebutBetween(LocalDate startDate, LocalDate endDate) {
        return demandeCongeRepository.findByDateDebutBetween(startDate, endDate);
    }

    @Override
    public List<DemandeDeConge> findByDateFinBetween(LocalDate startDate, LocalDate endDate) {
        return demandeCongeRepository.findByDateFinBetween(startDate, endDate);
    }

    @Override
    public List<DemandeDeConge> findByEtat(EtatConge etat) {
        return demandeCongeRepository.findByEtat(etat);

    }

    @Override
    public List<DemandeDeConge> findByMotifContaining(String motif) {
        return demandeCongeRepository.findByMotifContaining(motif);
    }


}
