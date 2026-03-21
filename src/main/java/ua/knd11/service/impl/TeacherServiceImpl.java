package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.TeacherService;

import java.util.Optional;

    public void calculateTotalSalary() {
        double result = repository.stream().mapToDouble(user -> user instanceof Teacher t ? t.getSalary() : 0).sum();
        System.out.println("Total University Budget: " + result);
    }

    public void filterByDegree(String degreeQuery) {
        Optional <String> optional = Optional.ofNullable(degreeQuery);
        for (User user : repository) {
            if (user instanceof Teacher teacher) {
                if(optional.isPresent()) {
                    if (teacher.getDegree().toLowerCase().contains(optional.get().toLowerCase())) {
                        System.out.println(teacher);
                    }
                }
            }
        }
    }

    @Override
    public void add(User user) {
        if (!(user instanceof Teacher)) {
            throw new IllegalArgumentException("Only Teacher instances can be added");
        }
        super.add(user);
    }
}
private boolean isNullOrBlank(String str) {
    return str == null || str.isBlank();
}}

