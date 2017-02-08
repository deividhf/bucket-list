package com.bucketlist.interfaces.rest.user.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bucketlist.domain.model.user.User;
import com.bucketlist.domain.model.user.UserRepository;
import com.bucketlist.domain.model.user.UserService;
import com.bucketlist.interfaces.rest.user.converter.ChangeUserConverter;
import com.bucketlist.interfaces.rest.user.converter.UserConverter;
import com.bucketlist.interfaces.rest.user.dto.UserDTO;

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
	public ResponseEntity<UserDTO> save(@Valid @RequestBody final UserDTO user) {
		final User userSaved = userRepository.save(userConverter.toEntity(user, User.Builder.create()));
		final URI location = linkTo(UserResource.class).slash(userSaved.getName()).toUri();
		return ResponseEntity.created(location)
				.body(userConverter.toRepresentation(userSaved));
	}
	
	@PutMapping
	public ResponseEntity<UserDTO> change(@Valid @RequestBody(required = true) final UserDTO userDTO,
			final Authentication authentication) {
		final User user = userService.findByUserName(authentication.getName());
		final User userUpdated = userRepository.save(changeUserConverter.toEntity(userDTO, User.Builder.from(user)));
		return ResponseEntity.ok().body(userConverter.toRepresentation(userUpdated));
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<UserDTO> findByName(@PathVariable(required = true) final String name) {
		return ResponseEntity.ok().body(userConverter.toRepresentation(userService.findUserIfAuthenticated(name)));
	}
	
}
