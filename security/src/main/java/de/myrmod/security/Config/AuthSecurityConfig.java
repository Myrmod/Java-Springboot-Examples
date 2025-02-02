package de.myrmod.security.Config;

import de.myrmod.security.Service.UserPrincipalService;
import de.myrmod.security.Utility.EncryptionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthSecurityConfig {

	private final UserDetailsService userDetailsService;
	private final EncryptionUtility encryptionUtility;

	@Autowired
	public AuthSecurityConfig(UserPrincipalService userPrincipalService, EncryptionUtility encryptionUtility) {
		this.userDetailsService = userPrincipalService;
		this.encryptionUtility = encryptionUtility;
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setPasswordEncoder(encryptionUtility.passwordEncoder);
		provider.setUserDetailsService(userDetailsService);

		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	@ConditionalOnProperty(name="myrmod.environment.authentication", havingValue = "httpBasic")
	public SecurityFilterChain basicAuthSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.authorizeHttpRequests(auth -> auth
				.anyRequest().authenticated() // All requests require authentication
			)
			/** using CURL the endpoints can be requested using:
			 * curl -u user:password http://localhost:8080/api/v1/users
			 */
			.httpBasic(Customizer.withDefaults()); // Enable Basic Authentication

		return httpSecurity.build();
	}

	@Bean
	@ConditionalOnProperty(name="myrmod.environment.authentication", havingValue = "formLogin")
	public SecurityFilterChain formAuthSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		/*
		 * here custom security filter logic can be implemented
		 * The builder pattern can be used as well
		 */
		httpSecurity
			.authorizeHttpRequests(auth -> auth
				.anyRequest()
				.authenticated()
			)
			/** You can test the login form at:
			 *  http://localhost:8080/login
			 *  Credentials: user:password
			 */
			.formLogin(form -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout(LogoutConfigurer::permitAll);

		return httpSecurity.build();
	}

	@Bean
	@ConditionalOnProperty(name="myrmod.environment.authentication", havingValue = "formLogin")
	public WebSecurityCustomizer removeSecurityFromPublicDirectory() {
		return (web) -> web.ignoring().requestMatchers("/public/**");
	}

}
