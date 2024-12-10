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

        strength = zxcvbn.measure("e!f.M&@6ycvN3E[s)nPp=7\n");
        assertEquals(4, strength.getScore());
        assertEquals(3.6E24, strength.getCrackTimeSeconds().getOnlineThrottling100perHour());
    }

}
