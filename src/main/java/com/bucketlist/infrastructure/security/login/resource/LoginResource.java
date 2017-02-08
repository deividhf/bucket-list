package com.bucketlist.infrastructure.security.login.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bucketlist.infrastructure.security.login.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginResource {

	private LoginService loginService;
	
	public LoginResource(final LoginService loginService) {
		this.loginService = loginService;
	}

	@GetMapping
	public ResponseEntity<String> login(final Authentication authentication) {
		loginService.login((UserDetails)authentication.getPrincipal());
		return ResponseEntity.ok().body("Logged");
	}
}
