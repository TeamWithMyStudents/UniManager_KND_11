package ua.knd11.service;
import ua.knd11.model.User;
import java.util.List;

public interface UserService {
    void add(User u);
    boolean delete(int id);
    List<User> getAll();
    @SuppressWarnings("unused")
    void findByName(String query);
    @SuppressWarnings("unused")
    void findBySurname(String query);
    @SuppressWarnings("unused")
    void sortBySurname();
}
