package com.simplifier.service;

import com.simplifier.model.SearchRecord;
import com.simplifier.model.Source;
import com.simplifier.repository.impl.SearchRecordService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class WebSearchService {

    private final RestClient restClient;

    private final SearchRecordService searchRecordService;

    public WebSearchService(RestClient.Builder restClientBuilder, SearchRecordService service,
                            @Value("${tavily.api-key}") String tavilyApiKey) {

        this.restClient = restClientBuilder
                .baseUrl("https://api.tavily.com")
                .defaultHeader("Authorization", "Bearer " + tavilyApiKey)
                .build();
        this.searchRecordService = service;
    }

    public SearchRecord search(String topic) {

        SearchRecord searchRecord = new SearchRecord();
        searchRecord.setTopic(topic);

        Map<String, Object> request = Map.of("query", topic, "search_depth", "basic", "max_results", 10, "include_answer", false);

        TavilyResponse response = restClient
                .post()
                .uri("/search")
                .body(request)
                .retrieve()
                .body(TavilyResponse.class);

        if (response != null && response.results() != null) {
           response.results.forEach(r->{
               Source source = new Source();
               source.setTitle(r.title);
               source.setUrl(r.url);
               source.setSearchRecord(searchRecord);
               searchRecord.getSources().add(source);
           });
        }

         return searchRecordService.save(searchRecord);
    }

    private record TavilyResponse(List<TavilyResult> results) {
    }

    private record TavilyResult(String title, String url, String content) {
    }

}