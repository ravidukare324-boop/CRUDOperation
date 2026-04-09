package com.in.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.in.Entitty.Users;
import com.in.Excepitons.ResourceNotFoundException;
import com.in.Repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService
{
 private final UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	 Users user = userRepo.findByEmail(username)
			 .orElseThrow(() -> new ResourceNotFoundException("user have not any space"));
		return user;
	}

}
