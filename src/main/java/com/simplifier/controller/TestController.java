package com.simplifier.controller;

import com.simplifier.service.VectorService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ChatClient chatClient;
    private final VectorService vectorService;

    public TestController(ChatClient.Builder chatClientBuilder, VectorService vectorService) {
        this.chatClient = chatClientBuilder.build();
        this.vectorService = vectorService;
    }

    // for testing connection b/w ollama and application
    @GetMapping("chat")
    public String chat(@RequestParam String question) {

        ChatClient.CallResponseSpec callResponseSpec =  chatClient
                .prompt()
                .user(question)
                .call();

        return callResponseSpec.content();
    }

    @GetMapping("search")
    public List<Document> search(@RequestParam String researchId, @RequestParam String question) {
        return vectorService.search(researchId, question);
    }
}