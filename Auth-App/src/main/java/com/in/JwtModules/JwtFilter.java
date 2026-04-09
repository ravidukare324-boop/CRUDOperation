package com.in.JwtModules;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.in.Repository.UserRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter  {
	private final JWTService jwtService;
	private final UserRepo userRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");

		if(header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			if (!jwtService.isAccessToken(token)) {
				filterChain.doFilter(request, response);
				return ;
			}
			try { 
				Jws<Claims> parse = jwtService.parse(token);
				Claims payload = parse.getPayload();
				String subject = payload.getSubject();
				int  id = Integer.parseInt(payload.getSubject());

				userRepo.findById(id).ifPresent(
						user -> {
							if(user.isEnabled()) {
								List<GrantedAuthority> authorities = 
										user.getRoles()== null ? List.of() : 
											user.getRoles().stream().map(role -> 
											new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

								UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
										user.getEmail(),
										null,authorities);

								authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

								if (SecurityContextHolder.getContext().getAuthentication()==null)
									SecurityContextHolder.getContext().setAuthentication(authentication);
							}
						});


			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

filterChain.doFilter(request, response);

	}
}

