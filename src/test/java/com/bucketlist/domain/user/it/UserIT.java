package com.bucketlist.domain.user.it;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bucketlist.domain.model.user.UserRepository;
import com.bucketlist.interfaces.rest.user.dto.UserDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserIT {

	@Inject
	private TestRestTemplate restTemplate;
	@Inject
	private UserRepository userRepository;
	
	@Test
	public void testCRUD() {
		// request with empty user
		final ResponseEntity<UserDTO> responseEmptyUser = restTemplate.postForEntity("/users", UserDTO.builder().build(), UserDTO.class);
		assertThat(responseEmptyUser.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
		
		// save
		final UserDTO user = UserDTO.builder()
				.withName("User")
				.withPassword("user")
				.withEmail("user@email.com")
				.build();
		final ResponseEntity<UserDTO> userSavedResponse = restTemplate.postForEntity("/users", user, UserDTO.class);
		assertNotNull(userSavedResponse);
		assertNotNull(userSavedResponse.getBody());
		assertThat(userSavedResponse.getStatusCode(), is(equalTo(HttpStatus.CREATED)));
		final UserDTO userSaved = userSavedResponse.getBody();
		assertThat(userSaved.getName(), is(equalTo(user.getName())));
		assertThat(userSaved.getEmail(), is(equalTo(user.getEmail())));
		assertNull(userSaved.getPassword());

		final UserDTO userUpdate = userSaved.withEmail("userChanged@user.com").withPassword("user");
		// change email
		restTemplate.withBasicAuth(user.getName(), user.getPassword()).put("/users", userUpdate);

		// find user changed
		final ResponseEntity<UserDTO> userFoundResponse = restTemplate.withBasicAuth(user.getName(), user.getPassword())
				.getForEntity("/users/{name}", UserDTO.class, userUpdate.getName());
		assertNotNull(userFoundResponse);
		assertNotNull(userFoundResponse.getBody());
		final UserDTO userFound = userFoundResponse.getBody();
		assertThat(userFound.getName(), is(equalTo(userUpdate.getName())));
		assertThat(userFound.getEmail(), is(equalTo(userUpdate.getEmail())));
	}

	@After
	public void tearDown() {
		userRepository.deleteAll();
	}
}
