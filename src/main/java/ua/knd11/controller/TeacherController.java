package ua.knd11.controller;

import ua.knd11.model.Teacher;
import ua.knd11.service.impl.TeacherServiceImpl;
import ua.knd11.util.FieldValidator;

import java.util.List;

public class TeacherController {

    private final TeacherServiceImpl service = new TeacherServiceImpl();

    public void addTeacherFromTerminal(String value) {
        String[] parts = value.trim().split("\\s+");
        if (parts.length == 7) {
            service.addTeacher(createTeacherWithParts(parts));
            return;
        }
        System.out.println("Error: Expected 7 fields (Name Surname Dept Degree Salary Email@example.com Password).");
    }

    private Teacher createTeacherWithParts(String[] parts) {
        String name = parts[0];
        String surname = parts[1];
        String dept = parts[2];
        String degree = parts[3];
        double salary = Double.parseDouble(parts[4]);
        String email = parts[5];
        String password = parts[6];

        FieldValidator.validateSalary(salary);
        FieldValidator.validateEmail(email);
        FieldValidator.validatePassword(password);

        return new Teacher(name, surname, dept, degree, salary, email, password);
    }

    public void deleteTeacher(int id) {
        service.deleteTeacher(id);
    }

    public void getAll() { // TODO: Переписать что бы было проще (KISS, .isEmpty)
        List<Teacher> teachers = service.getAllTeachers();
        teachers.stream().filter(teacher -> teacher instanceof Teacher).forEach(System.out::println);
        if (teachers.isEmpty()) { System.out.println("No teachers found"); }
    }

    public void calculateTotalSalary() {
        service.calculateTotalSalary();
    }

    public void filterByDegree(String degree) {
        service.filterByDegree(degree);
    }
}

