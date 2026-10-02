package com.simplifier.repository.impl;

import com.simplifier.model.ResearchDocument;
import com.simplifier.model.SearchRecord;
import com.simplifier.model.Source;
import com.simplifier.repository.ResearchDocumentRepository;
import com.simplifier.repository.SearchRecordRepository;
import com.simplifier.repository.SourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchRecordService {

    private final SearchRecordRepository searchRecordRepository;

    private final SourceRepository sourceRepository;

    private final ResearchDocumentRepository researchDocumentRepository;

    public SearchRecordService(SearchRecordRepository searchRecordRepository, SourceRepository sourceRepository, ResearchDocumentRepository researchDocumentRepository) {
        this.searchRecordRepository = searchRecordRepository;
        this.sourceRepository = sourceRepository;
        this.researchDocumentRepository = researchDocumentRepository;
    }

    public SearchRecord save(SearchRecord searchRecord) {
        return searchRecordRepository.save(searchRecord);
    }

    public List<SearchRecord> findAll() {
        return searchRecordRepository.findAll();
    }

    public SearchRecord findBySearchId(String searchId) {
        return searchRecordRepository.findById(searchId).orElse(null);
    }

    public List<Source> findSourcesBySearchId(String searchId) {
        return sourceRepository.findBySearchRecordResearchId(searchId);
    }

    public Source saveSource(Source source) {
        return sourceRepository.save(source);
    }

    public void saveResearchDocument(ResearchDocument researchDocument) {
        researchDocumentRepository.save(researchDocument);
    }


}