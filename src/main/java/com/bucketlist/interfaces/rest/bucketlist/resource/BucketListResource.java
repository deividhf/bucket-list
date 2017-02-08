package com.bucketlist.interfaces.rest.bucketlist.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bucketlist.domain.model.bucketlist.BucketList;
import com.bucketlist.domain.model.bucketlist.BucketListService;
import com.bucketlist.interfaces.rest.bucketlist.converter.BucketListConverter;
import com.bucketlist.interfaces.rest.bucketlist.dto.BucketListDTO;

@RestController
@RequestMapping("/bucket-list")
public class BucketListResource {

	private BucketListService bucketListService;
	private BucketListConverter bucketListConverter;

	public BucketListResource(final BucketListService bucketListService,
			final BucketListConverter bucketListConverter) {
		this.bucketListService = bucketListService;
		this.bucketListConverter = bucketListConverter;
	}

	@GetMapping
	public ResponseEntity<BucketListDTO> findBucketListByAuthenticatedUser(final Authentication authentication) {
		final BucketList bucketListFromAuthenticatedUser = bucketListService.findByUserName(authentication.getName());
		return ResponseEntity.ok()
				.body(bucketListConverter.toRepresentation(bucketListFromAuthenticatedUser));
	}
}
