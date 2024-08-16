package com.tenstep.tenstepconge.RestControllers;

import com.tenstep.tenstepconge.Services.ITypeCongeService;
import com.tenstep.tenstepconge.dao.entities.TypeConge;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("TypeCongeRestController")
public class TypeCongeController {
    @Autowired
    private ITypeCongeService typeCongeService;

    @PostMapping("/create")
    public ResponseEntity<TypeConge> createTypeConge(@RequestBody TypeConge typeConge) {
        TypeConge createdTypeConge = typeCongeService.createTypeConge(typeConge);
        return new ResponseEntity<>(createdTypeConge, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TypeConge> updateTypeConge(@PathVariable("id") String id, @RequestBody TypeConge typeConge) {
        TypeConge updatedTypeConge = typeCongeService.updateTypeConge(id, typeConge);
        return new ResponseEntity<>(updatedTypeConge, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TypeConge> getTypeCongeById(@PathVariable("id") String id) {
        TypeConge typeConge = typeCongeService.findById(id);
        return new ResponseEntity<>(typeConge, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TypeConge>> getAllTypeConge() {
        List<TypeConge> typeConges = typeCongeService.findAll();
        return new ResponseEntity<>(typeConges, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTypeConge(@PathVariable("id") String id) {
        typeCongeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
