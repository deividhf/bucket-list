package com.bucketlist.infrastructure.security.login.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bucketlist.domain.model.bucketlist.BucketList;
import com.bucketlist.domain.model.bucketlist.BucketListRepository;
import com.bucketlist.domain.model.user.UserService;

@Service
public class LoginService {

	private BucketListRepository buckeListRepository;
	private UserService userService;

	public LoginService(final BucketListRepository buckeListRepository, final UserService userService) {
		this.buckeListRepository = buckeListRepository;
		this.userService = userService;
	}

	public void login(final UserDetails principal) {
		createBucketListNewUser(principal);
	}

	private void createBucketListNewUser(final UserDetails principal) {
		final Optional<BucketList> bucketList = buckeListRepository.findByUserName(principal.getUsername());
		if (!bucketList.isPresent()) {
			final BucketList newBucketListForUser = BucketList.Builder.create()
					.withDescription(principal.getUsername() + " Bucket List")
					.forUser(userService.findByUserName(principal.getUsername()))
					.build();
			buckeListRepository.save(newBucketListForUser);
		}
	}

}
