package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.Documents;
import com.tenstep.tenstepconge.dao.repositories.DocumentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class DocumentService implements IDocumentService{
    @Autowired
    private DocumentRepository documentsRepository;
    @Override
    public Documents saveDocument(MultipartFile file) throws IOException {
        Documents document = Documents.builder()
                .documentContent(file.getBytes())
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .build();
        return documentsRepository.save(document);
    }

    @Override
    public Documents getDocumentById(String id) {
        Optional<Documents> document = documentsRepository.findById(id);
        return document.orElse(null);
    }

    @Override
    public void deleteDocument(String id) {
        documentsRepository.deleteById(id);

    }
}
