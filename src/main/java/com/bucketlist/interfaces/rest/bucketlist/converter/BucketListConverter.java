package com.bucketlist.interfaces.rest.bucketlist.converter;

import static java.util.stream.Collectors.toList;

import org.springframework.stereotype.Component;

import com.bucketlist.domain.model.bucketlist.BucketList;
import com.bucketlist.domain.model.bucketlist.BucketList.Builder;
import com.bucketlist.domain.model.bucketlist.goal.Goal;
import com.bucketlist.infrastructure.spring.security.user.rest.converter.UserConverter;
import com.bucketlist.interfaces.rest.bucketlist.dto.BucketListDTO;
import com.bucketlist.interfaces.rest.bucketlist.goal.dto.GoalDTO;
import com.bucketlist.interfaces.shared.AbstractConverter;

@Component
public class BucketListConverter extends AbstractConverter<BucketList, BucketListDTO, BucketList.Builder> {

	private UserConverter userConverter;

	public BucketListConverter(final UserConverter userConverter) {
		this.userConverter = userConverter;
	}
	
	@Override
	public BucketList toEntity(final BucketListDTO representation, final Builder builder) {
		return builder.withDescription(representation.getDescription())
				.withGoals(representation.getGoals().stream()
						.map(goal -> Goal.of(goal.getDescription(), goal.isAchieved()))
						.collect(toList()))
				.build();
	}

	@Override
	public BucketListDTO toRepresentation(final BucketList bucketList) {
		return BucketListDTO.builder()
				.withIdentifier(bucketList.getId())
				.withDescription(bucketList.getDescription())
				.withUser(userConverter.toRepresentation(bucketList.getUser()))
				.withGoals(bucketList.getGoals().stream()
						.map(goal -> GoalDTO.of(goal.getDescription(), goal.isAchieved()))
						.collect(toList()))
				.build();
	}

}
