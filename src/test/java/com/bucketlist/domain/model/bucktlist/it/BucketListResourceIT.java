package com.bucketlist.domain.model.bucktlist.it;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
import com.bucketlist.interfaces.rest.bucketlist.goal.dto.GoalDTO;
import com.bucketlist.shared.CommonDataTest;
import com.bucketlist.shared.UserWithPasswordTest;
import com.google.common.collect.ImmutableList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class BucketListResourceIT {

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
	
	@Test
	public void shouldAddGoalsToBucketList() {
		final UserWithPasswordTest user = commonData.createUser();
		
		// get the bucket list from user
		final ResponseEntity<BucketListDTO> userBucketListResponse = restTemplate
				.withBasicAuth(user.getName(), user.getPassword()).getForEntity("/bucket-lists", BucketListDTO.class);
		assertNotNull(userBucketListResponse);
		assertNotNull(userBucketListResponse.getBody());
		
		// add goals
		final BucketListDTO bucketList = userBucketListResponse.getBody();
		final GoalDTO travellingLondon = GoalDTO.of("Travelling to London");
		final GoalDTO travellingAntartic = GoalDTO.of("Travelling to Antartic");
		final BucketListDTO bucketListWithGoalsAdded = bucketList.withGoals(
				ImmutableList.of(
						travellingLondon,
						travellingAntartic
				));
		
		// 
		restTemplate.withBasicAuth(user.getName(), user.getPassword()).put("/bucket-lists/{identifier}", 
				bucketListWithGoalsAdded, bucketListWithGoalsAdded.getIdentifier());
		
		// find to verify if the goals were added
		final ResponseEntity<BucketListDTO> bucketListUpdatedWithGoalsResponse = restTemplate
				.withBasicAuth(user.getName(), user.getPassword()).getForEntity("/bucket-lists", BucketListDTO.class);
		assertThat(bucketListUpdatedWithGoalsResponse.getStatusCode(), is(equalTo(HttpStatus.OK)));
		assertNotNull(bucketListUpdatedWithGoalsResponse);
		assertNotNull(bucketListUpdatedWithGoalsResponse.getBody());
		
		final BucketListDTO bucketListUpdatedWithGoals = bucketListUpdatedWithGoalsResponse.getBody();
		assertNotNull(bucketListUpdatedWithGoals.getGoals());
		assertThat(bucketListUpdatedWithGoals.getGoals().size(), is(equalTo(2)));
		
		final GoalDTO travellingLondonFound = bucketListUpdatedWithGoals.getGoals().stream()
				.filter(goal -> goal.getDescription().equals(travellingLondon.getDescription()))
				.findFirst()
				.orElse(null);
		assertNotNull(travellingLondonFound);
		assertFalse(travellingLondonFound.isAchieved());
		
		final GoalDTO travellingAntarticFound = bucketListUpdatedWithGoals.getGoals().stream()
				.filter(goal -> goal.getDescription().equals(travellingAntartic.getDescription()))
				.findFirst()
				.orElse(null);
		assertNotNull(travellingAntarticFound);
		assertFalse(travellingAntarticFound.isAchieved());
	}
	
	@Test
	public void shouldAchieveGoal() {
		final UserWithPasswordTest user = commonData.createUser();
		
		// get the bucket list from user
		final ResponseEntity<BucketListDTO> userBucketListResponse = restTemplate
				.withBasicAuth(user.getName(), user.getPassword()).getForEntity("/bucket-lists", BucketListDTO.class);
		assertNotNull(userBucketListResponse);
		assertNotNull(userBucketListResponse.getBody());
		
		// add goals achieved and not achieved
		final BucketListDTO bucketList = userBucketListResponse.getBody();
		final GoalDTO travellingLondon = GoalDTO.of("Travelling to London", false);
		final GoalDTO livingRussia = GoalDTO.of("Living in Russia", false);
		final GoalDTO travellingAntartic = GoalDTO.of("Travelling to Antartic", true);
		final BucketListDTO bucketListWithGoalsAdded = bucketList.withGoals(
				ImmutableList.of(
						travellingLondon,
						livingRussia,
						travellingAntartic
				));
		
		// update 
		restTemplate.withBasicAuth(user.getName(), user.getPassword()).put("/bucket-lists/{identifier}", 
				bucketListWithGoalsAdded, bucketListWithGoalsAdded.getIdentifier());
		
		// find to verify if the goals were added
		final ResponseEntity<BucketListDTO> bucketListUpdatedWithGoalsResponse = restTemplate
				.withBasicAuth(user.getName(), user.getPassword()).getForEntity("/bucket-lists", BucketListDTO.class);
		assertThat(bucketListUpdatedWithGoalsResponse.getStatusCode(), is(equalTo(HttpStatus.OK)));
		assertNotNull(bucketListUpdatedWithGoalsResponse);
		assertNotNull(bucketListUpdatedWithGoalsResponse.getBody());
		
		final BucketListDTO bucketListUpdatedWithGoals = bucketListUpdatedWithGoalsResponse.getBody();
		assertNotNull(bucketListUpdatedWithGoals.getGoals());
		assertThat(bucketListUpdatedWithGoals.getGoals().size(), is(equalTo(3)));
		
		final GoalDTO travellingLondonFound = bucketListUpdatedWithGoals.getGoals().stream()
				.filter(goal -> goal.getDescription().equals(travellingLondon.getDescription()))
				.findFirst()
				.orElse(null);
		assertFalse(travellingLondonFound.isAchieved());
		
		final GoalDTO livingRussiaFound = bucketListUpdatedWithGoals.getGoals().stream()
				.filter(goal -> goal.getDescription().equals(livingRussia.getDescription()))
				.findFirst()
				.orElse(null);
		assertFalse(livingRussia.isAchieved());
		
		final GoalDTO travellingAntarticFound = bucketListUpdatedWithGoals.getGoals().stream()
				.filter(goal -> goal.getDescription().equals(travellingAntartic.getDescription()))
				.findFirst()
				.orElse(null);
		assertTrue(travellingAntarticFound.isAchieved());
		
		// achieve living in russia goal
		final GoalDTO livingRussiaAchieved = livingRussiaFound.achieve();
		
		// update bucket list with the goal updated
		final BucketListDTO bucketListWithGoalsAchieved = bucketList.withGoals(
				ImmutableList.of(
						travellingLondon,
						livingRussiaAchieved,
						travellingAntartic
				));

		restTemplate.withBasicAuth(user.getName(), user.getPassword()).put("/bucket-lists/{identifier}", 
				bucketListWithGoalsAchieved, bucketListWithGoalsAchieved.getIdentifier());
		
		// find to verify if it was changed
		final ResponseEntity<BucketListDTO> bucketListUpdatedWithGoalAchievedResponse = restTemplate
				.withBasicAuth(user.getName(), user.getPassword()).getForEntity("/bucket-lists", BucketListDTO.class);
		assertThat(bucketListUpdatedWithGoalAchievedResponse.getStatusCode(), is(equalTo(HttpStatus.OK)));
		assertNotNull(bucketListUpdatedWithGoalAchievedResponse);
		assertNotNull(bucketListUpdatedWithGoalAchievedResponse.getBody());
		
		final BucketListDTO bucketListUpdatedWithGoalAchieved = bucketListUpdatedWithGoalAchievedResponse.getBody();
		assertThat(bucketListUpdatedWithGoalAchieved.getGoals().size(), is(equalTo(3)));
		
		final GoalDTO livingRussiaAchievedFound = bucketListUpdatedWithGoalAchieved.getGoals().stream()
				.filter(goal -> goal.getDescription().equals(livingRussiaAchieved.getDescription()))
				.findFirst()
				.orElse(null);
		assertTrue(livingRussiaAchievedFound.isAchieved());
	}
	
	@After
	public void tearDown() {
		commonData.deleteCommonData();
	}
}
