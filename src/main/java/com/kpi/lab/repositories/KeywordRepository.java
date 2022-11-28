package com.kpi.lab.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kpi.lab.entity.Keyword;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, UUID> {
	Optional<Keyword> findByValue(String value);
}
