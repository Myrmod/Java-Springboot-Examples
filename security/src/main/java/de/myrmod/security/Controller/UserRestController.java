package de.myrmod.security.Controller;

import de.myrmod.security.Exception.ResourceNotFoundException;
import de.myrmod.security.Model.User;
import de.myrmod.security.Repository.UserRepository;
import de.myrmod.security.Service.UserPrincipalService;
import de.myrmod.security.Utility.EncryptionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/api/v1/users/")
@RestController
public class UserRestController {

	private final UserRepository userRepository;
	private final EncryptionUtility encryptionUtility;
	private final UserPrincipalService userPrincipalService;

	@Value("${myrmod.environment.encryptor.salt}")
	private String encryptorSalt;

	@Value("${myrmod.environment.encryptor.password}")
	private String encryptorPassword;

	@Autowired
	public UserRestController(UserRepository userRepository, EncryptionUtility encryptionUtility, UserPrincipalService userPrincipalService) {
		this.userRepository = userRepository;
		this.encryptionUtility = encryptionUtility;
		this.userPrincipalService = userPrincipalService;
	}

	@GetMapping("/")
	public List<User> getAll() {
		return userPrincipalService.decryptUsers(userRepository.findAll());
	}

	@GetMapping("/{id}")
	public  User getById(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User does not exists with id " + id));

		return userPrincipalService.decryptUser(user);
	}

	@PutMapping("/")
	public User creatUser(@RequestBody User user) {

		// encryption
		user.setPassword(encryptionUtility.encryptOneWay(user.getPassword()));
		user.setEmail(encryptionUtility.encryptTwoWay(user.getEmail()));

		return userPrincipalService.decryptUser(userRepository.save(user));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteUserById(@PathVariable Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("User does not exists with id " + id));

		userRepository.delete(user);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/{id}")
	public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User userData) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("User does not exists with id " + id));

		if (userData.getEmail() != null)
			user.setEmail(encryptionUtility.encryptTwoWay(userData.getEmail()));
		if (userData.getPassword() != null)
			user.setPassword(encryptionUtility.encryptOneWay(userData.getPassword()));

		User updatedUser = userRepository.save(user);

		return ResponseEntity.ok(userPrincipalService.decryptUser(updatedUser));
	}

}
