package com.kpi.lab1.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.kpi.lab1.entity.Book;
import com.kpi.lab1.entity.Keyword;

public interface BookRepository {
	List<Book> getAllBooks();
	List<Book> findByAuthor(String authorName);

	List<Book> findByName(String name);

	List<Book> findByKeywordIn(Keyword keyWord);

	Book addBook(Book book);

	Optional<Book> deleteBook(UUID bookId);

	Optional<Book> changeBookName(UUID bookId, String name);
}
