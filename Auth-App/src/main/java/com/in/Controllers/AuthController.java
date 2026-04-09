package com.in.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.in.DTOs.UserDto;
import com.in.Services.AuthService2;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService2 authService;

	@PostMapping("/register")
	ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		
		return ResponseEntity.ok(authService.registerUser(userDto));
		
	}
}
