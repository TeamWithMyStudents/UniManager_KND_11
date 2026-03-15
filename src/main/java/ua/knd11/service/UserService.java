package ua.knd11.service;

import ua.knd11.model.User;

public interface UserService {
    void add(User u);
    boolean delete(int id);
    void getAll();
}
