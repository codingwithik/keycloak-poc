package com.stanbicibtc.AuthenticationService.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.stanbicibtc.AuthenticationService.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	Optional<User> findByUuid(String uuid);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByPhoneNumber(String phoneNumber);

	Optional<User> findByPhoneNumberOrEmail(String phoneNumber,String email);
	
}
