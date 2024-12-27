package com.rajesh.security.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rajesh.security.exception.JwtTokenExipredException;
import com.rajesh.security.exception.RefreshTokenExipredException;
import com.rajesh.security.model.RefreshToken;
import com.rajesh.security.model.User;
import com.rajesh.security.repo.RefreshTokenRepo;
import com.rajesh.security.repo.UserRepo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final Logger logger=LoggerFactory.getLogger(JwtService.class);
//	@Value("${key}")
	private String secretKey;

	@Autowired
	private RefreshTokenRepo refreshTokenRepo;
	@Autowired
	private UserRepo userRepo;

	public JwtService() {
		try {
			KeyGenerator kg = KeyGenerator.getInstance("hmacSHA256");
			SecretKey key = kg.generateKey();
			secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
			System.out.println("key---- " + secretKey);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String generateToke(String userName) {
		return Jwts.builder().subject(userName).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 1000)).signWith(getKey()).compact();

	}

	public Date expiry(String token) {
		return Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token).getPayload()
				.getExpiration();
	}

	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public boolean isValid(String token) {
		try {
			Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token);
//			return true;
		} catch (RuntimeException e) {
			throw new JwtTokenExipredException("Jwt Token Expired.");
		}
		return true;
	}

	public String getUserNameFromToken(String token) {

		return Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token).getPayload()
				.getSubject();
	}

	public RefreshToken generateRefreshToken(String userName) {

		User user = userRepo.findByUserName(userName);
		RefreshToken refreshTokenDb = user.getRefreshToken();
		if (refreshTokenDb != null ) {
			refreshTokenDb.setExpiry(new Date(System.currentTimeMillis() + 2 * 60 * 1000));
			user.setRefreshToken(refreshTokenDb);
		} else {
			refreshTokenDb = RefreshToken.builder().refreshToken(UUID.randomUUID().toString()).user(user)
					.expiry(new Date(System.currentTimeMillis() + 2 * 60 * 1000)).build();

		}
		refreshTokenDb = refreshTokenRepo.save(refreshTokenDb);
		return refreshTokenDb;
	}

	public RefreshToken verifyRefreshToken(String refreshToken) {
		logger.info("inside validate refresh token :: ");
		RefreshToken refreshTokenDb= refreshTokenRepo.findByRefreshToken(refreshToken);
		Date refreshTokenExpiry = refreshTokenDb.getExpiry();
		if (refreshTokenExpiry.compareTo(new Date()) < 0) {
			logger.info("existing reresh token expired :: ");
			User user=refreshTokenDb.getUser();
			user.setRefreshToken(null);
			userRepo.save(user);
			refreshTokenRepo.delete(refreshTokenDb);
			logger.info("deleted old expired token ::");
			throw new RefreshTokenExipredException("Refresh Token Expired. Please login again");
		}
		return refreshTokenDb;
	}
}
