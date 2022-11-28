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
		Collection<Keyword> keywords = getKeywords(bookInput.keywords());
		Book book = new Book(bookInput.id(), bookInput.name(), bookInput.authorName(), keywords);
		return bookRepository.saveBook(book);
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

					bookRepository.saveBook(book);
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
						keywordRepository.saveKeyword(keyword);
					}
					return keyword;
				})
				.toList();
	}
}
