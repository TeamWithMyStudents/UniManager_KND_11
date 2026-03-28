package ua.knd11.util;

import ua.knd11.model.User;
import ua.knd11.service.UserService;
import ua.knd11.service.impl.UserServiceImpl;

import java.util.Objects;

public class FieldValidators {
    public static String normalizer(String string, String type) {
        string = string.trim();

        Objects.requireNonNull(string, type + " must not be null");
        if (string.isBlank()) throw new IllegalArgumentException(type + " must not be blank");

        String regex = "^(?=.{1,100}$)[A-Za-zА-Яа-яЁёЄєҐґЇїІі0-9_-]+$";
        if (!string.matches(regex)) throw new IllegalArgumentException("invalid " + type);

        return string;
    }

    public static String credentialsValidation(String credential, String type) {
        UserService userService = new UserServiceImpl();
        if (type.equals("Email")) {
            String regex = "^(?=.{1,254}$)(?=.{1,64}@)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            if (!credential.matches(regex)) throw new IllegalArgumentException("Invalid email address");

            for (User user : userService.getAll()) {
                if (user.getEmail().equals(credential)) throw new IllegalArgumentException("Email already exists");
            }
        }

        if (type.equals("Password")) {
            String regex = "^[-!@#$%^&*.A-Za-z\\d]{8,}$";
            if (!credential.matches(regex)) throw new IllegalArgumentException("Invalid password");
        }
        return credential;
    }

    public static double normalizerSalary(double salary) {
        if (Double.isNaN(salary) || Double.isInfinite(salary) || salary <= 0) {
            throw new IllegalArgumentException("Salary must be a positive finite number");
        }
        return salary;
    }

    public static boolean isNullOrBlank(String str, String query) {
        if (str == null || str.isBlank()) {
            System.out.println(query + " must not be null or blank");
            return true;
        }
        return false;
    }
}
