package ua.knd11.service.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.impl.AuthServiceImpl;
import ua.knd11.service.UserService;
import ua.knd11.util.UserFileHandler;

import java.text.Collator;
import java.util.*;

import static ua.knd11.util.UserFileHandler.loadUsers;

public class UserServiceImpl implements UserService {
    protected static List<User> repository = new ArrayList<>();

    @SuppressWarnings("FieldMayBeFinal")
    private static boolean initialized = false;
    private final AuthService authService = new AuthServiceImpl();

    public static String normalizer(String string, String type) {
        Objects.requireNonNull(string, type + " must not be null");
        if (string.isBlank()) throw new IllegalArgumentException(type + " must not be blank");

        String regex = "^(?=.{1,100}$)[A-Za-zА-Яа-яЁёЄєҐґЇїІі0-9_-]+$";
        if (!string.matches(regex)) throw new IllegalArgumentException("invalid " + type);

        string = string.trim();
        return string;
    }

    public static String credentialsValidation(String credential, String type) {

        if (type.equals("Email")) {
            String regex = "^(?=.{1,254}$)(?=.{1,64}@)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            if (!credential.matches(regex)) throw new IllegalArgumentException("Invalid email address");

            for (User user : repository) {
                if (user.getEmail().equals(credential)) throw new IllegalArgumentException("Email already exists");
            }
        }

        if (type.equals("Password")) {
            String regex = "^[-!@#$%^&*.A-Za-z\\d]{8,}$";
            if (!credential.matches(regex)) throw new IllegalArgumentException("Invalid password");
        }
        return credential;
    }

    public static void init() {
        if (initialized) return;
        repository.addAll(loadUsers());
        initialized = true;
    }

    public boolean add(User user) {
        if (user == null || isNullOrBlank(user.getName(), "Name") || isNullOrBlank(user.getSurname(), "Surname")) {
            return false;
        }
        user.assignId();
        repository.add(user);
        authService.registration(user);
        UserFileHandler.saveUsers(user);
        return true;
    }

    public boolean delete(int id) {
        return repository.removeIf(user -> user.getId() == id);
    }

    public List<User> getAll() {
        return new ArrayList<>(repository);
    }

    public void findByName(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Query must not be null");
        }
        repository.stream().filter(user -> query.equalsIgnoreCase(user.getName())).forEach(System.out::println);
    }

    public void findBySurname(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Query must not be null");
        }
        repository.stream().filter(user -> query.equalsIgnoreCase(user.getSurname())).forEach(System.out::println);
    }

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

    public boolean isNullOrBlank(String str, String query) {
        if (str == null || str.isBlank()) {
            System.out.println(query + " must not be null or blank");
            return true;
        }
        return false;
    }
}
