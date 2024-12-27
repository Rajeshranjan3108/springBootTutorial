package com.rajesh.security.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rajesh.security.exception.UserAlreadyExistException;
import com.rajesh.security.model.AuthToken;
import com.rajesh.security.model.RefreshToken;
import com.rajesh.security.model.User;
import com.rajesh.security.repo.UserRepo;

@Service
public class UserService {

	private static final Logger logger=LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepo repo;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	public boolean registerUser(User user)
	{
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		User savedUser=null;
		try {
			savedUser=repo.save(user);
		}catch(RuntimeException e) {
			throw new UserAlreadyExistException("User already exists!!");
		}
		return savedUser==null?false:true;
	}

	public AuthToken verify(User user) {
		logger.info("before inside verify method :: ");

		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		logger.info("before inside verify method :: ");

		if(authenticate.isAuthenticated()) {
			logger.info("inside isAutheticated method :: ");
			String token=jwtService.generateToke(user.getUserName());
			Date expiry=jwtService.expiry(token);
			RefreshToken refreshToken=jwtService.generateRefreshToken(user.getUserName());
			return AuthToken.builder()
					.token(token).userName(user.getUserName())
					.expiry(expiry).refreshToken(refreshToken.getRefreshToken()).build();
			
		}else {
			throw new RuntimeException("User Not Authenticated");
		}
		
	}

	public AuthToken generateTokenFromRefresh(String userName) {
		String token=jwtService.generateToke(userName);
		Date expiry=jwtService.expiry(token);
		RefreshToken refreshToken=jwtService.generateRefreshToken(userName);
		return AuthToken.builder()
				.token(token).userName(userName)
				.expiry(expiry).refreshToken(refreshToken.getRefreshToken()).build();		
	}
}
