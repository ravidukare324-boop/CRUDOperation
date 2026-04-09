package com.in.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.in.DTOs.UserDto;
import com.in.Services.UserService;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	@GetMapping("/users/{id}")
	public ResponseEntity<UserDto> getUseById(@PathVariable int id) {
		return ResponseEntity.ok(userService.getById(id));
	}
	@GetMapping("/user/{email}")
	public ResponseEntity<UserDto> getByEmail(@PathVariable String email){
		return ResponseEntity.ok(userService.getUserByEmail(email));
	}
	
	
	@GetMapping("/all/user/")
	public ResponseEntity<List<UserDto>> getAllUser(){
		return ResponseEntity.ok(userService.getAllUser());
	}
	
	@PostMapping("/post/user")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.creteUser(userDto));
	}
	@DeleteMapping("/delete/user{id}")
	public ResponseEntity<String	> deleteUser(@PathVariable int id ){
		userService.deleteUser(id);
		return ResponseEntity.ok("userDelete successfully");
	}
	
	@PutMapping("/update/user/{id}")
	public ResponseEntity<UserDto> updateUser( @RequestBody UserDto userDto,@PathVariable int id){
		return ResponseEntity.ok(userService.updateUser(userDto, id));
	}
	
	
	
	
}
