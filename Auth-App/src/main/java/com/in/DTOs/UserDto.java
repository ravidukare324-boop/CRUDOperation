package com.in.DTOs;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.in.Entitty.Provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {
	
	 
	   private String email;
	   private String name ;
	   private String password;
	   private String image;
	   private boolean enabled = true; 
	   private Instant createAt ;
	   private Instant updatedAt;
	   private Provider provider;
	   private Set<RoleDto> roleDto = new HashSet<>();
	   

}
