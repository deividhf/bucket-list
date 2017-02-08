package com.bucketlist.domain.model.bucketlist;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

@Service
public class BucketListService {

	private BucketListRepository bucketListRepository;

	public BucketListService(final BucketListRepository bucketListRepository) {
		this.bucketListRepository = bucketListRepository;
	}
	
	public BucketList findByUserName(final String userName) {
		return bucketListRepository.findByUserName(userName)
				.orElseThrow(() -> new BadRequestException("Bucket list not found for the user"));
	}
}
