package com.simplifier.service;

import com.simplifier.model.DocumentChunk;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VectorStoreService {

    private final VectorStore vectorStore;

    public VectorStoreService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void saveChunk(DocumentChunk chunk) {
        String researchId = chunk.getDocument()
                        .getSource()
                        .getSearchRecord()
                        .getResearchId();

        String sourceId = chunk.getDocument()
                        .getSource()
                        .getId();

        String url = chunk.getDocument()
                        .getSource()
                        .getUrl();

        Document document = new Document(chunk.getContent(), Map.of(
                        "researchId", researchId,
                        "sourceId", sourceId,
                        "chunkId", chunk.getId(),
                        "url", url
                ));

        vectorStore.add(List.of(document));
    }
}