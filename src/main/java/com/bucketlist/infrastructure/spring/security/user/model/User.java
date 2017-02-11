package com.bucketlist.infrastructure.spring.security.user.model;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bucketlist.domain.shared.AbstractBuilder;
import com.bucketlist.domain.shared.AbstractEntity;
import com.bucketlist.domain.shared.specification.Validator;
import com.bucketlist.infrastructure.spring.security.user.model.email.Email;
import com.google.common.base.MoreObjects;
import static com.google.common.base.Preconditions.checkArgument;
import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;

@SuppressWarnings("serial")
@Entity
public class User extends AbstractEntity {

	@NotNull
	@Length(max = 100)
	private String name;

	@QueryType(PropertyType.STRING)
	private Email email;

	@NotNull
	private String password;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role;

	User() {
		this.role = Role.USER;
	}

	private User(final Long id, final String name, final Optional<Email> email, final String password,
			final Role role) {
		super(id);
		this.name = name;
		this.role = role;
		this.email = email.orElse(null);
		this.password = new BCryptPasswordEncoder().encode(password);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(final Object obj) {
		if(obj instanceof User) {
			final User other = (User) obj;
			return Objects.equals(this.name, other.name);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.toString();
	}

	public String getName() {
		return name;
	}
	
	private void setName(final String name) {
		checkArgument(Pattern.compile("[a-zA-Z0-9\\._\\-]{3,}").matcher(name).matches(), 
				"Invalid login name");
		this.name = name;
	}

	public Optional<Email> getEmail() {
		return Optional.ofNullable(Email.of(email.getAddress()));
	}

	public String getPassword() {
		return password;
	}
	
	private void setPassword(final String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}

	public Role getRole() {
		return role;
	}

	public User changePassword(final String newPassword) {
		return new User(id, name, Optional.ofNullable(email), new BCryptPasswordEncoder().encode(newPassword), role);
	}

	public static final class Builder extends AbstractBuilder<User> {

		private Builder(final User user) {
			super(user);
		}

		public static Builder create() {
			return new Builder(new User());
		}

		public static Builder from(final User user) {
			return new Builder(user);
		}

		public Builder withName(final String name) {
			entity.setName(name);
			return this;
		}

		public Builder withEmail(final Email email) {
			entity.email = email;
			return this;
		}

		public Builder withPassword(final String password) {
			entity.setPassword(password);
			return this;
		}

		@Override
		protected Optional<Class<? extends Validator<User>>> getValidatorClass() {
			return Optional.of(UserValidator.class);
		}
	}

}