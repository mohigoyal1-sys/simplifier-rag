package com.simplifier.service;

import com.simplifier.model.ImportSourcesRequest;
import com.simplifier.model.ResearchDocument;
import com.simplifier.model.SearchRecord;
import com.simplifier.model.Source;
import com.simplifier.repository.impl.SearchRecordService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ResearchImportService {

    private final SearchRecordService searchRecordService;
    private final WebPageService webPageService;
    private final DocumentChunkService documentChunkService;

    public ResearchImportService(SearchRecordService searchRecordService, WebPageService webPageService, DocumentChunkService documentChunkService) {
        this.searchRecordService = searchRecordService;
        this.webPageService = webPageService;
        this.documentChunkService = documentChunkService;
    }

    public SearchRecord importSources(ImportSourcesRequest request) {
        List<Source> sourceList = searchRecordService.findSourcesBySearchId(request.searchId());

        for (Source source : sourceList) {
            if(!request.sourceIds().contains(source.getId())){
                continue;
            }
            source.setUserSelected(true);
            try {

                String content = webPageService.fetch(source.getUrl());

                ResearchDocument document = new ResearchDocument();

                document.setContent(content);
                document.setSource(source);

                searchRecordService.saveResearchDocument(document);

                documentChunkService.chunkDocument(document);

                source.setImported(true);
                source.setImportedDate(Instant.now());
                source.setError(null);

            } catch (Exception e) {
                source.setImported(false);
                source.setError(e.getMessage());
            }finally {
                searchRecordService.saveSource(source);
            }
        }
        return searchRecordService.findBySearchId(request.searchId());
    }
}