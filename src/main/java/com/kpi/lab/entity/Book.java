package com.kpi.lab.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "books")
@NamedQuery(name = "Book.findByAuthor", query = "SELECT b FROM Book b WHERE b.authorName = ?1")
public class Book {
	@Id
	@Column(name = "id", nullable = false, unique = true)
	private UUID id;
	@Column(name = "name", nullable = false)

	private String name;
	@Column(name = "author_name", nullable = false)
	private String authorName;
	@ManyToMany(targetEntity = Keyword.class, cascade = CascadeType.ALL)
	@JoinTable(name = "books_keywords", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "keyword_id"))
	private Set<Keyword> keywords = new HashSet<>();

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public void setKeywords(Set<Keyword> keywords) {
		this.keywords = keywords;
	}

	public void addKeyword(Keyword keyword) {
		this.keywords.add(keyword);
		keyword.getBooks().add(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Keyword> getKeywords() {
		return keywords;
	}
}
