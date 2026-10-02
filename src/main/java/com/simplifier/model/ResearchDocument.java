package com.simplifier.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "research_documents")
public class ResearchDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private Instant createdDate = Instant.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;
}