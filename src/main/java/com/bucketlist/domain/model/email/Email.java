package com.bucketlist.domain.model.email;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.validator.constraints.Length;

import com.google.common.base.MoreObjects;

@Embeddable
public class Email {

	@Column(name = "email")
	@Length(max = 255)
	private String address;

	Email() {
		
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
	
	private Email(final String address) {
		checkArgument(EmailValidator.getInstance().isValid(address), "Invalid email address");
		this.address = address;
	}
	
	public static Email of(final String address) {
		return new Email(address);
	}
	
	public String getAddress() {
		return address;
	}
}
