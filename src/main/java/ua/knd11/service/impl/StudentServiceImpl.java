package ua.knd11.service.impl;

import ua.knd11.model.Student;
import ua.knd11.service.StudentService;

public class StudentServiceImpl extends UserServiceImpl implements StudentService {

    @Override
    public void findByGroup(String groupQuery) {
        repository.stream().filter(user -> user instanceof Student st &&
                !groupQuery.isBlank() &&
                !st.getGroup().isBlank() &&
                st.getGroup() == null &&
                st.getGroup().contains(groupQuery.toUpperCase())).forEach(System.out::println);
    }
}

