package com.simplifier.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "search_records")
public class SearchRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String researchId;

    private String topic;

    @OneToMany(
            mappedBy = "searchRecord",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Source> sources = new ArrayList<>();

    private Instant createdDate = Instant.now();
}