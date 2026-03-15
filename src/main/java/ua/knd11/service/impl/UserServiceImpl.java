package ua.knd11.service.impl;

import ua.knd11.model.User;
import ua.knd11.service.UserService;

import java.util.Arrays;

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

    public boolean delete(int id) {
        for (int i = 0; i < repository.length; i++) {
            if (repository[i] != null && repository[i].getId() == id) {
                repository[i] = null;
                for (int j = i + 1; j < repository.length; j++) {
                    repository[j - 1] = repository[j];
                }
                repository[repository.length - 1] = null;
                return true;
            }
        }
        return false;
    }

    public void getAll() {
        for (User value : repository) {
            if (value == null) {
                System.out.println("-");
            } else System.out.print(value);
        }
    }

    @Override
    public String toString() {
        return "Users: \n" + Arrays.toString(repository);
    }
}

