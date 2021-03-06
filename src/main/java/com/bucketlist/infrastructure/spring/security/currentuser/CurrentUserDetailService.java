package com.bucketlist.infrastructure.spring.security.currentuser;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bucketlist.infrastructure.spring.security.user.model.User;
import com.bucketlist.infrastructure.spring.security.user.model.UserRepository;

@Service
public class CurrentUserDetailService implements UserDetailsService {

	private UserRepository userRepository;

	public CurrentUserDetailService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(final String name) throws UsernameNotFoundException {
		final User user = userRepository.findByName(name)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new CurrentUser(user);
	}

}
