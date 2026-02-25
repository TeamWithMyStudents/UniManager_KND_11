package ua.knd11.service.impl;

import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.service.StudentService;

public class StudentServiceImpl extends UserServiceImpl implements StudentService {

    public StudentServiceImpl() {
        super(new Student[5]);
    }

    @Override
    public void findByGroup(String groupName) {
        boolean found = false;

        for (User user : repository) {
            if (user instanceof Student) {
                Student st = (Student) user;
                if (st.getGroup().equalsIgnoreCase(groupName)) {
                    System.out.print(st);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("Student in" + groupName + "is not exist");
        }
    }
}

