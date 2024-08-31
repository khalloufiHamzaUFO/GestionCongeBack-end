package com.tenstep.tenstepconge.RestControllers;

import com.tenstep.tenstepconge.Services.IDocumentService;
import com.tenstep.tenstepconge.dao.entities.Documents;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("DocumentRestController")
public class DocumentRestController {
    @Autowired
    private IDocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Documents> uploadDocument(@RequestParam("file") MultipartFile file) {
        try {
            Documents savedDocument = documentService.saveDocument(file);
            return new ResponseEntity<>(savedDocument, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documents> getDocumentById(@PathVariable String id) {
        Documents document = documentService.getDocumentById(id);
        if (document != null) {
            return new ResponseEntity<>(document, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String id) {
        Documents document = documentService.getDocumentById(id);
        if (document != null) {
            documentService.deleteDocument(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
