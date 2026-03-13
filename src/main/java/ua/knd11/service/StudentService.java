package ua.knd11.service;

public interface StudentService extends UserService {
    void findByGroup(String groupName);
}
