package com.kpi.lab.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.kpi.lab.entity.Book;
import com.kpi.lab.entity.Keyword;

@Repository
public class BookRepositoryImpl implements BookRepository {
	private final List<Book> books = new ArrayList<>();

	@Override
	public List<Book> getAllBooks() {
		return List.copyOf(books);
	}

	@Override
	public Page<Book> getAllBooks(Pageable pageable) {
		List<Book> content = books.stream().skip(pageable.getOffset())
				.limit(pageable.getPageSize())
				.toList();
		return new PageImpl<>(content, pageable, books.size());
	}

	@Override
	public Optional<Book> findById(UUID bookId) {
		return books.stream()
				.filter(book -> Objects.equals(bookId, book.getId()))
				.findFirst();
	}

	@Override
	public List<Book> findByAuthor(String authorName) {
		return books.stream()
				.filter(book -> Objects.equals(authorName, book.getAuthorName()))
				.toList();
	}

	@Override
	public List<Book> findByName(String name) {
		return books.stream()
				.filter(book -> Objects.equals(name, book.getName()))
				.toList();
	}

	@Override
	public List<Book> findByKeywordIn(Keyword keyWord) {
		return books.stream()
				.filter(book -> book.getKeywords().stream().anyMatch(keyword -> Objects.equals(keyword, keyWord)))
				.toList();
	}

	@Override
	public Book addBook(Book book) {
		books.add(book);
		return book;
	}

	@Override
	public Optional<Book> deleteBook(UUID bookId) {
		Optional<Book> book = books.stream()
				.filter(it -> Objects.equals(bookId, it.getId()))
				.findFirst();

		book.ifPresent(books::remove);

		return book;
	}

	@Override
	public Optional<Book> changeBookName(UUID bookId, String name) {
		Optional<Book> book = books.stream()
				.filter(it -> Objects.equals(bookId, it.getId()))
				.findFirst();

		book.ifPresent(it -> it.setName(name));
		return book;
	}
}
