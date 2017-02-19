package com.bucketlist.domain.model.bucktlist.it;

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
				.withBasicAuth(user.getName(), user.getPassword()).getForEntity("/bucket-lists", BucketListDTO.class);
		assertNotNull(userBucketListResponse);
		assertNotNull(userBucketListResponse.getBody());

		final BucketListDTO userBucketList = userBucketListResponse.getBody();
		assertThat(userBucketList.getDescription(), is(equalTo(user.getName() + " Bucket List")));
		assertThat(userBucketList.getUser().getName(), is(equalTo(user.getName())));
		assertThat(userBucketList.getUser().getEmail(), is(equalTo(user.getEmail())));
	}
	
	@Test
	public void shouldUpdateBucketListDescription() {
		final UserWithPasswordTest user = commonData.createUser();
		
		// get the bucket list from user
		final ResponseEntity<BucketListDTO> userBucketListResponse = restTemplate
				.withBasicAuth(user.getName(), user.getPassword()).getForEntity("/bucket-lists", BucketListDTO.class);
		assertNotNull(userBucketListResponse);
		assertNotNull(userBucketListResponse.getBody());
		
		// update
		final BucketListDTO bucketList = userBucketListResponse.getBody();
		final BucketListDTO bucketListToUpdate = bucketList.withDescription("Travelling around the world");
		
		restTemplate.withBasicAuth(user.getName(), user.getPassword()).put("/bucket-lists/{identifier}", bucketListToUpdate, 
				bucketListToUpdate.getIdentifier());
		
		// find to verify if it was changed
		final ResponseEntity<BucketListDTO> userBucketListUpdatedResponse = restTemplate
				.withBasicAuth(user.getName(), user.getPassword()).getForEntity("/bucket-lists", BucketListDTO.class);
		assertThat(userBucketListUpdatedResponse.getStatusCode(), is(equalTo(HttpStatus.OK)));
		assertNotNull(userBucketListUpdatedResponse);
		assertNotNull(userBucketListUpdatedResponse.getBody());
		
		final BucketListDTO bucketListUpdated = userBucketListUpdatedResponse.getBody();
		assertThat(bucketListUpdated.getUser().getName(), is(equalTo(bucketListToUpdate.getUser().getName())));
		assertThat(bucketListUpdated.getDescription(), is(equalTo(bucketListToUpdate.getDescription())));
	}
	
	@After
	public void tearDown() {
		commonData.deleteCommonData();
	}
}
