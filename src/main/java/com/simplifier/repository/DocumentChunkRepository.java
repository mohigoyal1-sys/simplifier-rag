package com.simplifier.repository;

import com.simplifier.model.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentChunkRepository
        extends JpaRepository<DocumentChunk, String> {

    List<DocumentChunk> findByDocumentId(String documentId);
}