package com.in.Services;

import java.util.List;

import com.in.DTOs.UserDto;

public interface UserService {
	
	UserDto creteUser(UserDto userDto) ;
	UserDto updateUser(UserDto userDto, int id);
  void deleteUser(int id );
  UserDto getUserByEmail(String email );
  List<UserDto> getAllUser();
  UserDto getById(int id);
  

}
