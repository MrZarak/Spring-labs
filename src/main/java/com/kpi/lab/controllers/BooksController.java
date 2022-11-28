package com.kpi.lab.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kpi.lab.dto.BookInput;
import com.kpi.lab.dto.BookInputId;
import com.kpi.lab.entity.Book;
import com.kpi.lab.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/books")
public class BooksController {
	@Autowired
	private BookService bookService;

	@Operation(summary = "Get all books", description = "Get all books BIG DESCR")
	@ApiResponse(responseCode = "200", description = "OK")
	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	@Operation(summary = "Get all books in page", description = "Get all books in page. BIG DESCR")
	@ApiResponse(responseCode = "200", description = "OK")
	@GetMapping("/page")
	public Page<Book> getAllBooks(@PageableDefault Pageable pageable) {
		return bookService.getAllBooks(pageable);
	}

	@Operation(summary = "Get all books with author name in", description = "Get all books with author name in. BIG DESCR")
	@ApiResponse(responseCode = "200", description = "OK")
	@GetMapping("/with-author")
	public List<Book> viewAuthor(@RequestParam String authorName) {
		return bookService.findByAuthor(authorName.trim());
	}

	@Operation(summary = "Get all books by name", description = "Get all books by name. BIG DESCR")
	@ApiResponse(responseCode = "200", description = "OK")
	@GetMapping("/with-name")
	public List<Book> viewName(@RequestParam String name) {
		return bookService.findByName(name.trim());
	}

	@Operation(summary = "Get all books with keyword in", description = "Get all books with keyword in. BIG DESCR")
	@ApiResponse(responseCode = "200", description = "OK")
	@GetMapping("/with-keyword")
	public List<Book> viewKeyword(@RequestParam String keyword) {
		return bookService.findByKeywordIn(keyword.trim());
	}

	@Operation(summary = "Create book", description = "Create book BIG DESCR")
	@ApiResponse(responseCode = "200", description = "OK")
	@PostMapping
	public Book createBook(@RequestBody BookInput input) {
		return bookService.createBook(input);
	}

	@Operation(summary = "Edit book", description = "Edit book BIG DESCR")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "404", description = "Book not found")
	@PatchMapping
	public ResponseEntity<Book> editBook(@RequestBody BookInput input) {
		return ResponseEntity.of(bookService.editBook(input));
	}

	@Operation(summary = "Delete book", description = "Delete book BIG DESCR")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "404", description = "Book not found")
	@DeleteMapping
	public ResponseEntity<Book> deleteBook(@RequestBody BookInputId input) {
		return ResponseEntity.of(bookService.deleteBook(input.id()));
	}
}
