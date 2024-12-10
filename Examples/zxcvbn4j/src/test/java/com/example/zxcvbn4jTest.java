package com.example;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class zxcvbn4jTest {

    private Zxcvbn zxcvbn = new Zxcvbn();

    @Test
    void testPasswords() {
        Strength strength = zxcvbn.measure("password");
        assertEquals(0, strength.getScore());
        assertEquals(108.0, strength.getCrackTimeSeconds().getOnlineThrottling100perHour());

        strength = zxcvbn.measure("Password!0");
        assertEquals(1, strength.getScore());
        assertEquals(720000.0, strength.getCrackTimeSeconds().getOnlineThrottling100perHour());

        String password = "e!f.M&@6ycvN3E[s)nPp=7";
        assertEquals(22, password.length());
        strength = zxcvbn.measure(password);
        assertEquals(4, strength.getScore());
        assertEquals(3.6E23, strength.getCrackTimeSeconds().getOnlineThrottling100perHour());

        password = "this is just a very lo";
        assertEquals(22, password.length());
        strength = zxcvbn.measure(password);
        assertEquals(4, strength.getScore());
        assertEquals(8.6486436E19, strength.getCrackTimeSeconds().getOnlineThrottling100perHour());

        strength = zxcvbn.measure("this is just a very long text without special characters or anything");
        assertEquals(4, strength.getScore());
        assertEquals(8.791855783542682E49, strength.getCrackTimeSeconds().getOnlineThrottling100perHour());
    }

}
