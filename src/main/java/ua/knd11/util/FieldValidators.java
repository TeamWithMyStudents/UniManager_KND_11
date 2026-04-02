package ua.knd11.util;

import java.util.Objects;

/**
 * Field validators is a utility class for validating user inputs and fields for models
 * has static methods for validating fields in models and user inputs
 */
public final class FieldValidators {

    /**
     * Normalizer method used basically for validating fields in models
     * Fields such as "Name", "Surname", "Department", "Degree", etc.
     *
     * @param string the string to be validated
     * @param type   the type of the field to be validated for error messages
     * @return the normalized string
     * @throws IllegalArgumentException if the string is null or blank
     */
    public static String normalizer(String string, String type) {
        Objects.requireNonNull(string, type + " must not be null");
        string = string.trim();

        if (string.isBlank()) {
            throw new IllegalArgumentException(type + " must not be blank");
        }

        // regex matches only English and Ukrainian characters, numbers, and special characters
        // Used in fields that doesn't need too much attention
        String regex = "^(?=.{1,100}$)[A-Za-zА-Яа-яЁёЄєҐґЇїІі0-9_-]+$";
        if (!string.matches(regex)) throw new IllegalArgumentException("invalid " + type);
        return string;
    }

    /**
     * Credentials validation method used for validating user inputs
     * Fields such as "Email", "Password"
     *
     * @param credential the string to be validated (email or password)
     * @param type       the type of the field to be validated for error messages
     * @return the normalized string
     * @throws IllegalArgumentException if the string is null or blank or invalid
     */
    public static String credentialsValidation(String credential, String type) {
        Objects.requireNonNull(credential, type + " must not be null");

        if (type.equals("Email")) {
            // regex matches emails that have length between 1 and 254 characters
            // and have 1 to 64 characters before the @ symbol
            // allows only English characters, numbers, and special characters
            // that can be used in email addresses
            String regex = "^(?=.{1,254}$)(?=.{1,64}@)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            if (!credential.matches(regex)) throw new IllegalArgumentException("Invalid email address");
        }

        if (type.equals("Password")) {
            // regex matches passwords that have length of at least 8 characters,
            // allows only English characters
            // numbers, and special characters that can be used in passwords
            String regex = "^[-!@#$%^&*.A-Za-z\\d]{8,}$";
            if (!credential.matches(regex)) throw new IllegalArgumentException("Invalid password");
        }
        return credential;
    }

    /**
     * <p>Normalizer for salary checks if salary in {@link ua.knd11.model.Teacher} is a positive finite number
     *
     * @param salary саляри #easter_egg
     * @return salary
     * @throws IllegalArgumentException if salary is not a positive finite number
     */
    public static double normalizerSalary(double salary) {
        if (Double.isNaN(salary) || Double.isInfinite(salary) || salary <= 0) {
            throw new IllegalArgumentException("Salary must be a positive finite number");
        }
        return salary;
    }

    /**
     * is a null or blank checks if a string is null or blank
     * and returns true if the string is null or blank, false otherwise
     *
     * @param str   the string to be checked
     * @param query the query to be checked
     * @return true if the string is invalid, false otherwise
     */
    public static boolean isNullOrBlank(String str, String query) {
        if (str == null || str.isBlank()) {
            System.out.println(query + " must not be null or blank");
            return true;
        }
        return false;
    }
}
