package com.bucketlist.infrastructure.user.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.bucketlist.infrastructure.spring.security.user.model.User;
import com.bucketlist.infrastructure.spring.security.user.model.UserSpecification;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class UserTest {
		
	@DataProvider
	public static Object[][] invalidUserNameDataProvider() {
		return new Object[][] {
			{"user:test"},
			{"user?test"},
			{"user test"},
			{"user/\\,"}
		};
	}
	
	@Test
	@UseDataProvider("invalidUserNameDataProvider")
	public void shoudValidateWithInvalidName(final String name) {
		final User user = mock(User.class);
		when(user.getName()).thenReturn(name);
		assertFalse(UserSpecification.checkValidaName().isSatisfiedBy(user));
	}
	
	@DataProvider
	public static Object[][] validUserNameDataProvider() {
		return new Object[][] {
			{"user.test"},
			{"user.test1"},
			{"userTest"}
		};
	}
	
	@Test
	@DataProvider("validUserNameDataProvider")
	public void shouldCreateUserWithValidName(final String name) {
		final User user = mock(User.class);
		when(user.getName()).thenReturn(name);
		assertTrue(UserSpecification.checkValidaName().isSatisfiedBy(user));
	}
}
