package com.rbac.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbac.domain.TokenPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapperDisableUnknownProperties;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("authorization");
        token = token.substring(7);
        String[] parts = token.split("\\.");
        String payloadString = new String(Base64.getDecoder().decode(parts[1]));

        TokenPayload tokenPayload = objectMapperDisableUnknownProperties.readValue(payloadString, TokenPayload.class);

        Authentication authentication = this.getAuthentication(payloadString, tokenPayload);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().matches("/swagger-ui/.*$");
    }

    public Authentication getAuthentication(String token, TokenPayload tokenPayload) {
        Collection<? extends GrantedAuthority> authorities = tokenPayload.getData().get(0).getRoles().stream()
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User principal = new User(tokenPayload.getSub(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
