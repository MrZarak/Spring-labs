package com.kpi.lab.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kpi.lab.entity.Book;
import com.kpi.lab.entity.Keyword;

@Repository
public class BookRepositoryImpl implements BookRepository {
	private final NamedParameterJdbcTemplate jdbc;
	private final KeywordRepository keywordRepository;

	public BookRepositoryImpl(NamedParameterJdbcTemplate jdbc, KeywordRepository keywordRepository) {
		this.jdbc = jdbc;
		this.keywordRepository = keywordRepository;
	}

	@Override
	public List<Book> getAllBooks() {
		return jdbc.query("SELECT * FROM books", toDto());
	}

	@Override
	public Page<Book> getAllBooks(Pageable pageable) {
		int size = pageable.getPageSize();
		long offset = pageable.getOffset();

		List<Book> content = jdbc.query("SELECT * FROM books OFFSET :offset LIMIT :size", Map.of(
				"offset", offset,
				"size", size
		), toDto());

		int amount = jdbc.queryForObject("SELECT count(*) as amount FROM books", Map.of(), (rs, num) -> {
			Integer amountNullable = rs.getInt("amount");
			return amountNullable == null ? 0 : amountNullable;
		});

		return new PageImpl<>(content, pageable, amount);
	}

	@Override
	public Optional<Book> findById(UUID bookId) {
		List<Book> resultRaw = jdbc.query("SELECT * FROM books WHERE id = :bookId LIMIT 1", Map.of("bookId", bookId), toDto());
		return resultRaw.stream().findFirst();
	}

	@Override
	public List<Book> findByAuthor(String authorName) {
		return jdbc.query("SELECT * FROM books WHERE author_name = :authorName", Map.of("authorName", authorName), toDto());
	}

	@Override
	public List<Book> findByName(String name) {
		return jdbc.query("SELECT * FROM books WHERE name = :name", Map.of("name", name), toDto());
	}

	@Override
	public List<Book> findByKeywordIn(Keyword keyWord) {
		return jdbc.query("""
				SELECT b.*
				FROM books b
				         LEFT JOIN books_keywords bk ON b.id = bk.book_id
				         LEFT JOIN keywords k on bk.keyword_id = k.id
				WHERE k.id = :keywordId
				""", Map.of("keywordId", keyWord.getId()), toDto());
	}

	@Override
	@Transactional
	public Book saveBook(Book book) {
		if (book.getId() == null) {
			book.setId(UUID.randomUUID());
		}

		var params = Map.of(
				"id", book.getId(),
				"name", book.getName(),
				"authorName", book.getAuthorName()
		);

		jdbc.update("""
				INSERT INTO books (id, name, author_name) VALUES (:id, :name, :authorName)
				 ON CONFLICT (id) DO UPDATE SET name = :name, author_name = :authorName
				""", params);

		book.getKeywords().forEach(keyword -> {
			var paramsKeywords = Map.of(
					"bookId", book.getId(),
					"keyWordId", keyword.getId()
			);

			jdbc.update("""
					INSERT INTO books_keywords (book_id, keyword_id) VALUES (:bookId, :keyWordId)
					 ON CONFLICT DO NOTHING
					""", paramsKeywords);
		});

		return book;
	}

	@Override
	@Transactional
	public Optional<Book> deleteBook(UUID bookId) {
		var params = Map.of("id", bookId);

		Optional<Book> result = findById(bookId);
		jdbc.update("DELETE FROM books WHERE id = :id", params);

		return result;
	}


	private RowMapper<Book> toDto() {
		return (rs, rowNum) -> {
			UUID uuid = UUID.fromString(rs.getString("id"));
			List<Keyword> keywordsForBook = keywordRepository.getKeywordsForBook(uuid); // yes, I know about N+1 problem
			return new Book(uuid, rs.getString("author_name"), rs.getString("name"), keywordsForBook);
		};
	}
}
