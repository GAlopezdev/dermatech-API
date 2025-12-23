package com.dermatech.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    String path = request.getRequestURI();
	    if (path.startsWith("/api/usuarios/login") || path.startsWith("/api/usuarios/registrar")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    String authHeader = request.getHeader("Authorization");
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        String jwt = authHeader.substring(7);
	        Claims claims = jwtUtil.obtenerClaims(jwt);
	        String username = claims.getSubject();
	        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
	                Collections.singleton(new SimpleGrantedAuthority("USER")));
	        SecurityContextHolder.getContext().setAuthentication(auth);
	    }

	    filterChain.doFilter(request, response);
	}
}