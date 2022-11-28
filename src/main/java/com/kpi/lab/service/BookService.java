package com.kpi.lab.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kpi.lab.controllers.BookInput;
import com.kpi.lab.entity.Book;

public interface BookService {
	List<Book> getAllBooks();

	Page<Book> getAllBooks(Pageable pageable);

	List<Book> findByAuthor(String authorName);

	List<Book> findByName(String name);

	List<Book> findByKeywordIn(String keyWord);

	Book createBook(BookInput input);

	Optional<Book> editBook(BookInput input);

	Optional<Book> deleteBook(UUID bookId);

}
