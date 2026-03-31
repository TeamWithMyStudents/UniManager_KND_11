package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.TeacherService;

import static ua.knd11.util.FieldValidators.isNullOrBlank;

public class TeacherServiceImpl extends UserServiceImpl implements TeacherService {

    public void calculateTotalSalary() {
        double result = repository.stream().mapToDouble(user -> user instanceof Teacher t ? t.getSalary() : 0).sum();
        System.out.println("Total University Budget: " + result);
    }

    //A method that filters by teacher degree.
    //If the desired degree is not found, the method terminates.
    //Otherwise, a variable is created that stores a boolean value.
    //All users are then iterated through, with only teachers selected.
    //These users are compared with the desired degree, displayed, and true is set to the variable.
    //If none are found, a message is displayed stating "No teachers with this degree were found."
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