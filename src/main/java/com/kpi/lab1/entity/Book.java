package com.kpi.lab1.entity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Book {
	private UUID id;
	private String name;
	private String authorName;
	private Collection<Keyword> keywords;

	public Book(UUID id, String authorName, String name, Collection<Keyword> keywords) {
		this.id = id;
		this.authorName = authorName;
		this.name = name;
		this.keywords = List.copyOf(keywords);
	}

	public Book() {
		keywords = List.of();
	}

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

	public void setKeywords(Collection<Keyword> keywords) {
		this.keywords = keywords;
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
