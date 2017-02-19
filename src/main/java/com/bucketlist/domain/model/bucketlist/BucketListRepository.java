package com.bucketlist.domain.model.bucketlist;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketListRepository extends JpaRepository<BucketList, Long> {

	Optional<BucketList> findByUserName(final String username);
	
	Optional<BucketList> findByIdAndUserName(final Long id, final String username);
}
