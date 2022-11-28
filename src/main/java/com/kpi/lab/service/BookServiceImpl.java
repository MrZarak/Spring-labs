package com.kpi.lab.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpi.lab.dto.BookInput;
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
		return bookRepository.findAll();
	}

	@Override
	public Page<Book> getAllBooks(Pageable pageable) {
		return bookRepository.findAll(pageable);
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
		return bookRepository.findByKeywordIn(keyWord);
	}

	@Override
	@Transactional
	public Book createBook(BookInput bookInput) {
		Set<Keyword> keywords = getKeywords(bookInput.keywords());
		Book book = new Book();
		book.setId(bookInput.id());
		book.setName(bookInput.name());
		book.setAuthorName(bookInput.authorName());
		book.setKeywords(keywords);
		return bookRepository.save(book);
	}

	@Override
	@Transactional
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
						Set<Keyword> keywords = getKeywords(bookInput.keywords());
						book.setKeywords(keywords);
					}

					bookRepository.save(book);
					return book;
				});
	}


	@Override
	@Transactional
	public Optional<Book> deleteBook(UUID bookId) {
		Optional<Book> byId = bookRepository.findById(bookId);
		byId.ifPresent(bookRepository::delete);
		return byId;
	}

	private Set<Keyword> getKeywords(Collection<String> keywords) {
		return keywords.stream()
				.map(String::trim)
				.map(keyRaw -> {
					Keyword keyword = keywordRepository.findByValue(keyRaw).orElse(null);
					if (keyword == null) {
						keyword = new Keyword();
						keyword.setId(UUID.randomUUID());
						keyword.setValue(keyRaw);
						keywordRepository.save(keyword);
					}
					return keyword;
				})
				.collect(Collectors.toSet());
	}
}
