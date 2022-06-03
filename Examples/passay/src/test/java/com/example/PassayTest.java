package com.example;

import org.junit.jupiter.api.Test;
import org.passay.*;

import static org.junit.jupiter.api.Assertions.*;

public class PassayTest {
    PasswordValidator validator = new PasswordValidator(
            // Length should be between 8 and 50
            new LengthRule(8, 50),

            // Contains at least two uppercase characer
            new CharacterRule(EnglishCharacterData.UpperCase, 2),

            // Contains at least three digit character
            new CharacterRule(EnglishCharacterData.Digit, 3),

            // Contains at least one symbol (special character)
            new CharacterRule(EnglishCharacterData.Special, 1),

            // Doesn't contain a sequence of 5 or more characters
            new IllegalSequenceRule(EnglishSequenceData.Numerical, 3, false),

            // Doesn't contain whitespaces
            new WhitespaceRule()
    );

    @Test
    void testInvalidPasswords() {
        RuleResult ruleResult = validator.validate(new PasswordData("password"));
        assertFalse(ruleResult.isValid());
        assertEquals(3, validator.getMessages(ruleResult).size());
        assertEquals("Password must contain 2 or more uppercase characters.", validator.getMessages(ruleResult).get(0));
        assertEquals("Password must contain 3 or more digit characters.", validator.getMessages(ruleResult).get(1));
        assertEquals("Password must contain 1 or more special characters.", validator.getMessages(ruleResult).get(2));

        ruleResult = validator.validate(new PasswordData("DifficultPassword!123"));
        assertFalse(ruleResult.isValid());
        assertEquals(1, validator.getMessages(ruleResult).size());
        assertEquals("Password contains the illegal numerical sequence '123'.", validator.getMessages(ruleResult).get(0));
    }

    @Test
    void testValidPasswords() {
        RuleResult ruleResult = validator.validate(new PasswordData("AB721$MORETEST"));
        assertTrue(ruleResult.isValid());

        ruleResult = validator.validate(new PasswordData("a83by4oYUAp23#(%V*N"));
        assertTrue(ruleResult.isValid());
    }
}
