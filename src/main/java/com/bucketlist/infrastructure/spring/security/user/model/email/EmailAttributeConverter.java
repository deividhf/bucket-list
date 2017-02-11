package com.bucketlist.infrastructure.spring.security.user.model.email;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EmailAttributeConverter implements AttributeConverter<Email, String> {

	@Override
	public String convertToDatabaseColumn(final Email email) {
		if (email == null) return null;
		return email.getAddress();
	}

	@Override
	public Email convertToEntityAttribute(final String dbData) {
		if(dbData == null) return null;
		return Email.of(dbData);
	}

}
