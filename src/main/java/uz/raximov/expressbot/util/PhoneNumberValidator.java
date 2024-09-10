package uz.raximov.expressbot.util;

import java.util.regex.Pattern;

public class PhoneNumberValidator {

    // Define the regex pattern for the phone number format
    private static final String PHONE_NUMBER_PATTERN = "^\\+998\\d{9}$";
    private static final Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);

    // Method to validate the phone number
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false; // Null check
        }
        return pattern.matcher(phoneNumber).matches(); // Check if it matches the pattern
    }
}

