package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.TeacherService;

import java.util.Objects;

public class TeacherServiceImpl extends UserServiceImpl implements TeacherService {

    public TeacherServiceImpl() {
        super(new Teacher[5]);
    }

    public void calculateTotalSalary() {
        double result = 0;
        for (User user : repository) {
            if (!(user instanceof Teacher t)) continue;
            result += t.getSalary();
        }
        System.out.println("Total University Budget: " + result);
    }

    public void filterByDegree(String degree) {
        if (degree == null) return;
        for (User user : repository) {
            if (!(user instanceof Teacher t)) continue;
            if (t.getDegree().equalsIgnoreCase(degree) || t.getDegree().toLowerCase().contains(degree.toLowerCase())) {
                System.out.println("\n" + t);
            }
        }
    }

    @Override
    public void add(User u) {
        if (u == null) {
            System.out.println("User cannot be null");
            return;
        }
        if (!(u instanceof Teacher t)) {
            System.out.println("Expected Teacher instance");
            return;
        }
        if (Objects.equals(t.getName(), "") ||
                Objects.equals(t.getSurname(), "") ||
                Objects.equals(t.getDepartment(), "") ||
                Objects.equals(t.getDegree(), "")
        ) {
            System.out.println("\n" + t + " isn't added" + "\nSome fields are empty");
            return;
        } else if (t.getSalary() <= 0) {
            System.out.println("\n" + t + " isn't added" + "\nSalary must be positive");
            return;
        }
        System.out.println("Teacher added: " + t);
        super.add(t);
    }
}
