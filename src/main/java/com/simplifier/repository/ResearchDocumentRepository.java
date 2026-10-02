package com.simplifier.repository;

import com.simplifier.model.ResearchDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResearchDocumentRepository
        extends JpaRepository<ResearchDocument, String> {
}