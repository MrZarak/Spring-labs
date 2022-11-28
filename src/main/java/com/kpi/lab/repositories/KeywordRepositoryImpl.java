package com.kpi.lab.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.kpi.lab.entity.Keyword;

@Repository
public class KeywordRepositoryImpl implements KeywordRepository {
	private final List<Keyword> keywords = new ArrayList<>();

	public KeywordRepositoryImpl() {
		keywords.add(new Keyword(UUID.randomUUID(), "Keyword1"));
		keywords.add(new Keyword(UUID.randomUUID(), "Keyword2"));
		keywords.add(new Keyword(UUID.randomUUID(), "Keyword3"));
	}

	@Override
	public List<Keyword> getAll() {
		return List.copyOf(keywords);
	}

	@Override
	public Optional<Keyword> findByValue(String value) {
		return keywords.stream()
				.filter(keyword -> Objects.equals(value, keyword.getValue()))
				.findFirst();
	}

	@Override
	public Keyword addKeyword(Keyword keyword) {
		keywords.add(keyword);
		return keyword;
	}
}
