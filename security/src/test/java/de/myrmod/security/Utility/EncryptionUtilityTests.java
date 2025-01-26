package de.myrmod.security.Utility;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class EncryptionUtilityTests {

	@Autowired
	EncryptionUtility encryptionUtility;

	@Test
	public void oneWayEncryption() {
		String password = "password";
		String encryptedPassword = encryptionUtility.encryptOneWay(password);

		assertTrue(encryptionUtility.passwordEncoder.matches(password, encryptedPassword));
	}

	@Test
	public void twoWayEncryption() {
		String name = "name";

		String encryptedName = encryptionUtility.encryptTwoWay(name);

		String decryptedName = encryptionUtility.decryptTwoWay(encryptedName);

		assertEquals(decryptedName, name);
	}
}
