package com.example.angular_spring_boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private UserDetailsService userDetailsService;

  public SecurityConfig(UserDetailsService userPrincipalService) {
    this.userDetailsService = userPrincipalService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    /*
     * here custom security filter logic can be implemented
     * The builder pattern can be used as well
     */

    /**
     * we can disable this in case we want to make the requests stateless, eg when
     * making use of a changing session id
     */
    httpSecurity.csrf(customizer -> customizer.disable());

    /**
     * enables the default authentication of spring security
     */
    httpSecurity.authorizeHttpRequests(request -> request.anyRequest().authenticated());

    /**
     * sets up the login form for requests
     * if not used while SessionCreationPolicy.STATELESS is active, we will get a
     * basic authentication form for GET requests
     */
    httpSecurity.formLogin(Customizer.withDefaults());

    /**
     * allows http authentication
     */
    httpSecurity.httpBasic(Customizer.withDefaults());

    /*
     * makes the sessionmanagement stateless, so with every request the session id
     * changes. This will break GET requests
     */
    httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return httpSecurity.build();
  }

  /*
   * @Bean
   * public UserDetailsService userDetailsService() {
   * 
   * This stuff is insecure, but show basic functionality well
   * UserDetails user = User
   * .withDefaultPasswordEncoder()
   * .username("pascal")
   * .password("password")
   * .roles("USER")
   * .build();
   * 
   * return new InMemoryUserDetailsManager(user);
   * 
   * 
   * }
   */

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

    provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
    provider.setUserDetailsService(userDetailsService);

    return provider;
  }

}
