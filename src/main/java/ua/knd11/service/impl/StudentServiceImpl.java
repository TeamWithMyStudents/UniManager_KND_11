package ua.knd11.service.impl;

import ua.knd11.model.Student;
import ua.knd11.service.StudentService;

public class StudentServiceImpl extends UserServiceImpl implements StudentService {

    @Override
    public void findByGroup(String groupQuery) {
        if (groupQuery == null || groupQuery.isBlank()) {
            System.out.println("Group query cannot be empty.");
            return;
        }
        repository.stream().filter(user -> user instanceof Student st && st.getGroup().contains(groupQuery.toUpperCase())).forEach(System.out::println);
    }
}

