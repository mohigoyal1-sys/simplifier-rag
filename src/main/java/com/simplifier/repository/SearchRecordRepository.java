package com.simplifier.repository;

import com.simplifier.model.SearchRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRecordRepository extends JpaRepository<SearchRecord, String> {
}