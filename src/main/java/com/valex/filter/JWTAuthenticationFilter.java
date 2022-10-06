package com.valex.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valex.domain.exception.UnauthorizedException;
import com.valex.domain.model.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  public static final int EXPIRATION_TOKEN = 600_000_000;
  public static final String PASSWORD_TOKEN = "fe55b9fc-f2c7-4189-830f-53127bd3ff16";
  private final AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws AuthenticationException {

    try {
      User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

      return this.authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              user.getEmail(),
              user.getPassword(),
              new ArrayList<>()
          )
      );

    } catch (IOException e) {
      throw new UnauthorizedException("Unauthorized user!", e);
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain, Authentication authResult
  ) throws IOException, ServletException {

    User user = (User) authResult.getPrincipal();

    String token = JWT.create()
        .withSubject( user.getEmail() )
        .withExpiresAt( new Date ( System.currentTimeMillis() + EXPIRATION_TOKEN ) )
        .sign( Algorithm.HMAC512(PASSWORD_TOKEN) );

    response.getWriter().write(token);
    response.getWriter().flush();
  }
}
