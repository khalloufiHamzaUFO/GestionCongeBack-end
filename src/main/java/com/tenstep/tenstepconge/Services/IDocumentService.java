package com.tenstep.tenstepconge.Services;

import com.tenstep.tenstepconge.dao.entities.Documents;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IDocumentService {
    Documents saveDocument(MultipartFile file) throws IOException;
    Documents getDocumentById(String id);
    void deleteDocument(String id);
}
