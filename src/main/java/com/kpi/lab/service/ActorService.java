package com.kpi.lab.service;

import java.util.List;

import com.kpi.lab.entity.Actor;

public interface ActorService {
	List<Actor> getAllActors();

	Actor getGuest();

	Actor getAdmin();
}
