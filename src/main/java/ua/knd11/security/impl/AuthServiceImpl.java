package ua.knd11.security.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthServiceImpl implements AuthService {
    private ArrayList<User> registeredUsers = new ArrayList<>();

    public void registration(User user) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(regex);
        for (String text : user.getEmail().split(" ") ) {
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()){
                user.setEmail(text);
            } else {
                throw new IllegalArgumentException("Invalid email address or password");
            }
        }

        if (user.getPassword().length() >= 8) {
            user.setPassword(user.getPassword());
        } else
            throw new IllegalArgumentException("Invalid email address or password");

        registeredUsers.add(user);
    }

    public ArrayList<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(ArrayList<User> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }
}
