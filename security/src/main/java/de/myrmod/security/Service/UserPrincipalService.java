package de.myrmod.security.Service;

import de.myrmod.security.Exception.ResourceNotFoundException;
import de.myrmod.security.Model.User;
import de.myrmod.security.Model.UserPrincipal;
import de.myrmod.security.Repository.UserRepository;
import de.myrmod.security.Utility.EncryptionUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserPrincipalService implements UserDetailsService {

  @Value("${myrmod.environment.init.user.name}")
  private String initialUsername = "noNameSet";

  @Value("${myrmod.environment.init.user.email}")
  private String initialEmail = "noEmailSet";

  @Value("${myrmod.environment.init.user.password}")
  private String initialUserPassword = "noPasswordSet";

  private final UserRepository userRepository;
  private final EncryptionUtility encryptionUtility;

  public UserPrincipalService(UserRepository userRepository, EncryptionUtility encryptionUtility) {
    this.userRepository = userRepository;
    this.encryptionUtility = encryptionUtility;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    User foundUser = findUserByNameAndDecrypt(name);
    if (foundUser == null) {
      throw new ResourceNotFoundException("User with name \"" + name + "\" not found");
    }

		return new UserPrincipal(foundUser);
  }

  private User findUserByNameAndDecrypt(String name) {
    List<User> users = userRepository.findAll();
    for (User user : users) {
      if (encryptionUtility.decryptTwoWay(user.getName()).equals(name)) {
        return decryptUser(user);
      }
    }

    return null;
  }

  public User decryptUser(User user) {
    user.setName(encryptionUtility.decryptTwoWay(user.getName()));
    user.setEmail(encryptionUtility.decryptTwoWay(user.getEmail()));

    return user;
  }

  public List<User> decryptUsers(List<User> users) {
    return users.stream().map(this::decryptUser).toList();
  }
}
