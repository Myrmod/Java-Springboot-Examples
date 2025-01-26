package de.myrmod.security.Service;

import de.myrmod.security.Model.User;
import de.myrmod.security.Repository.UserRepository;
import de.myrmod.security.Utility.EncryptionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final EncryptionUtility encryptionUtility;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, EncryptionUtility encryptionUtility) {
		this.userRepository = userRepository;
		this.encryptionUtility = encryptionUtility;
	}

	@Override
	public void save(User user) {
		user.setPassword(encryptionUtility.encryptOneWay(user.getPassword()));
		userRepository.save(user);
	}

	@Override
	public User findByName(String name) {
		return userRepository.findByName(name);
	}
}
