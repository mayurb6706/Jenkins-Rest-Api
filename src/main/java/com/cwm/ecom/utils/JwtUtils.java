package com.cwm.product.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

	@Value("${app.secret")
	private String secret;

	public String gernerateToken(String subject) {
//		if (secret == null || secret.isEmpty()) {
//			throw new IllegalStateException("Secret key must be configured");
//		}
		return Jwts.builder().setSubject(subject).setIssuer("Mayur").setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(500)))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	// Read Claims
	public Claims readClaims(String token) {
//		 if (secret == null || secret.isEmpty()) {
//	            throw new IllegalStateException("Secret key must be configured");
//	        }
//		 else
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token, String username) {
		String usernameInToken = getUsername(token);
		return (usernameInToken.equals(username) && !isTokenExpired(token));
	}

	// 6. Check Current and Exp Date
	private boolean isTokenExpired(String token) {
		final Date expiration = getExpDate(token);
		return expiration.before(new Date());
	}

	public String getUsername(String token) {
		return readClaims(token).getSubject();
	}

	// 3. read ExpDate
	public Date getExpDate(String token) {
		return readClaims(token).getExpiration();
	}

}
