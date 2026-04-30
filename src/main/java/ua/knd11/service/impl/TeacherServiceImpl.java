package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.security.UserSession;
import ua.knd11.service.TeacherService;
import ua.knd11.util.FieldValidator;
import ua.knd11.util.SQLActions;

import java.util.ArrayList;

public class TeacherServiceImpl implements TeacherService {

    //проверка суперюзера, если мы это будем делать, то checkAccess(); добавить в методы, которые может делать только суперюзер.
//    private void checkAccess() {
//        User current = UserSession.getCurrentUser();
//        if (current == null || !current.isSuperuser()) {
//            throw new SecurityException("Access Denied: Only the Superuser (ID 1) can perform this action.");
//        }
//    }

    public void calculateTotalSalary() {
        double result = getAllTeachers().stream().mapToDouble(user -> user instanceof Teacher t ? t.getSalary() : 0).sum();
        System.out.println("Total University Budget: " + result);
    }

    public void filterByDegree(String degree) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("Degree", degree);

        boolean found = false;
        for (User user : getAllTeachers()) {
            if (user instanceof Teacher t) {
                if (t.getDegree().toLowerCase().contains(degree.toLowerCase())) {
                    System.out.println(t);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No teachers found with the specified degree.");
        }
    }

    public void addTeacher(Teacher teacher) throws IllegalArgumentException {
        SQLActions.addTeacherToDB(teacher);
    }

    @Override
    public void deleteTeacher(int id) {
        SQLActions.deleteTeacherFromDBWithID(id);
    }

    public ArrayList<Teacher> getAllTeachers() {
        return SQLActions.retrieveTeachersFromDB();
    }
}