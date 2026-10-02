package com.simplifier.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "sources")
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @Column(length = 2000)
    private String url;

    private boolean userSelected;

    private boolean imported;

    @Column(length = 1000)
    private String error;

    private Instant createdDate = Instant.now();

    private Instant importedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "search_record_id")
    @JsonIgnore
    private SearchRecord searchRecord;
}
