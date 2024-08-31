package com.tenstep.tenstepconge.RestControllers;

import com.tenstep.tenstepconge.Services.IDemandeDeCongeService;
import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.EtatConge;
import com.tenstep.tenstepconge.dao.repositories.DocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("DemandeRestController")
public class DemandeRestController {
    @Autowired
    IDemandeDeCongeService iDemandeDeCongeService;
    DocumentRepository documentRepository;

    @PostMapping("/create/{id}")
    public ResponseEntity<DemandeDeConge> createDemandeDeConge(
            @RequestParam("motif") String motif,
            @RequestParam("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateDebut,
            @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFin,
            @RequestParam("files") MultipartFile[] files,
            @PathVariable String id) {

        DemandeDeConge demandeDeConge = DemandeDeConge.builder()
                .motif(motif)
                .dateDebut(dateDebut)
                .dateFin(dateFin)
                .build();

        DemandeDeConge createdDemande = iDemandeDeCongeService.createDemandeDeConge(demandeDeConge, id, files);
        return ResponseEntity.ok(createdDemande);
    }






    @PostMapping("/add")
    public ResponseEntity<?> addDemandeDeConge(@RequestBody DemandeDeConge demandeDeConge) {
        try {
            DemandeDeConge savedDemande = iDemandeDeCongeService.addDemandeDeConge(demandeDeConge);
            return new ResponseEntity<>(savedDemande, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestParam String status) {
        System.out.println("Received status: " + status);
        try {
            DemandeDeConge updatedDemande = iDemandeDeCongeService.updateDemandeDeCongeStatus(id, status);
            if (updatedDemande != null) {
                return ResponseEntity.ok(updatedDemande);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value: " + status);
        }
    }



    @GetMapping("/findAllWithUserDetails")
    public List<DemandeDeConge> findAllWithUserDetails() {
        return iDemandeDeCongeService.findAllDemandsWithUserDetails();
    }

}
