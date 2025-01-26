package de.myrmod.security.Utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class EncryptionUtility {
  public PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

  @Value("${myrmod.environment.encryptor.salt}")
  private final String encryptorSalt = "noSaltSet";

  @Value("${myrmod.environment.encryptor.password}")
  private final String encryptorPassword = "noPasswordSet";

  public EncryptionUtility() {
  }

  public String encryptOneWay(String string) {
    return passwordEncoder.encode(string);
  }

  public String encryptTwoWay(String string) {
    TextEncryptor textEncryptor = Encryptors.text(encryptorPassword,
        new String(Hex.encode((encryptorSalt.getBytes(StandardCharsets.UTF_8)))));

    return textEncryptor.encrypt(string);
  }

  public String decryptTwoWay(String string) {
    TextEncryptor textEncryptor = Encryptors.text(encryptorPassword,
        new String(Hex.encode((encryptorSalt.getBytes(StandardCharsets.UTF_8)))));

    return textEncryptor.decrypt(string);
  }

}
