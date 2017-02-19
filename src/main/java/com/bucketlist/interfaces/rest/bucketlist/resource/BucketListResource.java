package com.bucketlist.interfaces.rest.bucketlist.resource;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bucketlist.domain.model.bucketlist.BucketList;
import com.bucketlist.domain.model.bucketlist.BucketListRepository;
import com.bucketlist.domain.model.bucketlist.BucketListService;
import com.bucketlist.interfaces.rest.bucketlist.converter.BucketListConverter;
import com.bucketlist.interfaces.rest.bucketlist.dto.BucketListDTO;

@RestController
@RequestMapping("/bucket-lists")
public class BucketListResource {

	private BucketListService bucketListService;
	private BucketListRepository bucketListRepository;
	private BucketListConverter bucketListConverter;

	public BucketListResource(final BucketListService bucketListService, 
			final BucketListRepository bucketListRepository,
			final BucketListConverter bucketListConverter) {
		this.bucketListService = bucketListService;
		this.bucketListRepository = bucketListRepository;
		this.bucketListConverter = bucketListConverter;
	}

	@GetMapping
	public ResponseEntity<BucketListDTO> findBucketListByAuthenticatedUser(final Authentication authentication) {
		final BucketList bucketListFromAuthenticatedUser = bucketListService.findByUserName(authentication.getName());
		return ResponseEntity.ok()
				.body(bucketListConverter.toRepresentation(bucketListFromAuthenticatedUser));
	}
	
	@PutMapping("/{identifier}")
	public ResponseEntity<BucketListDTO> update(@PathVariable(name = "identifier", required = true) Long bucketListId, 
			@Valid @RequestBody(required = true) final BucketListDTO bucketListDTO, 
			final Authentication authentication) {
		final BucketList userBucketList = bucketListService.findByIdAndUserName(bucketListId, authentication.getName());
		final BucketList userBucketListUpdated = bucketListRepository
				.save(bucketListConverter.toEntity(bucketListDTO, BucketList.Builder.from(userBucketList)));
		return ResponseEntity.ok()
				.body(bucketListConverter.toRepresentation(userBucketListUpdated));
	}
}
