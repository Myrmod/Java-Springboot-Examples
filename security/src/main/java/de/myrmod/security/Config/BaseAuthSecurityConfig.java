package de.myrmod.security.Config;

import de.myrmod.security.Service.UserPrincipalService;
import de.myrmod.security.Utility.EncryptionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class BaseAuthSecurityConfig {

	private final UserDetailsService userDetailsService;
	private final EncryptionUtility encryptionUtility;

	@Autowired
	public BaseAuthSecurityConfig(UserPrincipalService userPrincipalService, EncryptionUtility encryptionUtility) {
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
}
