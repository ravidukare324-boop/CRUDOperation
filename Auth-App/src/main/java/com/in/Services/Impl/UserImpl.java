package com.in.Services.Impl;



import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import com.in.DTOs.UserDto;
import com.in.Entitty.Users;
import com.in.Excepitons.ResourceNotFoundException;
import com.in.Repository.UserRepo;
import com.in.Services.UserService;

import java.time.Instant;
import java.util.List;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {

	private final UserRepo userRepo;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDto creteUser(UserDto userDto) {
		if(userDto.getEmail()==null || userDto.getEmail().trim().isEmpty()) {
			throw new ResourceNotFoundException("inser email please");
		}
		if(userRepo.existsByEmail(userDto.getEmail())) {
			throw new ResourceNotFoundException("user is exists");
		}
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Users user = modelMapper.map(userDto, Users.class);
		user = userRepo.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, int id) {
		Users user = userRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("user not found"));

		modelMapper.map(userDto, user);
		user.setUpdatedAt(Instant.now());
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Users users = userRepo.save(user);
		return modelMapper.map(users, UserDto.class);	
	}

	@Override
	public void deleteUser(int id) {
		Users user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user invalid"));
		userRepo.deleteById(id);
	}




	@Override
	public UserDto getUserByEmail(String email) {

		Users user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("email invalid"));
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<Users> users = userRepo.findAll();   
		return  users.stream().map(user ->
		modelMapper.map(user,UserDto.class)).toList();

	}

	@Override
	public UserDto getById(int id ) {
		Users user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("id not matched"));
		return modelMapper.map(user,UserDto.class);

	}

}
