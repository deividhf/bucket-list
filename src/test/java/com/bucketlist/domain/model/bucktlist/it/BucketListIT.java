package com.bucketlist.domain.model.bucktlist.it;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bucketlist.interfaces.rest.bucketlist.dto.BucketListDTO;
import com.bucketlist.shared.CommonDataTest;
import com.bucketlist.shared.UserWithPasswordTest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class BucketListIT {

	@Inject
	private TestRestTemplate restTemplate;
	@Inject
	private CommonDataTest commonData;
	
	@Test
	public void shouldCreateBucktListAfterCreateNewUser() {
		final UserWithPasswordTest user = commonData.createUser();

		final ResponseEntity<BucketListDTO> userBucketListResponse = restTemplate
				.withBasicAuth(user.getName(), user.getPassword()).getForEntity("/bucket-list", BucketListDTO.class);
		assertNotNull(userBucketListResponse);
		assertNotNull(userBucketListResponse.getBody());

		final BucketListDTO userBucketList = userBucketListResponse.getBody();
		assertThat(userBucketList.getDescription(), is(equalTo(user.getName() + " Bucket List")));
		assertThat(userBucketList.getUser().getName(), is(equalTo(user.getName())));
		assertThat(userBucketList.getUser().getEmail(), is(equalTo(user.getEmail())));
	}
}
