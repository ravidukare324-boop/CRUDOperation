package com.in.JwtModules;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.security.auth.Subject;

import lombok.RequiredArgsConstructor;

import org.apache.catalina.User;
import org.aspectj.weaver.GeneratedReferenceTypeDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.in.DTOs.UserDto;
import com.in.Entitty.Role;
import com.in.Entitty.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Service
public class JWTService {

	private final SecretKey key;
	private final long refreshTtlSeconds;
	private final long accessTtlSeconds;
	private final String issuer;

	public JWTService(
			@Value("${security.jwt.secret}") String secret,
			@Value("${security.jwt.refresh-ttl-seconds}") long refreshTtlSeconds,
			@Value("${security.jwt.access-ttl-seconds}") long accessTtlSeconds,
			@Value("${security.jwt.issuer}") String issuer) {



		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.refreshTtlSeconds= refreshTtlSeconds;
		this.accessTtlSeconds = accessTtlSeconds;
		this.issuer = issuer;

	}
	
	public String generateToken(Users user){

		Instant now = Instant.now();
		List<String> roles = user.getRoles()==null ? List.of():user.getRoles()
				.stream()
				.map(Role::getName)
				.toList();

		return Jwts.builder()
				.id(UUID.randomUUID().toString())
				.subject(user.getUsername().toString())
				.issuer(issuer)
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plusSeconds(accessTtlSeconds)))
				.claims(Map.of(
						"email", user.getEmail(),
						"roles", roles,
						"typ", "access"
						 )
						)
				.signWith(key)
				.compact();
	}

	public String generateRefreshToken(Users user , String jti) {

	  Instant now = Instant.now();
		return Jwts.builder().
				id(UUID.randomUUID().toString())
				.subject(user.getUsername().toString())
				.issuer(issuer)
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plusSeconds(refreshTtlSeconds)))
				.claims(
						Map.of(
								"type","refresh"))
				.signWith(key).compact();
	}




		public Jws<Claims> parse(String token){
		
				return Jwts.parser().
						verifyWith(key).build().
						parseSignedClaims(token);
			}
			
			
		

		public boolean isAccessToken(String token) {
			Claims c = parse(token).getPayload();
			return "access".equals(c.get("typ"));
		}
		public boolean isRefreshToken(String token) {
			Claims c = parse (token).getPayload();
			return "refresh".equals(c.get("typ"));
		}
		public UUID getUserId(String token)
		{
			Claims c = parse(token)
					.getPayload();
			return UUID.fromString(c.getSubject());
		}

		public String getJti(String token) {
			return parse(token).getPayload()
					.getId();
		}
	}
