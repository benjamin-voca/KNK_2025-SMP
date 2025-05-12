package pages;

import utilities.PasswordHasher;

public class PasswordTest {
    public static void main(String[] args) throws Exception {
        String pass = "prof1234";
        String hashed = PasswordHasher.hash(pass);
        System.out.println(hashed);
    }
}
