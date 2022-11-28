package com.kpi.lab.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kpi.lab.controllers.BookInput;
import com.kpi.lab.entity.Book;
import com.kpi.lab.entity.Keyword;
import com.kpi.lab.repositories.BookRepository;
import com.kpi.lab.repositories.KeywordRepository;

@Service
public class BookServiceImpl implements BookService {
	private final BookRepository bookRepository;
	private final KeywordRepository keywordRepository;

	public BookServiceImpl(BookRepository bookRepository, KeywordRepository keywordRepository) {
		this.bookRepository = bookRepository;
		this.keywordRepository = keywordRepository;
		Keyword keyword1 = keywordRepository.findByValue("Keyword1").orElse(null);
		Keyword keyword2 = keywordRepository.findByValue("Keyword2").orElse(null);
		Book book1 = new Book(UUID.randomUUID(), "Some1", "Book1", List.of(keyword1));
		Book book2 = new Book(UUID.randomUUID(), "Some2", "Book2", List.of(keyword2));
		Book book3 = new Book(UUID.randomUUID(), "Some3", "Book3", List.of());
		Book book4 = new Book(UUID.randomUUID(), "Some2", "Book4", List.of(keyword1, keyword2));
		bookRepository.addBook(book1);
		bookRepository.addBook(book2);
		bookRepository.addBook(book3);
		bookRepository.addBook(book4);
	}


	@Override
	public List<Book> getAllBooks() {
		return bookRepository.getAllBooks();
	}

	@Override
	public Page<Book> getAllBooks(Pageable pageable) {
		return bookRepository.getAllBooks(pageable);
	}

	@Override
	public List<Book> findByAuthor(String authorName) {
		return bookRepository.findByAuthor(authorName);
	}

	@Override
	public List<Book> findByName(String name) {
		return bookRepository.findByName(name);
	}

	@Override
	public List<Book> findByKeywordIn(String keyWord) {
		return keywordRepository.findByValue(keyWord)
				.map(bookRepository::findByKeywordIn)
				.orElse(List.of());
	}

	@Override
	public Book createBook(BookInput bookInput) {
		UUID bookId = bookInput.id() == null ? UUID.randomUUID() : bookInput.id();
		Collection<Keyword> keywords = getKeywords(bookInput.keywords());

		Book book = new Book(bookId, bookInput.name(), bookInput.authorName(), keywords);

		bookRepository.addBook(book);
		return book;
	}

	@Override
	public Optional<Book> editBook(BookInput bookInput) {
		return bookRepository
				.findById(bookInput.id())
				.map(book -> {
					if (bookInput.name() != null) {
						book.setName(bookInput.name());
					}
					if (bookInput.authorName() != null) {
						book.setAuthorName(bookInput.authorName());
					}
					if (bookInput.authorName() != null) {
						Collection<Keyword> keywords = getKeywords(bookInput.keywords());
						book.setKeywords(keywords);
					}
					return book;
				});
	}


	@Override
	public Optional<Book> deleteBook(UUID bookId) {
		return bookRepository.deleteBook(bookId);
	}

	private Collection<Keyword> getKeywords(Collection<String> keywords) {
		return keywords.stream()
				.map(String::trim)
				.map(keyRaw -> {
					Keyword keyword = keywordRepository.findByValue(keyRaw).orElse(null);
					if (keyword == null) {
						keyword = new Keyword(UUID.randomUUID(), keyRaw);
						keywordRepository.addKeyword(keyword);
					}
					return keyword;
				})
				.toList();
	}
}
