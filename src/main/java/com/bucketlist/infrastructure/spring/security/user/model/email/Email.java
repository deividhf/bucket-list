package com.bucketlist.infrastructure.spring.security.user.model.email;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

import org.apache.commons.validator.routines.EmailValidator;

import com.google.common.base.MoreObjects;

public class Email {

	private final String address;

	private Email(final String address) {
		this.address = address;
	}
	
	public static final Email of(final String address) {
		checkNotNull(address);
		checkArgument(address.length() <= 255, "Address can'nt have more than 255 characters");
		checkArgument(EmailValidator.getInstance().isValid(address), "Invalid email address");
		return new Email(address);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(address);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Email) {
			final Email other = (Email) obj;
			return Objects.equals(this.address, other.address);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("address", address)
				.toString();
	}
	
	public String getAddress() {
		return address;
	}
}
