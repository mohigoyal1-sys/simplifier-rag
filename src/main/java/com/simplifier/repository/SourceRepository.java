package com.simplifier.repository;

import com.simplifier.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SourceRepository extends JpaRepository<Source, String> {

    List<Source> findBySearchRecordResearchId(String searchRecordId);
}