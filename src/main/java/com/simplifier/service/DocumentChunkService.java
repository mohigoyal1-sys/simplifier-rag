package com.simplifier.service;

import com.simplifier.model.DocumentChunk;
import com.simplifier.model.ResearchDocument;
import com.simplifier.repository.DocumentChunkRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentChunkService {

    private final DocumentChunkRepository documentChunkRepository;
    private final VectorStoreService vectorStoreService;

    private final TokenTextSplitter textSplitter = TokenTextSplitter.builder()
            .withChunkSize(800)
            .withMinChunkSizeChars(350)
            .withMinChunkLengthToEmbed(5)
            .withMaxNumChunks(10000)
            .build();

    public DocumentChunkService(
            DocumentChunkRepository documentChunkRepository, VectorStoreService vectorStoreService) {
        this.documentChunkRepository = documentChunkRepository;
        this.vectorStoreService = vectorStoreService;
    }

    public void chunkDocument(ResearchDocument researchDocument) {

        if (researchDocument.getContent() == null
                || researchDocument.getContent().isBlank()) {
            return;
        }

        Document document = new Document(researchDocument.getContent());

        List<Document> chunks = textSplitter.split(document);


        for (int i = 0; i < chunks.size(); i++) {

            Document chunk = chunks.get(i);

            DocumentChunk documentChunk = new DocumentChunk();

            documentChunk.setContent(chunk.getText());
            documentChunk.setChunkIndex(i);
            documentChunk.setDocument(researchDocument);

            documentChunkRepository.save(documentChunk);

            vectorStoreService.saveChunk(documentChunk);
        }
    }
}