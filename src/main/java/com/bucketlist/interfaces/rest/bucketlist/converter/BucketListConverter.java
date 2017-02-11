package com.bucketlist.interfaces.rest.bucketlist.converter;

import org.springframework.stereotype.Component;

import com.bucketlist.domain.model.bucketlist.BucketList;
import com.bucketlist.domain.model.bucketlist.BucketList.Builder;
import com.bucketlist.infrastructure.spring.security.user.model.UserService;
import com.bucketlist.infrastructure.spring.security.user.rest.converter.UserConverter;
import com.bucketlist.interfaces.rest.bucketlist.dto.BucketListDTO;
import com.bucketlist.interfaces.shared.AbstractConverter;

@Component
public class BucketListConverter extends AbstractConverter<BucketList, BucketListDTO, BucketList.Builder> {

	private UserService userService;
	private UserConverter userConverter;

	public BucketListConverter(final UserService userService, final UserConverter userConverter) {
		this.userService = userService;
		this.userConverter = userConverter;
	}
	
	@Override
	public BucketList toEntity(final BucketListDTO representation, final Builder builder) {
		return builder.withDescription(representation.getDescription())
				.forUser(userService.findByName(representation.getUser().getName()))
				.build();
	}

	@Override
	public BucketListDTO toRepresentation(final BucketList entity) {
		return BucketListDTO.builder()
				.withDescription(entity.getDescription())
				.withUser(userConverter.toRepresentation(entity.getUser()))
				.build();
	}

}
