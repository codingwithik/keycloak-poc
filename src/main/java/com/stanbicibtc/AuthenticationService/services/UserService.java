package com.stanbicibtc.AuthenticationService.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stanbicibtc.AuthenticationService.entities.User;
import com.stanbicibtc.AuthenticationService.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	
	public Optional<User> findByUuid(String uuId) {
		return userRepository.findByUuid(uuId);
	}
	
	
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> findByPhoneNumber(String phoneNumber) {
		return userRepository.findByPhoneNumber(phoneNumber);
	}
	

	public Optional<User> findByPhoneNumberOrEmail(String string) {
		return userRepository.findByPhoneNumberOrEmail(string,string);
	}
	
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	public void delete(User user) {
		userRepository.delete(user);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public void saveAll(List<User> users) {
		userRepository.saveAll(users);
	}

	public boolean existsById(Long id) {
		return userRepository.existsById(id);
	}

	public long count() {
		return userRepository.count();
	}

	public void deleteAll(List<User> users) {
		userRepository.deleteAll(users);
	}

	public void deleteAll() {
		userRepository.deleteAll();
	}

}
