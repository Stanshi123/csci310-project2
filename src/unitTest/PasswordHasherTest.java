package unitTest;

import data.PasswordHasher;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordHasherTest {
    @Test
    public void hashConstructWith31() {
        boolean ifThrow = false;
        try {
            new PasswordHasher(31);
        } catch (Exception e) {
            ifThrow = true;
        }
        assertTrue(ifThrow);
    }

    @Test
    public void hashConstructWithNeg1() {
        boolean ifThrow = false;
        try {
            new PasswordHasher(-1);
        } catch (Exception e) {
            ifThrow = true;
        }
        assertTrue(ifThrow);
    }

    @Test
    public void hashConstructor() {
        boolean ifThrow = false;
        try {
            new PasswordHasher(30);
        } catch (Exception e) {
            ifThrow = true;
        }
        assertFalse(ifThrow);
    }

    @Test
    public void hashTest() {
        char[] password = {'a','p','p','l','e'};
        PasswordHasher passwordHasher = new PasswordHasher(5);
        assertFalse(passwordHasher.hash(password).equals("apple"));
    }

    @Test
    public void authenticateTest() throws Exception {
        char[] password = {'a','p','p','l','e'};
        PasswordHasher passwordHasher = new PasswordHasher(5);
        String token = passwordHasher.hash(password);
        System.out.println(token);
        assertTrue(passwordHasher.authenticate(password,token));
    }

}