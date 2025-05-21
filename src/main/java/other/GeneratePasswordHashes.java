package other;

import utilities.PasswordHasher;

public class GeneratePasswordHashes {
    public static void main(String[] args) throws Exception {
        String password = "assessor123";
        String hashed = PasswordHasher.hash(password);
        System.out.println("Hash for assessor123: " + hashed);
    }
}