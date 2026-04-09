package com.in.Services;

import org.springframework.stereotype.Service;

import com.in.DTOs.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService2 {

	private final UserService userService;
	
	public UserDto registerUser(UserDto userDto) {
		return userService.creteUser(userDto);
	}

}
