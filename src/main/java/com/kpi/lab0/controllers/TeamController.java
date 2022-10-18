package com.kpi.lab0.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
public class TeamController {
	@GetMapping("/all")
	public String getAllInfo() {
		return "All team: [me] :)";
	}

	@GetMapping("/owner")
	public String getOwnerOfTeam() {
		return "Just only me :)";
	}
}
