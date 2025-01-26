package de.myrmod.security.Service;

import de.myrmod.security.Model.User;

public interface UserService {
	void save(User user);

	User findByName(String name);
}
