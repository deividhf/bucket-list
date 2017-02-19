package com.bucketlist.infrastructure.spring.security.login.rest.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginResource {
	
	public LoginResource() {
	}

	@GetMapping
	public ResponseEntity<String> login() {
		return ResponseEntity.ok().body("Logged");
	}
}
