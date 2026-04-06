package ua.knd11.util;

/**
 * Utility class for validating field inputs with business rules.
 */
public final class FieldValidator {

    private static void validateNonEmptyString(String field, String value) throws IllegalArgumentException {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is null or empty");
        }
    }

    /**
     * Validates that a string contains only alphabetic characters.
     *
     * @param field field name for error messages
     * @param value the string to validate
     * @throws IllegalArgumentException if value is invalid
     */
    public static void validateAlphabeticString(String field, String value) throws IllegalArgumentException {
        validateNonEmptyString(field, value);

        // regex matches only English and Ukrainian characters, numbers, and special characters
        // Used in fields that doesn't need too much attention
        String regex = "^(?=.{1,100}$)[A-Za-zА-Яа-яЁёЄєҐґЇїІі]+$";
        if (!value.matches(regex)) throw new IllegalArgumentException(field + " has invalid characters");
    }

    /**
     * Validates academic group name format.
     *
     * @param value the group name to validate
     * @throws IllegalArgumentException if group name is invalid
     */
    public static void validateGroup(String value) throws IllegalArgumentException {
        validateNonEmptyString("Group", value);

        // regex matches only English and Ukrainian characters, numbers, and special characters
        // Used in fields that doesn't need too much attention
        String regex = "^(?=.{1,100}$)[A-Za-zА-Яа-яЁёЄєҐґЇїІі0-9_-]+$";
        if (!value.matches(regex)) throw new IllegalArgumentException("Group has invalid characters");
    }

    /**
     * Validates email address format.
     *
     * @param value the email to validate
     * @throws IllegalArgumentException if email is invalid
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
     * Validates password strength and format.
     *
     * @param value the password to validate
     * @throws IllegalArgumentException if password is invalid
     */
    public static void validatePassword(String value) throws IllegalArgumentException {
        validateNonEmptyString("Password", value);

        // regex matches passwords that have length of at least 8 characters,
        // allows only English characters
        // numbers, and special characters that can be used in passwords
        String regex = "^[-!@#$%^&*.A-Za-z\\d]{8,}$";
        if (!value.matches(regex)) throw new IllegalArgumentException("Invalid password");
    }

    /**
     * Validate salary.
     *
     * @param value the value
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void validateSalary(double value) throws IllegalArgumentException {
        if (Double.isNaN(value) || Double.isInfinite(value) || value <= 0) {
            throw new IllegalArgumentException("Salary must be a positive finite number");
        }
    }

    /**
     * Validate id.
     *
     * @param value the value
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void validateId(int value) throws IllegalArgumentException {
        if (value <= 0) {
            throw new IllegalArgumentException("ID must be a positive number.");
        }
    }

    /**
     * Validate score.
     *
     * @param value the value
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void validateScore(int value) throws IllegalArgumentException {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("The score should be between 0 and 100.");
        }
    }
}
