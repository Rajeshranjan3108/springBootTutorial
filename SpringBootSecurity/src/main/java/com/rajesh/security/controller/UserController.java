package com.rajesh.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rajesh.security.model.AuthToken;
import com.rajesh.security.model.RefreshToken;
import com.rajesh.security.model.RefreshTokenDto;
import com.rajesh.security.model.User;
import com.rajesh.security.repo.RefreshTokenRepo;
import com.rajesh.security.service.JwtService;
import com.rajesh.security.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtService jwtService;

	@Autowired
	private RefreshTokenRepo refreshTokenRepo;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {
		Boolean isSaved = userService.registerUser(user);
		if (isSaved) {
			return new ResponseEntity("User is registered with user name >> " + user.getUserName(), HttpStatus.OK);
		} else {
			return new ResponseEntity("User is not registered with user name >> " + user.getUserName(),
					HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		AuthToken authToken = userService.verify(user);
		return new ResponseEntity(authToken, HttpStatus.OK);
	}

	@PostMapping("/refresh")
	public ResponseEntity<String> refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
		RefreshToken refreshToken = jwtService.verifyRefreshToken(refreshTokenDto.getRefreshToken());

		User user = refreshToken.getUser();
		AuthToken authToken =userService.generateTokenFromRefresh(user.getUserName());
		return new ResponseEntity(authToken, HttpStatus.OK);
	}
}
