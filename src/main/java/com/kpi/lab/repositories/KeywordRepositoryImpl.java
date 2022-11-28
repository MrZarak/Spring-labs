package com.kpi.lab.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kpi.lab.entity.Keyword;

@Repository
public class KeywordRepositoryImpl implements KeywordRepository {
	private final NamedParameterJdbcTemplate jdbc;

	public KeywordRepositoryImpl(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Optional<Keyword> findByValue(String value) {
		List<Keyword> resultRaw = jdbc.query("SELECT * FROM keywords WHERE value = :value", Map.of("value", value), toDto());
		return resultRaw.stream().findFirst();
	}

	@Override
	public List<Keyword> getKeywordsForBook(UUID bookId) {
		return jdbc.query("""
				SELECT k.*
				 FROM keywords k
				 INNER JOIN books_keywords bk ON k.id = bk.keyword_id AND book_id = :bookId
				""", Map.of("bookId", bookId), toDto());
	}

	@Override
	@Transactional
	public Keyword saveKeyword(Keyword keyword) {
		if (keyword.getId() == null) {
			keyword.setId(UUID.randomUUID());
		}

		var params = Map.of(
				"id", keyword.getId(),
				"value", keyword.getValue()
		);

		jdbc.update("""
				INSERT INTO keywords (id, value) VALUES (:id, :value)
				 ON CONFLICT (id) DO UPDATE SET value = :value
				""", params);

		return keyword;
	}

	private RowMapper<Keyword> toDto() {
		return (rs, rowNum) -> {
			UUID uuid = UUID.fromString(rs.getString("id"));
			return new Keyword(uuid, rs.getString("value"));
		};
	}
}
