package com.valex.config;

import com.valex.domain.filter.JWTValidateFilter;
import com.valex.domain.filter.JWTAuthenticationFilter;
import com.valex.service.UserAuthService;
import com.valex.utils.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserAuthService userDetailService;

  public SecurityConfig(UserAuthService userDetailService) {
    this.userDetailService = userDetailService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.
        userDetailsService(this.userDetailService)
        .passwordEncoder(Encoder.getNewEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.
        csrf().disable()
        .authorizeRequests()
          .antMatchers(HttpMethod.POST, "/login").permitAll()
          .antMatchers(HttpMethod.POST, "/users").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilter( new JWTAuthenticationFilter( authenticationManager() ) )
        .addFilter( new JWTValidateFilter( authenticationManager() ) )
        .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource () {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
    source.registerCorsConfiguration("/**", corsConfiguration);

    return source;
  }

  @Override
  public void configure (WebSecurity web) throws Exception {
    web.ignoring().antMatchers(
        "/v2/api-docs",
        "/configuration/ui",
        "/swagger-resources/**",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**"
    );
  }
}
