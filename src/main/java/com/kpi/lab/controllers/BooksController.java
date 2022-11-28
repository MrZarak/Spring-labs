package com.kpi.lab.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kpi.lab.entity.Book;
import com.kpi.lab.service.BookService;

@Controller
@RequestMapping("/books")
public class BooksController {
	@Autowired
	private BookService bookService;

	@GetMapping
	public String view(Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return "index";
	}

	@GetMapping("/admin")
	public String viewAdmin(Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return "admin";
	}

	@GetMapping("/by-author")
	public String viewAuthor(@RequestParam String authorName, Model model) {
		authorName = authorName.trim();
		List<Book> byName = bookService.findByAuthor(authorName);
		model.addAttribute("authorName", authorName);
		model.addAttribute("books", byName);
		return "by_name";
	}

	@GetMapping("/by-name")
	public String viewName(@RequestParam String name, Model model) {
		name = name.trim();
		List<Book> byName = bookService.findByName(name);
		model.addAttribute("name", name);
		model.addAttribute("books", byName);
		return "by_name";
	}

	@GetMapping("/by-keyword")
	public String viewKeyword(@RequestParam String keyword, Model model) {
		keyword = keyword.trim();
		List<Book> byName = bookService.findByKeywordIn(keyword);
		model.addAttribute("keyword", keyword);
		model.addAttribute("books", byName);
		return "by_name";
	}

	@PostMapping("/add")
	public String addBook(@RequestParam String name, @RequestParam String authorName, @RequestParam String keywordsNotSplited) {
		bookService.createBook(name, authorName, keywordsNotSplited);
		return "redirect:/books/admin";
	}

	@PostMapping("/edit")
	public String editBookName(@ModelAttribute("bookId") UUID bookId, @ModelAttribute("name") String name) {
		bookService.changeBookName(bookId, name);
		return "redirect:/books/admin";
	}

	@PostMapping("/delete")
	public String deleteBook(@ModelAttribute("bookId") UUID bookId) {
		bookService.deleteBook(bookId);
		return "redirect:/books/admin";
	}
}
