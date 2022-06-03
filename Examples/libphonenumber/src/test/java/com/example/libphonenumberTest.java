package com.example;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class libphonenumberTest {
    PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    PhoneNumberToCarrierMapper phoneNumberToCarrierMapper = PhoneNumberToCarrierMapper.getInstance();
    PhoneNumberOfflineGeocoder phoneNumberOfflineGeocoder = PhoneNumberOfflineGeocoder.getInstance();

    @Test
    void testDutchNumber() throws NumberParseException {
        String number = "0478 501234";
        PhoneNumber parsedNumber = phoneNumberUtil.parse(number, "NL");
        assertTrue(phoneNumberUtil.isValidNumber(parsedNumber));
        assertEquals("Venray", phoneNumberOfflineGeocoder.getDescriptionForNumber(parsedNumber, Locale.ENGLISH));
        assertEquals("", phoneNumberToCarrierMapper.getNameForNumber(parsedNumber, Locale.ENGLISH));

        number = "0478 01234";
        parsedNumber = phoneNumberUtil.parse(number, "NL");
        assertFalse(phoneNumberUtil.isValidNumber(parsedNumber));

        number = "06 12345678";
        parsedNumber = phoneNumberUtil.parse(number, "NL");
        assertTrue(phoneNumberUtil.isValidNumber(parsedNumber));
        assertEquals("Netherlands", phoneNumberOfflineGeocoder.getDescriptionForNumber(parsedNumber, Locale.ENGLISH));
        assertEquals("KPN", phoneNumberToCarrierMapper.getNameForNumber(parsedNumber, Locale.ENGLISH));


        number = "00316 12345678";
        parsedNumber = phoneNumberUtil.parse(number, "NL");
        assertTrue(phoneNumberUtil.isValidNumber(parsedNumber));

        number = "+316 12345678";
        parsedNumber = phoneNumberUtil.parse(number, "NL");
        assertTrue(phoneNumberUtil.isValidNumber(parsedNumber));

        number = "06 12345";
        parsedNumber = phoneNumberUtil.parse(number, "NL");
        assertFalse(phoneNumberUtil.isValidNumber(parsedNumber));

    }
}
