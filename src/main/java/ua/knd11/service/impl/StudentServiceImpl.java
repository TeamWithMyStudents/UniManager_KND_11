package ua.knd11.service.impl;

import ua.knd11.model.Student;
import ua.knd11.service.StudentService;

public class StudentServiceImpl extends UserServiceImpl implements StudentService {

    public StudentServiceImpl(Student[] initialArray) {super(initialArray);}

    @Override
    public void findByGroup(String groupName) {
    }
}
