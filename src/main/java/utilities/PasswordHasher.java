package utilities;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {

    public static String hash(String password) throws Exception {
        int iterations = 10000;
        int keyLength = 256;
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        byte[] hashedPassword = factory.generateSecret(spec).getEncoded();

        String saltString = Base64.getEncoder().encodeToString(salt);
        String hashedPasswordString = Base64.getEncoder().encodeToString(hashedPassword);

        return saltString + "$" + hashedPasswordString;
    }

    public static boolean validatePassword(String password, String storedHash) throws Exception {
        String[] parts = storedHash.split("\\$");
        String saltString = parts[0];
        String storedHashedPassword = parts[1];

        byte[] salt = Base64.getDecoder().decode(saltString);

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hashedPassword = factory.generateSecret(spec).getEncoded();

        String hashedPasswordString = Base64.getEncoder().encodeToString(hashedPassword);

        return storedHashedPassword.equals(hashedPasswordString);
    }
}
