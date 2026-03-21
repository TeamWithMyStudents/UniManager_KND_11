package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.TeacherService;

public class TeacherServiceImpl extends UserServiceImpl implements TeacherService {

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
        Teacher teacher = (Teacher) user;
        if (isNullOrBlank(teacher.getName()) ||
                isNullOrBlank(teacher.getSurname()) ||
                isNullOrBlank(teacher.getDepartment()) ||
                isNullOrBlank(teacher.getDegree()) ||
                teacher.getSalary() <= 0) {
            return false;
        } else return super.add(teacher);
    }
}

