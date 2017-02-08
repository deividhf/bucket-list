package com.bucketlist.infrastructure.login.it;

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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bucketlist.domain.model.bucketlist.BucketListRepository;
import com.bucketlist.interfaces.rest.bucketlist.dto.BucketListDTO;
import com.bucketlist.shared.CommonDataTest;
import com.bucketlist.shared.UserWithPasswordTest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginIT {

	@Inject
	private TestRestTemplate restTemplate;
	@Inject
	private CommonDataTest commonData;
	@Inject
	private BucketListRepository bucketListRepository;

	@Test
	public void shouldCreateBucketListOnFirstLogin() {
		final UserWithPasswordTest user = commonData.createUser();
		final ResponseEntity<String> loginResponse = restTemplate.withBasicAuth(user.getName(), "user")
				.getForEntity("/login", String.class);
		assertNotNull(loginResponse);

		final ResponseEntity<BucketListDTO> userBucketListResponse = restTemplate.withBasicAuth(user.getName(), user.getPassword()).getForEntity("/bucket-list",
				BucketListDTO.class);
		assertNotNull(userBucketListResponse);
		assertNotNull(userBucketListResponse.getBody());
		
		final BucketListDTO userBucketList = userBucketListResponse.getBody();
		assertThat(userBucketList.getDescription(), is(equalTo(user.getName() + " Bucket List")));
		assertThat(userBucketList.getUser().getName(), is(equalTo(user.getName())));
		assertThat(userBucketList.getUser().getEmail(), is(equalTo(user.getEmail())));
	}

	@After
	public void tearDown() {
		bucketListRepository.deleteAll();
		commonData.deleteCommonData();
	}
}
