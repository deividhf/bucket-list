package com.bucketlist.domain.model.bucketlist;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.bucketlist.infrastructure.spring.security.user.model.UserCreatedEvent;

@Service
public class CreateBucketListNewUserService {

	private BucketListRepository bucketListRepository;

	public CreateBucketListNewUserService(final BucketListRepository bucketListRepository) {
		this.bucketListRepository = bucketListRepository;
	}
	
	@EventListener
	public void saveAfterUserCreated(final  UserCreatedEvent userCreatedEvent) {
		final BucketList bucketList = BucketList.Builder.create()
				.forUser(userCreatedEvent.getUser())
				.withDescription(userCreatedEvent.getUser().getName() + " Bucket List")
				.build();
		bucketListRepository.save(bucketList);
	}
}
