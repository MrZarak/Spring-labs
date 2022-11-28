package com.kpi.lab.entity;

import java.util.UUID;

public class Actor {
	private UUID id;
	private String role;

	public Actor(UUID id, String role) {
		this.id = id;
		this.role = role;
	}

	public Actor() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
