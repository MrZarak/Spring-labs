package com.kpi.lab.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kpi.lab.entity.Book;
import com.kpi.lab.entity.Keyword;

public interface BookRepository {
	List<Book> getAllBooks();

	Page<Book> getAllBooks(Pageable pageable);

	Optional<Book> findById(UUID bookId);

	List<Book> findByAuthor(String authorName);

	List<Book> findByName(String name);

	List<Book> findByKeywordIn(Keyword keyWord);

	Book saveBook(Book book);

	Optional<Book> deleteBook(UUID bookId);
}
