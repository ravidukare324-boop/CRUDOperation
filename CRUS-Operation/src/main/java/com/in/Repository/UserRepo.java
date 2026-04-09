package com.in.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in.Entitty.Users;
@Repository
public interface UserRepo extends JpaRepository<Users,Integer>{
	   
        Optional<Users> findByEmail(String email);
        boolean existsByEmail(String email);
}
