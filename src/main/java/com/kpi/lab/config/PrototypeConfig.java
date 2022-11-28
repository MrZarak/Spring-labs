package com.kpi.lab.config;

import java.util.UUID;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.kpi.lab.entity.Actor;
@Configuration
public class PrototypeConfig {

	@Bean
	@Scope(BeanDefinition.SCOPE_PROTOTYPE)
	public Actor createActor(String role) {
		return new Actor(UUID.randomUUID(), role);
	}
}
