package com.bucketlist.domain.model.bucketlist;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.bucketlist.infrastructure.spring.security.user.model.UserCreatedEvent;

@Component
public class CreateBucketListNewUserService {

	private BucketListRepository bucketListRepository;

	public CreateBucketListNewUserService(final BucketListRepository bucketListRepository) {
		this.bucketListRepository = bucketListRepository;
	}
	
	@EventListener
	public void create(final  UserCreatedEvent userCreatedEvent) {
		final BucketList bucketList = BucketList.Builder.create()
				.forUser(userCreatedEvent.getUser())
				.withDescription(userCreatedEvent.getUser().getName() + " Bucket List")
				.build();
		bucketListRepository.save(bucketList);
	}
}
