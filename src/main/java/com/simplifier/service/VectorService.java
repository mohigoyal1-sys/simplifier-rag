package com.simplifier.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VectorService {

    private final VectorStore vectorStore;

    public VectorService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public List<Document> search(String researchId, String question) {

        SearchRequest request = SearchRequest.builder()
                .query(question)
                .topK(5)
                .similarityThreshold(0.5)
                .filterExpression(
                        "researchId == '" + researchId + "'"
                )
                .build();

        return vectorStore.similaritySearch(request);
    }
}