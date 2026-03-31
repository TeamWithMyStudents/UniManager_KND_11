package ua.knd11.service.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.impl.AuthServiceImpl;
import ua.knd11.service.UserService;
import ua.knd11.util.UserFileHandler;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static ua.knd11.util.FieldValidators.isNullOrBlank;

public class UserServiceImpl implements UserService {
    //Repository where saves all users
    protected static List<User> repository = new ArrayList<>();
    private static final AuthService authService = new AuthServiceImpl();

    public UserServiceImpl() {
        if (repository.isEmpty()) repository.addAll(UserFileHandler.getSavedList());
    }

    //A method that adds a user to the list.
    //If the user is null, or the first and last name are not specified, it returns false.
    //Otherwise, in the try-catch, the user is registered, assigned an ID, added to the list, and returns true.
    public boolean add(User user) {
        if (user == null || isNullOrBlank(user.getName(), "Name") || isNullOrBlank(user.getSurname(), "Surname")) {
            return false;
        }
        try {
            authService.registration(user);
            user.assignId();
            repository.add(user);
            return true;
        } catch (RuntimeException e) {
            System.err.println("Failed to add user: " + e.getMessage());
            return false;
        }
    }

    //A method that removes a user from the list using the Stream API
    //Where the user is compared with the received ID, and removed if there is a match.
    public boolean delete(int id) {
        return repository.removeIf(user -> user.getId() == id);
    }

    //Method that return list
    public List<User> getAll() {
        return new ArrayList<>(repository);
    }

    //A method that searches for a user by name.
    //If the request is empty, an exception is thrown.
    //In another case, the Stream API is used to find users, where the user is compared with the received request and, if there is a match, the result is displayed.
    public void findByName(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Query must not be null");
        }
        repository.stream().filter(user -> query.equalsIgnoreCase(user.getName())).forEach(System.out::println);
    }

    //A method that searches for a user by surname.
    //If the request is empty, an exception is thrown.
    //In another case, the Stream API is used to find users, where the user is compared with the received request and, if there is a match, the result is displayed.
    public void findBySurname(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Query must not be null");
        }
        repository.stream().filter(user -> query.equalsIgnoreCase(user.getSurname())).forEach(System.out::println);
    }

    //Method that sort Users(Students or Teachers) by surname
    //used class Collator for correct comparison using the Ukrainian locale
    public void sortBySurname() {
        Collator uaCollator = Collator.getInstance(new Locale("uk", "UA"));
        repository.sort(Comparator.comparing(User::getSurname, Comparator.nullsLast(uaCollator)));
    }

    @Override
    public String toString() {
        return "Users: \n" + repository.toString()
                .replace("[", "")
                .replace("]", "");
    }
}
