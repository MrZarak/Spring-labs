package com.kpi.lab.entity;

import java.util.Objects;
import java.util.UUID;

public class Keyword {
	private UUID id;
	private String value;

	public Keyword(UUID id, String value) {
		this.id = id;
		this.value = value;
	}

	public Keyword() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Keyword keyword = (Keyword) o;
		return Objects.equals(id, keyword.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return value;
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
