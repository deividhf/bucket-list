package com.bucketlist.shared;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bucketlist.domain.model.bucketlist.BucketListRepository;
import com.bucketlist.infrastructure.spring.security.user.model.UserRepository;
import com.bucketlist.infrastructure.spring.security.user.rest.dto.UserDTO;

@Component
public class CommonDataTest {

	@Inject
	private TestRestTemplate restTemplate;
	@Inject
	private UserRepository userRepository;
	@Inject
	private BucketListRepository bucketListRepository;
	
	public UserWithPasswordTest createUser() {
		final String password = "user";
		final UserDTO user = UserDTO.builder()
				.withName("User")
				.withPassword(password)
				.withEmail("user@email.com")
				.build();
		final ResponseEntity<UserDTO> userSavedResponse = restTemplate.postForEntity("/users", user, UserDTO.class);
		final UserDTO userSaved = userSavedResponse.getBody();
		assertNotNull(userSaved);
		return new UserWithPasswordTest(user, password);
	}
	
	public void deleteCommonData() {
		bucketListRepository.deleteAll();
		userRepository.deleteAll();
	}
}
