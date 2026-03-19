package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.TeacherService;

public class TeacherServiceImpl extends UserServiceImpl implements TeacherService {

    public void calculateTotalSalary() {
        double result = repository.stream().mapToDouble(user -> user instanceof Teacher t ? t.getSalary() : 0).sum();
        System.out.println("Total University Budget: " + result);
    }

    public void filterByDegree(String degreeQuery) {
        repository.stream().filter(user -> user instanceof Teacher t && t.getDegree().equalsIgnoreCase(degreeQuery)).forEach(System.out::println);
    }

    @Override
    public void add(User user) {
        if (!(user instanceof Teacher)) {
            throw new IllegalArgumentException("Only Teacher instances can be added");
        }
        repository.add(user);
    }
}
