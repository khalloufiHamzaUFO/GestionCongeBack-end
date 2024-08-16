package com.tenstep.tenstepconge.RestControllers;

import com.tenstep.tenstepconge.Services.ISoldeCongeService;
import com.tenstep.tenstepconge.Services.ITypeCongeService;
import com.tenstep.tenstepconge.dao.entities.DemandeDeConge;
import com.tenstep.tenstepconge.dao.entities.SoldeConge;
import com.tenstep.tenstepconge.dao.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("SoldeCongeRestController")
public class SoldeCongeController {
    @Autowired
    private ISoldeCongeService soldeCongeService;

    @PostMapping("/initialize")
    public SoldeConge initializeSolde(@RequestBody SoldeConge soldeConge) {
        // Make sure the User is properly set in the SoldeConge
        return soldeCongeService.initializeSolde(soldeConge.getUser(), soldeConge.getAnnee(), soldeConge.getJoursRestants());
    }

    @PutMapping("/update")
    public SoldeConge updateSolde(@RequestBody SoldeConge soldeConge) {
        return soldeCongeService.updateSolde(soldeConge);
    }

    @GetMapping("/{userId}")
    public SoldeConge getSoldeByEmploye(@PathVariable String userId) {
        return soldeCongeService.findSoldeByUser(userId);
    }



}
