package com.simplifier.controller;

import com.simplifier.model.AskRequest;
import com.simplifier.model.ImportSourcesRequest;
import com.simplifier.model.SearchRecord;
import com.simplifier.model.SearchRequest;
import com.simplifier.service.RagService;
import com.simplifier.service.ResearchImportService;
import com.simplifier.service.WebSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/research")
public class ResearchController {

    private final ResearchImportService researchImportService;
    private final WebSearchService  webSearchService;
    private final RagService ragService;


    public ResearchController(ResearchImportService researchImportService, WebSearchService webSearchService, RagService ragService) {
        this.researchImportService = researchImportService;
        this.webSearchService = webSearchService;
        this.ragService = ragService;
    }

    @PostMapping("search")
    public ResponseEntity<SearchRecord> searchTopic(@RequestBody SearchRequest request){
        SearchRecord searchRecord = webSearchService.search(request.topic());
        return new ResponseEntity<>(searchRecord, HttpStatus.OK);
    }

    @PostMapping("import")
    public ResponseEntity<SearchRecord> importSources(@RequestBody ImportSourcesRequest request){
        SearchRecord searchRecord = researchImportService.importSources(request);
        return new ResponseEntity<>(searchRecord, HttpStatus.OK);
    }

    @PostMapping("ask")
    public ResponseEntity<String> ask(@RequestBody AskRequest request) {
        return new ResponseEntity<>(ragService.ask(request),HttpStatus.OK);
    }

}
