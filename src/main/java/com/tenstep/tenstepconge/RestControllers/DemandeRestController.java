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

    @PostMapping("/create/{id}")
    public ResponseEntity<DemandeDeConge> createDemandeDeConge(@RequestBody DemandeDeConge demandeDeConge, @PathVariable String id) {
        DemandeDeConge createdDemande = iDemandeDeCongeService.createDemandeDeConge(demandeDeConge, id);
        return ResponseEntity.ok(createdDemande);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<DemandeDeConge> editDemandeDeConge(@RequestBody DemandeDeConge demandeDeConge, @PathVariable String id) {
        DemandeDeConge editedDemande = iDemandeDeCongeService.editDemandeDeConge(demandeDeConge,id);
        return ResponseEntity.ok(editedDemande);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<DemandeDeConge>> findAllDemands() {
        List<DemandeDeConge> demandes = iDemandeDeCongeService.findAllDemands();
        return ResponseEntity.ok(demandes);
    }

    @GetMapping("/findByUser/{userId}")
    public ResponseEntity<List<DemandeDeConge>> findAllDemandsByUser(@PathVariable String userId) {
        List<DemandeDeConge> demandes = iDemandeDeCongeService.findAllDemandsByUser(userId);
        return ResponseEntity.ok(demandes);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<DemandeDeConge> findById(@PathVariable String id) {
        DemandeDeConge demande = iDemandeDeCongeService.findById(id);
        return ResponseEntity.ok(demande);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        iDemandeDeCongeService.deleteByID(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody DemandeDeConge demandeDeConge) {
        iDemandeDeCongeService.delete(demandeDeConge);
        return ResponseEntity.ok().build();
    }

}
