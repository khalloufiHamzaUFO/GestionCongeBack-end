package com.tenstep.tenstepconge.RestControllers;

import com.tenstep.tenstepconge.Services.IDemandeDeCongeService;
import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.EtatConge;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("DemandeRestController")
public class DemandeRestController {
    @Autowired
    IDemandeDeCongeService iDemandeDeCongeService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<DemandeDeConge> createDemandeDeConge(@RequestBody DemandeDeConge demandeDeConge, @PathVariable String userId) {
        DemandeDeConge createdDemande = iDemandeDeCongeService.createDemandeDeConge(demandeDeConge, userId);
        return ResponseEntity.ok(createdDemande);
    }
    @PostMapping("/addAll")
    public ResponseEntity<List<DemandeDeConge>> addAllDemandeDeConge(@RequestBody List<DemandeDeConge> demandeDeConges) {
        List<DemandeDeConge> addedDemandes = iDemandeDeCongeService.addAllDemandeDeConge(demandeDeConges);
        return ResponseEntity.ok(addedDemandes);
    }

    @PutMapping("/edit")
    public ResponseEntity<DemandeDeConge> editDemandeDeConge(@RequestBody DemandeDeConge demandeDeConge) {
        DemandeDeConge editedDemande = iDemandeDeCongeService.editDemandeDeConge(demandeDeConge);
        return ResponseEntity.ok(editedDemande);
    }

    @PutMapping("/changeState")
    public ResponseEntity<DemandeDeConge> changeState(@RequestParam String id, @RequestParam EtatConge etat) {
        DemandeDeConge updatedDemande = iDemandeDeCongeService.ChangeState(id, etat);
        return ResponseEntity.ok(updatedDemande);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<DemandeDeConge>> findAll() {
        List<DemandeDeConge> demandes = iDemandeDeCongeService.findAll();
        return ResponseEntity.ok(demandes);
    }

    @GetMapping("/findById")
    public ResponseEntity<DemandeDeConge> findById(@RequestParam String id) {
        DemandeDeConge demande = iDemandeDeCongeService.findById(id);
        return ResponseEntity.ok(demande);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteById(@RequestParam String id) {
        iDemandeDeCongeService.deleteByID(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody DemandeDeConge demandeDeConge) {
        iDemandeDeCongeService.delete(demandeDeConge);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findAllByUtilisateurId")
    public ResponseEntity<List<DemandeDeConge>> findAllByUtilisateurId(@RequestParam String id) {
        List<DemandeDeConge> demandes = iDemandeDeCongeService.findAllByUtilisateurId(id);
        return ResponseEntity.ok(demandes);
    }
}
