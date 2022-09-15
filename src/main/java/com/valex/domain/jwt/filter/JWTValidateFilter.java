package com.valex.domain.jwt.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTValidateFilter extends BasicAuthenticationFilter {

  public static final String ATTRIBUTE_HEADER = "Authorization";
  public static final String ATTRIBUTE_PREFIX = "Bearer ";

  public JWTValidateFilter(
      AuthenticationManager authenticationManager
  ) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain
  ) throws IOException, ServletException {

    String prefix = request.getHeader(ATTRIBUTE_HEADER);

    if (prefix == null || !prefix.startsWith(ATTRIBUTE_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    String token = prefix.replace(ATTRIBUTE_PREFIX, "");
    UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    chain.doFilter(request, response);
  }

  private static UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {

    String user = JWT
        .require( Algorithm.HMAC512(JWTAuthenticationFilter.PASSWORD_TOKEN) )
        .build()
        .verify(token)
        .getSubject();

    if (user == null) { return null; }

    return new UsernamePasswordAuthenticationToken(
        user,
        null,
        new ArrayList<>()
    );
  }
}
