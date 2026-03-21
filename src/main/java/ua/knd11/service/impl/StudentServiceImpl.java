package ua.knd11.service.impl;

import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.service.StudentService;

public class StudentServiceImpl extends UserServiceImpl implements StudentService {

    @Override
    public void findByGroup(String groupQuery) {
        repository.stream().filter(user -> user instanceof Student st &&
                !groupQuery.isBlank() &&
                !st.getGroup().isBlank() &&
                st.getGroup().contains(groupQuery.toUpperCase())).forEach(System.out::println);
    }

    @Override
    public boolean add(User user) {
        Student student = (Student) user;
        if (isNullOrBlank(student.getName()) ||
                isNullOrBlank(student.getSurname()) ||
                isNullOrBlank(student.getLastname()) ||
                isNullOrBlank(student.getGroup())) {
            return false;
        } else return super.add(student);
    }
}

