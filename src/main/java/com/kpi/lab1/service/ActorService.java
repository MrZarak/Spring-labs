package com.kpi.lab1.service;

import java.util.List;

import com.kpi.lab1.entity.Actor;

public interface ActorService {
	List<Actor> getAllActors();

	Actor getGuest();

	Actor getAdmin();
}
