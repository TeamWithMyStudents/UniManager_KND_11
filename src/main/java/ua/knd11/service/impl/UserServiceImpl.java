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
import static ua.knd11.util.UserFileHandler.loadUsers;

public class UserServiceImpl implements UserService {
    protected static List<User> repository = new ArrayList<>();

    @SuppressWarnings("FieldMayBeFinal")
    private static boolean initialized = false;
    private final AuthService authService = new AuthServiceImpl();

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
}
