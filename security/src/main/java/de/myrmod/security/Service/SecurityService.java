package de.myrmod.security.Service;

public interface SecurityService {
	boolean isAuthenticated();
	void autoLogin(String name, String password);
}
