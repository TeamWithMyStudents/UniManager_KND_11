package ua.knd11.controller;

import ua.knd11.model.Teacher;
import ua.knd11.service.impl.TeacherServiceImpl;

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
        // parts[1] name
        // parts[2] surname
        // parts[3] department
        // parts[4] degree
        // parts[5] salary
        // parts[6] email
        // parts[7] password
        return new Teacher(parts[1], parts[2], parts[3], parts[4], Double.parseDouble(parts[5]), parts[6], parts[7]);
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

