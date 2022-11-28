package com.kpi.lab.repositories;

import java.util.List;
import java.util.Optional;

import com.kpi.lab.entity.Keyword;

public interface KeywordRepository {
	List<Keyword> getAll();
	Optional<Keyword> findByValue(String value);
	Keyword addKeyword(Keyword keyword);
}
