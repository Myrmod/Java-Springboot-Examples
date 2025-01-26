package de.myrmod.security.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Profile("basic")
@Configuration
@EnableWebSecurity
public class BasicAuthSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
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
}
