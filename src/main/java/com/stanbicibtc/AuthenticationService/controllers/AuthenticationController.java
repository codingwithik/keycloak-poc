package com.stanbicibtc.AuthenticationService.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stanbicibtc.AuthenticationService.entities.User;
import com.stanbicibtc.AuthenticationService.pojo.GenericResponse;
import com.stanbicibtc.AuthenticationService.pojo.JwtResponse;
import com.stanbicibtc.AuthenticationService.pojo.LoginRequest;
import com.stanbicibtc.AuthenticationService.pojo.UserInfoResponse;
import com.stanbicibtc.AuthenticationService.security.CustomUserDetails;
import com.stanbicibtc.AuthenticationService.security.JwtUtils;
import com.stanbicibtc.AuthenticationService.services.UserService;
import com.stanbicibtc.AuthenticationService.util.Constant;
import com.stanbicibtc.AuthenticationService.util.KeycloakHandler;
import com.stanbicibtc.AuthenticationService.util.PasswordValidator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping(path = "api")
public class AuthenticationController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;

	private PasswordValidator validator;
	
	private KeycloakHandler handler;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	MessageSource messageSource;
	
	@PostMapping("/v1/auth/register")
	public GenericResponse newUser(@RequestBody User newUser) {
		
		
		// generate uuid for user
		String userUuid = java.util.UUID.randomUUID().toString();
				
		if (userService.findByEmail(newUser.getEmail().trim()).isPresent())
			return new GenericResponse(messageSource.getMessage("email.exist", null, LocaleContextHolder.getLocale()),
					false, "101");
		

		// strip spaces from phone number
		newUser.setPhoneNumber(newUser.getPhoneNumber().trim());

		if (userService.findByPhoneNumber(newUser.getPhoneNumber()).isPresent())
			return new GenericResponse(messageSource.getMessage("phone.exist", null, LocaleContextHolder.getLocale()),
					false, "101");

	
//		if (!validator.validatePassword(newUser.getPassword().trim()))
//				return new GenericResponse(
//						messageSource.getMessage("registration.password.invalid", null, LocaleContextHolder.getLocale()),
//						false, "101");
		
			
		// hash user password
	    newUser.setPassword(passwordEncoder.encode(newUser.getPassword().trim()));
		newUser.setUuid(userUuid);
		newUser.setEmail(newUser.getEmail().trim());
		newUser.setRole("ROLE_USER");
		
		userService.save(newUser);
		
		return new GenericResponse(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()), true, "000");
	}
	
	@GetMapping("/v1/getAll")
	public List<User> getAll() {
		return userService.findAll();
	}
	
	@PostMapping("/v1/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		
		Map<String, Object> map = new HashMap<>();
		
		
		handler = new KeycloakHandler();
		
		UserInfoResponse response = handler.validateToken(loginRequest.getTokenId());
		
		if(response != null) {
			
			User user = userService.findByEmail(response.getEmail()).orElse(null);
			
			if(user != null) {
				
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtUtils.generateJwtToken(authentication);
				
				CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
				
				return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getEmail(), Constant.EXPIRATION_TIME));
				
			}else if(user == null) {
				
				User newUser = new User();
				//generate uuid for user
				String userUuid = java.util.UUID.randomUUID().toString();
				newUser.setEmail(response.getEmail());
				newUser.setFirstName(response.getGiven_name());
				newUser.setLastName(response.getFamily_name());
				newUser.setRole(Constant.ROLE_USER);
				newUser.setUuid(userUuid);
				newUser.setPassword(passwordEncoder.encode(loginRequest.getPassword().trim()));
				
				userService.save(newUser);
				
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtUtils.generateJwtToken(authentication);
				
				CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
				
				return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getEmail(), Constant.EXPIRATION_TIME));
			}
			
		}
			
		map.put("success", false);
		map.put("message", "Unauthorized user");
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}
}
