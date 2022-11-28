package com.kpi.lab.service;

import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.kpi.lab.entity.Actor;

@Service
public class ActorServiceImpl implements ActorService {
	private final Actor guest;
	private final Actor admin;

	public ActorServiceImpl(ObjectProvider<Actor> provider) {
		guest = provider.getObject( "guest");
		admin = provider.getObject("admin");
	}

	public List<Actor> getAllActors() {
		return List.of(guest, admin);
	}

	@Override
	public Actor getGuest() {
		return guest;
	}

	@Override
	public Actor getAdmin() {
		return admin;
	}
}
