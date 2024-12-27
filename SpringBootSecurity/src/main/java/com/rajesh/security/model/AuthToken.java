package com.rajesh.security.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthToken {

	private String token;
	private String userName;
	private Date expiry;
	private String refreshToken;
}
