package com.kpi.lab.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.kpi.lab.entity.Book;

public interface BookService {
	List<Book> getAllBooks();

	List<Book> findByAuthor(String authorName);

	List<Book> findByName(String name);

	List<Book> findByKeywordIn(String keyWord);

	Book createBook(String name, String authorName, String keywordsNotSplited);

	Optional<Book> deleteBook(UUID bookId);

	Optional<Book> changeBookName(UUID bookId, String name);

}
