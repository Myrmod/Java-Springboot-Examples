package com.example.angular_spring_boot.Utility;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtility {
  private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Value("${example.environment.encryptor.salt}")
  private String encryptorSalt = "noSaltSet";

  @Value("${example.environment.encryptor.password}")
  private String encryptorPassword = "noPasswordSet";

  public EncryptionUtility() {
  }

  public String encryptOneWay(String string) {
    return bCryptPasswordEncoder.encode(string);
  }

  public String encryptTwoWay(String string) {
    TextEncryptor textEncryptor = Encryptors.text(encryptorPassword,
        new String(Hex.encode((encryptorSalt.getBytes(Charset.forName("utf-8"))))));

    return textEncryptor.encrypt(string);
  }

  public String decryptTwoWay(String string) {
    TextEncryptor textEncryptor = Encryptors.text(encryptorPassword,
        new String(Hex.encode((encryptorSalt.getBytes(Charset.forName("utf-8"))))));

    return textEncryptor.decrypt(string);
  }

}
