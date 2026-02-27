package ua.knd11.service;

import ua.knd11.model.User;

public interface UserService {
    void add(User u);
    void delete(int id);
    User[] getAll();
    void findByName(String query);
    void findBySurname(String query);
    void sortBySurname();
}
