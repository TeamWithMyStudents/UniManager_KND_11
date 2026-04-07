package ua.knd11.util;

/**
 * Utility class for validating field inputs with business rules.
 */
public final class FieldValidator {

    /**
     * Ensures the provided string is neither null nor blank.
     *
     * @param field the label of the validated field used in the exception message
     * @param value the string value to validate
     * @throws IllegalArgumentException if {@code value} is null or contains only whitespace; the exception message is "{@code <field> is null or empty}"
     */
    private static void validateNonEmptyString(String field, String value) throws IllegalArgumentException {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is null or empty");
        }
    }

    /**
     * Prevents instantiation of this utility class.
     *
     * <p>This private constructor always throws an {@link UnsupportedOperationException} to
     * enforce non-instantiability.
     *
     * @throws UnsupportedOperationException always thrown to prevent creating an instance
     */
    private FieldValidator() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Ensures the named field value is non-empty and contains only English or Ukrainian letters, spaces, apostrophes, or hyphens.
     *
     * @param field name used in thrown exception messages
     * @param value value to validate
     * @throws IllegalArgumentException if value is null, blank, or contains invalid characters
     */
    public static void validateAlphabeticString(String field, String value) throws IllegalArgumentException {
        validateNonEmptyString(field, value);

        // regex matches only English and Ukrainian characters
        // Used in fields that don't need too much attention
        String regex = "^(?=.{1,100}$)[A-Za-zА-Яа-яЁёЄєҐґЇїІі][A-Za-zА-Яа-яЁёЄєҐґЇїІі\\s'\\-]*$";
        if (!value.matches(regex)) throw new IllegalArgumentException(field + " has invalid characters");
    }

    /**
     * Validates an academic group name.
     *
     * <p>The name must be 1–100 characters long and may contain English and Ukrainian letters,
     * digits, underscores (`_`), and hyphens (`-`). The value must not be `null` or blank.
     *
     * @param value the group name to validate
     * @throws IllegalArgumentException if the value is `null` or blank, longer than 100 characters,
     *                                  or contains characters other than English/Ukrainian letters,
     *                                  digits, underscore, or hyphen
     */
    public static void validateGroup(String value) throws IllegalArgumentException {
        validateNonEmptyString("Group", value);

        // regex matches only English and Ukrainian characters, numbers, and special characters
        // Used in fields that doesn't need too much attention
        String regex = "^(?=.{1,100}$)[A-Za-zА-Яа-яЁёЄєҐґЇїІі0-9_-]+$";
        if (!value.matches(regex)) throw new IllegalArgumentException("Group has invalid characters");
    }

    /**
     * Checks that the provided email address is non-empty and conforms to the required email format.
     *
     * @param value the email address to validate
     * @throws IllegalArgumentException if the email is null, blank, or does not match the required format
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
     * Ensures the password is not blank and meets the validator's format rules.
     *
     * <p>The password must be at least 8 characters long and contain only English letters,
     * digits, and the special characters - ! @ # $ % ^ & * .</p>
     *
     * @param value the password to validate
     * @throws IllegalArgumentException if the password is null, blank, shorter than 8 characters,
     *                                  or contains characters outside the allowed set
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
     * Ensures the salary is a positive finite number.
     *
     * @param value the salary amount to validate
     * @throws IllegalArgumentException if `value` is NaN, infinite, or less than or equal to zero
     */
    public static void validateSalary(double value) throws IllegalArgumentException {
        if (Double.isNaN(value) || Double.isInfinite(value) || value <= 0) {
            throw new IllegalArgumentException("Salary must be a positive finite number");
        }
    }

    /**
     * Ensure an identifier is a positive integer.
     *
     * @param value the identifier to validate
     * @throws IllegalArgumentException if {@code value} is less than or equal to zero
     */
    public static void validateId(int value) throws IllegalArgumentException {
        if (value <= 0) {
            throw new IllegalArgumentException("ID must be a positive number.");
        }
    }

    /**
     * Ensures the provided score is within the inclusive range 0 to 100.
     *
     * @param value the score to validate (expected 0–100)
     * @throws IllegalArgumentException if {@code value} is less than 0 or greater than 100
     */
    public static void validateScore(int value) throws IllegalArgumentException {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("The score should be between 0 and 100.");
        }
    }
}
