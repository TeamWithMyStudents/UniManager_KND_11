package ua.knd11.service.impl;

import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.service.StudentService;

public class StudentServiceImpl extends UserServiceImpl implements StudentService {
    public StudentServiceImpl() {super();}
    @Override
    public void findByGroup(String groupQuery) {
        if (isNullOrBlank(groupQuery, "Group")) return;

        repository.stream().filter(user -> user instanceof Student st &&
                st.getGroup().contains(groupQuery.toUpperCase())).forEach(System.out::println);
    }

    @Override
    public boolean add(User user) {
        if (!(user instanceof Student student)){
            throw new IllegalArgumentException("User must be an instance of Student");
        }
        if (    isNullOrBlank(student.getName(), "Name") ||
                isNullOrBlank(student.getSurname(), "Surname") ||
                isNullOrBlank(student.getLastname(), "Lastname") ||
                isNullOrBlank(student.getGroup(), "Group")) {
            return false;
        } else return super.add(student);
    }
}


