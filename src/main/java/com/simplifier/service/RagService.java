package com.simplifier.service;

import com.simplifier.model.AskRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public RagService(
            VectorStore vectorStore,
            ChatClient chatClient) {

        this.vectorStore = vectorStore;
        this.chatClient = chatClient;
    }

    public String ask(AskRequest askRequest) {

        SearchRequest request = SearchRequest.builder()
                .topK(5)
                .filterExpression(
                        "researchId == '" + askRequest.researchId() + "'"
                )
                .build();

        List<Document> documents =
                vectorStore.similaritySearch(request);

        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        String answer = chatClient.prompt()
                .system("""
                        You are a research assistant.

                        Answer the user's question using only
                        the provided research context.

                        If the answer is not present in the context,
                        say that the information is not available
                        in the imported research data.
                        """)
                .user("""
                        Research context:

                        %s

                        Question:
                        %s
                        """.formatted(context, askRequest.question()))
                .call()
                .content();

        return answer;
    }
}