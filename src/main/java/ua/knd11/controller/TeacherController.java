package ua.knd11.controller;

import ua.knd11.model.Student;
import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.impl.TeacherServiceImpl;

import java.util.List;

public class TeacherController {

    private final TeacherServiceImpl service = new TeacherServiceImpl();

    public void create(String input) { // TODO: Переписать в вид как в StudentController
        //normalize commas to spaces and trim whitespace
        String normalized = input.trim().replace(",", " ");
        //array that splits user input into tokens and stores them in elements
        String[] parts = normalized.split("\\s+");

        if (parts.length != 7) {
            System.out.println("Error: Expected 7 fields (Name Surname Dept Degree Salary Email@example.com Password).");
            return;
        }

        //assigning variables to specific array indexes
        String name = parts[0];
        String surname = parts[1];
        String department = parts[2];
        String degree = parts[3];
        double salary;
        String email = parts[5];
        String password = parts[6];

        //A new teacher is created, into which the previously created variables are entered.
        //Calls service.add() implemented in TeacherServiceImpl to add the teacher
        try {
            salary = Double.parseDouble(parts[4]);
            Teacher teacher = new Teacher(name, surname, department, degree, salary, email, password);

            service.addTeacher(teacher);
            System.out.println("Teacher added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteTeacher(int id) {
        service.deleteTeacher(id);
    }

    public void getAll() { // TODO: Переписать что бы было проще (KISS, .isEmpty)
        List<Teacher> teachers = service.getAllTeachers();
        boolean found = false;
        for (User student : teachers) {
            if (student instanceof Student) {
                System.out.println(student);
                found = true;
            }
        }
        if (!found) {
            System.err.println("No teachers found in the repository.\n");
        }
    }

    public void calculateTotalSalary() {
        service.calculateTotalSalary();
    }

    public void filterByDegree(String degree) {
        service.filterByDegree(degree);
    }
}

