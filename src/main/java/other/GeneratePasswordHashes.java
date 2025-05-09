package other;

import utilities.PasswordHasher;

public class GeneratePasswordHashes {
    public static void main(String[] args) throws Exception {
        String password = "test123";
        String hashed = PasswordHasher.hash(password);
        System.out.println("Hash for test123: " + hashed);
    }
}