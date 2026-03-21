package ua.knd11.service.impl;

import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.service.StudentService;

public class StudentServiceImpl extends UserServiceImpl implements StudentService {
    public StudentServiceImpl() {super();}
    @Override
    public void findByGroup(String groupQuery) {
        if (groupQuery == null || groupQuery.isBlank()) {
            return;
        }
        for (User user : repository) {
            if (user instanceof Student) {
                Student student = (Student) user;
                if (student.getGroup() != null &&
                        !student.getGroup().isBlank() &&
                        student.getGroup().toUpperCase().contains(groupQuery.toUpperCase())) {
                    System.out.println(student);
                }
            }
}}}

