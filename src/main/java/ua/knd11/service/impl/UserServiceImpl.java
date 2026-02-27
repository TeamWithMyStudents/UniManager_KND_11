package ua.knd11.service.impl;

import ua.knd11.model.User;
import ua.knd11.service.UserService;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class UserServiceImpl implements UserService {
    protected User[] repository;

    public UserServiceImpl(User[] initialArray) {
        this.repository = initialArray;
    }

    public void add(User u) {
        for (int i = 0; i < repository.length; i++) {
            if (u.getId() > repository.length) {
                repository = Arrays.copyOf(repository, repository.length * 2);
            } else if (repository[i] == null) {
                repository[i] = u;
                return;
            }
        }
    }

    public void delete(int id) {
        for (int i = 0; i < repository.length; i++) {
            if (repository[i] != null && repository[i].getId() == id) {
                repository[i] = null;
                for (int j = i + 1; j < repository.length; j++) {
                    repository[j - 1] = repository[j];
                }
                repository[repository.length - 1] = null;
                break;
            }
        }
    }

    public User[] getAll() {
        int count = 0;
        for (User user : repository) {
            if (user != null) {
                count++;
            }
        }

        User[] users = new User[count];
        int index = 0;
        for (User user : repository) {
            if (user != null) {
                users[index++] = user;
            }
        }
        return users;
    }


    public void findByName(String query){
        for (User value : repository) {
            if (value != null && (value.getName().equalsIgnoreCase(query) || value.getName().toLowerCase().contains(query.toLowerCase()))) {
                System.out.print(value);
            }
        }
    }

    public void findBySurname(String query){
        for (User value : repository) {
            if (value != null && (value.getSurname().equalsIgnoreCase(query) ||  value.getSurname().toLowerCase().contains(query.toLowerCase()))) {
                System.out.print(value);
            }
        }
    }

    public void sortBySurname() {
        Collator uaCollator = Collator.getInstance(new Locale("uk", "UA"));
        Arrays.sort(repository, Comparator.nullsLast(Comparator.comparing(User::getSurname, uaCollator)));
    }


    @Override
    public String toString() {
        return "Users: \n"  + Arrays.toString(repository);
    }
}
