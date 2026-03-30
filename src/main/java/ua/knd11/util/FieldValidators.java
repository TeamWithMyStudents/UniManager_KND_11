package ua.knd11.util;

import java.util.Objects;

// Field validators is a utility class for validating user inputs and fields for models
public final class FieldValidators {

    // Normalizer method used basically for validating fields in models
    // Fields such as "Name", "Surname", "Department", "Degree", etc.
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

        // If all conditions (null, blank, regex checks) are met, return the normalized string
        return string;
    }

    // Credentials validation method used for validating user inputs
    // Fields such as "Email", "Password"
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

        // If all conditions (null, blank, regex checks) are met,
        // return the normalized string password or email
        return credential;
    }

    // normalizer for salary is a simple method that checks if salary in Teacher is a positive finite number
    public static double normalizerSalary(double salary) {
        if (Double.isNaN(salary) || Double.isInfinite(salary) || salary <= 0) {
            throw new IllegalArgumentException("Salary must be a positive finite number");
        }
        return salary;
    }

    // is a null or blank method simply checks if a string is null or blank
    // and returns true if the string is null or blank, false otherwise
    public static boolean isNullOrBlank(String str, String query) {
        if (str == null || str.isBlank()) {
            System.out.println(query + " must not be null or blank");
            return true;
        }
        return false;
    }
}
