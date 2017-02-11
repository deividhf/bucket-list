package com.bucketlist.infrastructure.user.email.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.bucketlist.infrastructure.spring.security.user.model.email.Email;

public class EmailTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void shouldValidateInvalidEmail() {
		exception.equals(IllegalArgumentException.class);
		exception.expectMessage("Invalid email address");
		Email.of("wrong.com.br");
	}
	
	public void shouldCreateEmail() {
		final String address = "addres@address.com"; 
		Email email = Email.of(address);
		assertThat(email.getAddress(), is(equalTo(address)));
	}
}
