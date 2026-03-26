package ua.knd11.security.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthServiceImpl implements AuthService {
    private final ArrayList<User> registeredUsers = new ArrayList<>();

    public void registration(User user) {
        String email = user.getEmail();
        String regex = "^(?=.{1,254}$)(?=.{1,64}@)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (user.getPassword().length() < 8) throw new IllegalArgumentException("Invalid password");

        registeredUsers.add(user);
    }

    public ArrayList<User> getRegisteredUsers() {
        return new ArrayList<>(registeredUsers);
    }
}
