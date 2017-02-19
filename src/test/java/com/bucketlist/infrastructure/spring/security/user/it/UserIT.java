package com.bucketlist.infrastructure.spring.security.user.it;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bucketlist.domain.shared.specification.InvalidSpecificationException;
import com.bucketlist.infrastructure.spring.security.user.model.User;
import com.bucketlist.infrastructure.spring.security.user.model.UserRepository;
import com.bucketlist.infrastructure.spring.security.user.model.email.Email;
import com.bucketlist.shared.CommonDataTest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class UserIT {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Inject
	private CommonDataTest commonData;
	@Inject
	private UserRepository userRepository;

	@Test
	public void shouldValidateDuplicateUser() {
		final User user = User.Builder.create()
				.withName("test")
				.withEmail(Email.of("test@email.com"))
				.withPassword("teste")
				.build();
		userRepository.save(user);
		exception.expect(InvalidSpecificationException.class);
		User.Builder.create()
				.withName("test")
				.withEmail(Email.of("test@email.com"))
				.withPassword("teste")
				.build();
	}
	
	@After
	public void tearDown() {
		commonData.deleteCommonData();
	}
}
