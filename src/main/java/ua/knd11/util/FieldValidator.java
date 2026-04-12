package ua.knd11.util;

import com.password4j.Hash;
import com.password4j.Password;

public final class FieldValidator {

    private FieldValidator() {
        throw new UnsupportedOperationException("Utility class");
    }

    private static void validateNonEmptyString(String field, String value) throws IllegalArgumentException {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is null or empty");
        }
    }

    public static void validateAlphabeticString(String field, String value) throws IllegalArgumentException {
        validateNonEmptyString(field, value);

        // regex matches only English and Ukrainian characters
        // Used in fields that don't need too much attention
        String regex = "^(?=.{1,100}$)[A-Za-zА-Яа-яЁёЄєҐґЇїІі][A-Za-zА-Яа-яЁёЄєҐґЇїІі\\s'\\-]*$";
        if (!value.matches(regex)) throw new IllegalArgumentException(field + " has invalid characters");
    }

    public static void validateGroup(String value) throws IllegalArgumentException {
        validateNonEmptyString("Group", value);

        // regex matches only English and Ukrainian characters, numbers, and special characters
        // Used in fields that doesn't need too much attention
        String regex = "^(?=.{1,100}$)[A-Za-zА-Яа-яЁёЄєҐґЇїІі0-9_-]+$";
        if (!value.matches(regex)) throw new IllegalArgumentException("Group has invalid characters");
    }

    public static void validateEmail(String value) throws IllegalArgumentException {
        validateNonEmptyString("Email", value);

        // regex matches emails that have length between 1 and 254 characters
        // and have 1 to 64 characters before the @ symbol
        // allows only English characters, numbers, and special characters
        // that can be used in email addresses
        String regex = "^(?=.{1,254}$)(?=.{1,64}@)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!value.matches(regex)) throw new IllegalArgumentException("Email doesn't match regex");
    }

    public static void validatePassword(String value) throws IllegalArgumentException {
        validateNonEmptyString("Password", value);
        if (isPasswordProtected(value)) return;

        // regex matches passwords that have length of at least 8 characters,
        // allows only English characters
        // numbers, and special characters that can be used in passwords
        String charactersRegex = "^[-!@#$%^&*.A-Za-z\\d]{8,}$";
        if (!(value.length() >= 8)) throw new IllegalArgumentException("Too short password!");
        if (!value.matches(charactersRegex)) throw new IllegalArgumentException("Invalid characters in password");
    }

    public static Boolean isPasswordProtected(String value) {
        validateNonEmptyString("Password", value);
        return value.length() == 44 && value.endsWith("=");
    }

    public static void validateSalary(double value) throws IllegalArgumentException {
        if (Double.isNaN(value) || Double.isInfinite(value) || value <= 0) {
            throw new IllegalArgumentException("Salary must be a positive finite number");
        }
    }

    public static void validateId(int value) throws IllegalArgumentException {
        if (value <= 0) {
            throw new IllegalArgumentException("ID must be a positive number.");
        }
    }

    public static void validateScore(int value) throws IllegalArgumentException {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("The score should be between 0 and 100.");
        }
    }

    public static String makeProtectedPassword(String value) {
        //noinspection SpellCheckingInspection
        Hash password = Password.hash(value)
                .addPepper("Uni-hddjtf") // random characters
                .addSalt("Uni-fktjgyu") // random characters
                .withPBKDF2();
        return password.getResult();
    }


}
