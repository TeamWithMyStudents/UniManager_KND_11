package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.TeacherService;

import java.util.Optional;

public class TeacherServiceImpl extends UserServiceImpl implements TeacherService {
   // public TeacherServiceImpl() {super();}
    public void calculateTotalSalary() {
        double result = repository.stream().mapToDouble(user -> user instanceof Teacher t ? t.getSalary() : 0).sum();
        System.out.println("Total University Budget: " + result);
    }

    public boolean filterByDegree(String degreeQuery) {
        Optional <String> optional = Optional.ofNullable(degreeQuery);
        for (User user : repository) {
            if (user instanceof Teacher teacher) {
                if (optional.isPresent()) {
                    if (teacher.getDegree().toLowerCase().contains(optional.get().toLowerCase())) {
                        System.out.println(teacher);
                        return true;
                    }
                }
            }
        }
        return false;
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

    private boolean isNullOrBlank(String str) {
        return str == null || str.isBlank();
    }
}