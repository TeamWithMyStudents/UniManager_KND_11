package ua.knd11.controller;

import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.service.StudentService;
import ua.knd11.service.impl.StudentServiceImpl;

import java.util.List;

public class StudentController {
    private final StudentService service = new StudentServiceImpl();

    public void assignHeadStudent(int id) {
        service.assignHeadStudent(id);
    }

    public void addStudentFromTerminal(String value) {
        String[] parts = value.trim().split("\\s+");
        if (parts.length == 5) {
            service.addStudent(createStudentWithParts(parts));
            return;
        }
        System.out.println("Error: Expected 5 fields (Name Surname Group Email@example.com Password).");
    }

    private Student createStudentWithParts(String[] parts) {
        // parts[0] - name, 
        // parts[1] - surname, 
        // parts[2] - group, 
        // parts[3] - email, 
        // parts[4] - password
        return new Student(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    public void deleteStudent(int id) {
        service.deleteStudent(id);
    }

    public void getAll() {
        List<Student> students = service.getAllStudents();
        if (students.isEmpty()) {
            System.err.println("No students found in the repository.\n");
            return;
        }
        students.forEach(System.out::println);
    }
}

