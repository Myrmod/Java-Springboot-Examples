package de.myrmod.security.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Profile("form")
@Configuration
@EnableWebSecurity
public class FormAuthSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
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
	public WebSecurityCustomizer removeSecurityFromPublicDirectory() {
		return (web) -> web.ignoring().requestMatchers("/public/**");
	}
}
