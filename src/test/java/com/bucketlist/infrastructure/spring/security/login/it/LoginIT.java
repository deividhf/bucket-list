package com.bucketlist.infrastructure.spring.security.login.it;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
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

import com.bucketlist.shared.CommonDataTest;
import com.bucketlist.shared.UserWithPasswordTest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration_test")
public class LoginIT {

	@Inject
	private TestRestTemplate restTemplate;
	@Inject
	private CommonDataTest commonData;

	@Test
	public void shouldLogin() {
		final UserWithPasswordTest user = commonData.createUser();
		final ResponseEntity<String> loginResponse = restTemplate.withBasicAuth(user.getName(), "user")
				.getForEntity("/login", String.class);
		assertNotNull(loginResponse);
		assertThat(loginResponse.getStatusCode(), is(equalTo(HttpStatus.OK)));
		assertThat(loginResponse.getBody(), is(equalTo("Logged")));
	}

	@After
	public void tearDown() {
		commonData.deleteCommonData();
	}
}
