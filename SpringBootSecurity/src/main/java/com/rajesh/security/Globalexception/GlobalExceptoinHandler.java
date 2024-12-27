package com.rajesh.security.Globalexception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rajesh.security.exception.JwtTokenExipredException;
import com.rajesh.security.exception.RefreshTokenExipredException;
import com.rajesh.security.exception.UserAlreadyExistException;

import jakarta.servlet.ServletException;

@RestControllerAdvice
public class GlobalExceptoinHandler {

	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<String> userAlredayExists(UserAlreadyExistException exception){
		return new ResponseEntity(exception.getMessage(),HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(RefreshTokenExipredException.class)
	public ResponseEntity<String> refreshTokenInavlid(RefreshTokenExipredException exception){
		return new ResponseEntity(exception.getMessage(),HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler({ServletException.class,JwtTokenExipredException.class, IOException.class})
	public ResponseEntity<String> tokenInavlid(Exception exception){
		return new ResponseEntity(exception.getMessage(),HttpStatus.UNAUTHORIZED);
	}
	
}
