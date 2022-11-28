package com.kpi.lab.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "keywords")
public class Keyword {
	@Id
	@Column(name = "id", nullable = false, unique = true)
	private UUID id;
	@Column(name = "value", nullable = false, unique = true)
	private String value;
	@ManyToMany(targetEntity = Book.class, mappedBy = "keywords")
	@JsonIgnore
	private Set<Book> books = new HashSet<>();

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> book) {
		this.books = book;
	}

	public void addBook(Book book) {
		this.books.add(book);
		book.addKeyword(this);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
