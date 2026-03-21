package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.TeacherService;

public class TeacherServiceImpl extends UserServiceImpl implements TeacherService {
   // public TeacherServiceImpl() {super();}
    public void calculateTotalSalary() {
        double result = repository.stream().mapToDouble(user -> user instanceof Teacher t ? t.getSalary() : 0).sum();
        System.out.println("Total University Budget: " + result);
    }

    public boolean filterByDegree(String degreeQuery) {
        if (degreeQuery == null) {
            return false;
        }
        String normalizedDegree = degreeQuery.trim();
        if (normalizedDegree.isEmpty()) {
            return false;
        }
        var matches = repository.stream().filter(user -> user instanceof Teacher t &&
                t.getDegree() != null &&
                !t.getDegree().isBlank() &&
                normalizedDegree.equalsIgnoreCase(t.getDegree().trim())).toList();
        matches.forEach(System.out::println);
        return !matches.isEmpty();
    }

    @Override
    public boolean add(User user) {
        if (!(user instanceof Teacher teacher)) {
            throw new IllegalArgumentException("User must be an instance of Teacher");
        }

        if (isNullOrBlank(teacher.getName(), "Name") ||
                isNullOrBlank(teacher.getSurname(), "Surname") ||
                isNullOrBlank(teacher.getDepartment(), "Department") ||
                isNullOrBlank(teacher.getDegree(), "Degree")) {
            return false;

        } else if (Double.isNaN(teacher.getSalary()) ||
                Double.isInfinite(teacher.getSalary()) ||
                teacher.getSalary() <= 0) {
            System.out.println("Salary must be a positive finite number");
            return false;
        }

        return super.add(teacher);
    }
}

