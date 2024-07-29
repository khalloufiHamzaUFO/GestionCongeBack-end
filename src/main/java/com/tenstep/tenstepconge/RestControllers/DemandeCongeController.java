package com.tenstep.tenstepconge.RestControllers;

import com.tenstep.tenstepconge.Services.IDemandeDeCongeService;
import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.EtatConge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("DemandeCongeController")
public class DemandeCongeController {
    private final IDemandeDeCongeService demandeDeCongeService;

    @Autowired
    public DemandeCongeController(IDemandeDeCongeService demandeDeCongeService) {
        this.demandeDeCongeService = demandeDeCongeService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<DemandeDeConge>> getAllDemandes() {
        List<DemandeDeConge> demandeDeConges = demandeDeCongeService.findAll();
        return new ResponseEntity<>(demandeDeConges, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<DemandeDeConge> getDemandeById(@PathVariable String id) {
        DemandeDeConge demandeDeConge = demandeDeCongeService.findById(id);
        if (demandeDeConge != null) {
            return new ResponseEntity<>(demandeDeConge, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addDemandeConge")
    public ResponseEntity<DemandeDeConge> addDemandeDeConge(@RequestBody DemandeDeConge demandeDeConge) {
        DemandeDeConge newDemande = demandeDeCongeService.createDemandeDeConge(demandeDeConge);
        return new ResponseEntity<>(newDemande, HttpStatus.CREATED);
    }

    @PutMapping("/editDemandeConge")
    public ResponseEntity<DemandeDeConge> editDemandeDeConge(@RequestBody DemandeDeConge demandeDeConge) {
        DemandeDeConge updatedDemande = demandeDeCongeService.editDemandeDeConge(demandeDeConge);
        return new ResponseEntity<>(updatedDemande, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteDemandeById(@PathVariable String id) {
        DemandeDeConge existingDemande = demandeDeCongeService.findById(id);
        if (existingDemande != null) {
            demandeDeCongeService.deleteByID(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteDemande")
    public ResponseEntity<Void> deleteDemande(@RequestBody DemandeDeConge demandeDeConge) {
        DemandeDeConge existingDemande = demandeDeCongeService.findById(demandeDeConge.getId());
        if (existingDemande != null) {
            demandeDeCongeService.delete(demandeDeConge);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByDateDebutBetween")
    public ResponseEntity<List<DemandeDeConge>> getByDateDebutBetween(
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        List<DemandeDeConge> demandes = demandeDeCongeService.findByDateDebutBetween(startDate, endDate);
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @GetMapping("/findByDateFinBetween")
    public ResponseEntity<List<DemandeDeConge>> getByDateFinBetween(
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        List<DemandeDeConge> demandes = demandeDeCongeService.findByDateFinBetween(startDate, endDate);
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @GetMapping("/findByEtat")
    public ResponseEntity<List<DemandeDeConge>> getByEtat(@RequestParam("etat") EtatConge etat) {
        List<DemandeDeConge> demandes = demandeDeCongeService.findByEtat(etat);
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @GetMapping("/findByMotifContaining")
    public ResponseEntity<List<DemandeDeConge>> getByMotifContaining(@RequestParam("motif") String motif) {
        List<DemandeDeConge> demandes = demandeDeCongeService.findByMotifContaining(motif);
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @GetMapping("/demandes-conge/period")
    public ResponseEntity<List<DemandeDeConge>> getDemandesDeCongeByPeriod(
            @RequestParam("dateDebutStart") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateDebutStart,
            @RequestParam("dateDebutEnd") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateDebutEnd) {
        List<DemandeDeConge> demandes = demandeDeCongeService.findByDateDebutBetween(dateDebutStart, dateDebutEnd);
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }



}