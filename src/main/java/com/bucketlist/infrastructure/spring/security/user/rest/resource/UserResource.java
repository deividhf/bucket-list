package com.bucketlist.infrastructure.spring.security.user.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bucketlist.infrastructure.spring.security.user.model.User;
import com.bucketlist.infrastructure.spring.security.user.model.UserRepository;
import com.bucketlist.infrastructure.spring.security.user.model.UserService;
import com.bucketlist.infrastructure.spring.security.user.rest.converter.ChangeUserConverter;
import com.bucketlist.infrastructure.spring.security.user.rest.converter.UserConverter;
import com.bucketlist.infrastructure.spring.security.user.rest.dto.UserDTO;

@RestController
@RequestMapping("/users")
public class UserResource {

	private UserRepository userRepository;
	private UserConverter userConverter;
	private UserService userService;
	private ChangeUserConverter changeUserConverter;

	public UserResource(final UserRepository userRepository, final UserService userService, 
			final UserConverter converter, final ChangeUserConverter changeUserConverter) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.userConverter = converter;
		this.changeUserConverter = changeUserConverter;
	}

	@PostMapping
	public ResponseEntity<UserDTO> create(@Valid @RequestBody final UserDTO user) {
		final User userSaved = userService.save(userConverter.toEntity(user, User.Builder.create()));
		final URI location = linkTo(UserResource.class).slash(userSaved.getName()).toUri();
		return ResponseEntity.created(location)
				.body(userConverter.toRepresentation(userSaved));
	}
	
	@PutMapping
	public ResponseEntity<UserDTO> change(@Valid @RequestBody(required = true) final UserDTO userDTO,
			final Authentication authentication) {
		final User user = userService.findByName(authentication.getName());
		final User userUpdated = userRepository.save(changeUserConverter.toEntity(userDTO, User.Builder.from(user)));
		return ResponseEntity.ok().body(userConverter.toRepresentation(userUpdated));
	}
	
	@GetMapping
	public ResponseEntity<UserDTO> findUserLogged(final Authentication authentication) {
		return ResponseEntity.ok()
				.body(userConverter.toRepresentation(userService.findByName(authentication.getName())));
	}
	
}
