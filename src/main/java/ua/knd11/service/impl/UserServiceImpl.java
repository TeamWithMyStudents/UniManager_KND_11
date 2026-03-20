package ua.knd11.service.impl;

import ua.knd11.model.User;
import ua.knd11.service.UserService;

import java.text.Collator;
import java.util.*;

public class UserServiceImpl implements UserService {
    protected List<User> repository;

    public UserServiceImpl() {
        this.repository = new ArrayList<>();
    }

    public void add(User user) {
        repository.add(user);
    }

    public boolean delete(int id) {
        return repository.removeIf(user -> user.getId() == id);
    }

    public List<User> getAll() {
        return new ArrayList<>(repository);
    }

    public void findByName(String query) {
        repository.stream().filter(user -> query.equalsIgnoreCase(user.getName())).forEach(System.out::println);
    }

    public void findBySurname(String query) {
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
