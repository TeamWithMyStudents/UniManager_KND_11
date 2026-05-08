package ua.knd11.util;

import com.password4j.Hash;
import com.password4j.Password;

/**
 * A final utility class providing static methods for data validation and security.
 * It handles format checks for user attributes, salary constraints, and password hashing
 * using the password4j library.
 */
public final class FieldValidator {

    /**
     * Private constructor to prevent instantiation of this utility class.
     * @throws UnsupportedOperationException if an attempt is made to instantiate this class.
     */
    private FieldValidator() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Internal helper to verify that a string is neither null nor empty/blank.
     * @param field the name of the field being validated (for error messaging)
     * @param value the string value to check
     * @throws IllegalArgumentException if the value is null or empty
     */
    private static void validateNonEmptyString(String field, String value) throws IllegalArgumentException {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is null or empty");
        }
    }

    /**
     * Validates that a string contains only alphabetic characters, spaces, apostrophes, or hyphens.
     * Supports both English and Ukrainian (Cyrillic) character sets.
     * @param field the name of the field being validated
     * @param value the string value to check
     * @throws IllegalArgumentException if the string contains invalid characters or exceeds 100 characters
     */
    public static void validateAlphabeticString(String field, String value) throws IllegalArgumentException {
        validateNonEmptyString(field, value);

        String regex = "^(?=.{1,100}$)[A-Za-zА-Яа-яЁёЄєҐґЇїІі][A-Za-zА-Яа-яЁёЄєҐґЇїІі\\s'\\-]*$";
        if (!value.matches(regex)) throw new IllegalArgumentException(field + " has invalid characters");
    }

    /**
     * Validates an academic group identifier.
     * Allows English/Ukrainian characters, numbers, underscores, and hyphens.
     * @param value the group string to validate
     * @throws IllegalArgumentException if the format is invalid or exceeds 100 characters
     */
    public static void validateGroup(String value) throws IllegalArgumentException {
        validateNonEmptyString("Group", value);

        String regex = "^(?=.{1,100}$)[A-Za-zА-Яа-яЁёЄєҐґЇїІі0-9_-]+$";
        if (!value.matches(regex)) throw new IllegalArgumentException("Group has invalid characters");
    }

    /**
     * Validates an email address against a standard pattern.
     * Ensures the total length is under 254 characters and the local part is under 64 characters.
     * @param value the email string to validate
     * @throws IllegalArgumentException if the email does not match the required regex format
     */
    public static void validateEmail(String value) throws IllegalArgumentException {
        validateNonEmptyString("Email", value);

        // regex matches emails that have length between 1 and 254 characters
        // and have 1 to 64 characters before the @ symbol
        // allows only English characters, numbers, and special characters
        // that can be used in email addresses
        String regex = "^(?=.{1,254}$)(?=.{1,64}@)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!value.matches(regex)) throw new IllegalArgumentException("Email doesn't match regex");
    }

    /**
     * Validates a raw password for security requirements.
     * Bypasses checks if the password is already in a protected (hashed) format.
     * Requires at least 8 characters consisting of alphanumeric and allowed special characters.
     * @param value the password string to validate
     * @throws IllegalArgumentException if the password is too short or contains forbidden characters
     */
    public static void validatePassword(String value) throws IllegalArgumentException {
        validateNonEmptyString("Password", value);
        if (isPasswordProtected(value)) return;

        String charactersRegex = "^[-!@#$%^&*.A-Za-z\\d]{8,}$";
        if (!(value.length() >= 8)) throw new IllegalArgumentException("Too short password!");
        if (!value.matches(charactersRegex)) throw new IllegalArgumentException("Invalid characters in password");
    }

    /**
     * Checks if a string represents a hashed (protected) password.
     * Heuristic check: looks for a length of 44 characters ending with a Base64 padding '='.
     * @param value the string to check
     * @return true if the string appears to be a protected hash, false otherwise
     */
    public static Boolean isPasswordProtected(String value) {
        validateNonEmptyString("Password", value);
        return value.length() == 44 && value.endsWith("=");
    }

    /**
     * Validates that a salary amount is a positive, finite numeric value.
     * @param value the double salary value to check
     * @throws IllegalArgumentException if the value is NaN, infinite, or less than or equal to zero
     */
    public static void validateSalary(double value) throws IllegalArgumentException {
        if (Double.isNaN(value) || Double.isInfinite(value) || value <= 0) {
            throw new IllegalArgumentException("Salary must be a positive finite number");
        }
    }

    /**
     * Validates that a unique ID is a positive integer.
     * @param value the integer ID to check
     * @throws IllegalArgumentException if the ID is zero or negative
     */
    public static void validateId(int value) throws IllegalArgumentException {
        if (value <= 0) {
            throw new IllegalArgumentException("ID must be a positive number.");
        }
    }

    /**
     * Validates that a student score falls within the standard 0-100 range.
     * @param value the integer score to check
     * @throws IllegalArgumentException if the score is outside the allowed boundaries
     */
    public static void validateScore(int value) throws IllegalArgumentException {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("The score should be between 0 and 100.");
        }
    }

    /**
     * Hashes a raw password string using PBKDF2 with a specific salt and pepper.
     * This method secures the password for storage in the database.
     * @param value the raw password to protect
     * @return a hashed string representation of the password
     */
    public static String makeProtectedPassword(String value) {
        //noinspection SpellCheckingInspection
        Hash password = Password.hash(value)
                .addPepper("Uni-hddjtf") // random characters
                .addSalt("Uni-fktjgyu") // random characters
                .withPBKDF2();
        return password.getResult();
    }
}