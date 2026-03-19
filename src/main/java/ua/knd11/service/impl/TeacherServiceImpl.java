package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.TeacherService;

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
        if (degree == null || degree.isBlank()) return;
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
            throw new IllegalArgumentException("User cannot be null");
        } else if (!(u instanceof Teacher t)) {
            throw new IllegalArgumentException("Expected Teacher instance");
        } else if (t.getName() == null || t.getName().isEmpty() || t.getSurname() == null || t.getSurname().isEmpty() || t.getDepartment() == null || t.getDepartment().isEmpty() || t.getDegree() == null || t.getDegree().isEmpty()) {
            throw new IllegalArgumentException("Teacher has empty required fields: " + t);
        } else if (t.getSalary() <= 0) {
            throw new IllegalArgumentException("Teacher has empty required fields: " + t);
        } else {
            System.out.println("Teacher added: " + t);
            super.add(t);
        }
    }
}
