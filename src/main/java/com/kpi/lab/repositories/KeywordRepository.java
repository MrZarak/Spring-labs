package com.kpi.lab.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.kpi.lab.entity.Keyword;

public interface KeywordRepository {
	Optional<Keyword> findByValue(String value);

	List<Keyword> getKeywordsForBook(UUID bookId);

	Keyword saveKeyword(Keyword keyword);
}
