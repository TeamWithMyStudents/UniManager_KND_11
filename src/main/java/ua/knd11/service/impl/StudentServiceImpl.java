package ua.knd11.service.impl;

import ua.knd11.model.User;
import ua.knd11.service.StudentService;

public class StudentServiceImpl extends UserServiceImpl implements StudentService {

    public StudentServiceImpl(User[] initialArray) {
        super(initialArray);
    }

    @Override
    public void findByGroup(String groupName) {
    }
}
