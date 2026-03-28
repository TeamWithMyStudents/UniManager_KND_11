package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.TeacherService;

public class TeacherServiceImpl extends UserServiceImpl implements TeacherService {

    public static double normalizerSalary(double salary) {
        if (Double.isNaN(salary) || Double.isInfinite(salary) || salary <= 0) {
            throw new IllegalArgumentException("Salary must be a positive finite number");
        }
        return salary;
    }

    public void calculateTotalSalary() {
        double result = repository.stream().mapToDouble(user -> user instanceof Teacher t ? t.getSalary() : 0).sum();
        System.out.println("Total University Budget: " + result);
    }

    public void filterByDegree(String degreeQuery) {
        if (isNullOrBlank(degreeQuery, "Degree")) return;

        boolean found = false;
        for (User user : repository) {
            if (user instanceof Teacher t) {
                if (t.getDegree().toLowerCase().contains(degreeQuery.toLowerCase())) {
                    System.out.println(t);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No teachers found with the specified degree.");
        }
    }

    @Override
    public boolean add(User user) {
        if (!(user instanceof Teacher teacher)) {
            throw new IllegalArgumentException("User must be an instance of Teacher");
        }
        return super.add(teacher);
    }
}