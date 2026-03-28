package ua.knd11.util;

import ua.knd11.model.Student;
import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.impl.AuthServiceImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static ua.knd11.model.enums.StudentRole.HEAD_STUDENT;
import static ua.knd11.model.enums.StudentRole.REGULAR;

public class UserFileHandler {
    private static final String FILE_PATH = "users_db.txt";
    private static final File file = new File(FILE_PATH);


    public static void saveUser(User u) {

        try {
            if (file.createNewFile()) {
                System.err.println("No DB found\nCreating new DB");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                if (u instanceof Student s) {
                    String[] parts = {
//                            String surname, String name, String lastname, String group, String email, String password
                            "Student",
                            s.getSurname(),
                            s.getName(),
                            s.getLastname(),
                            s.getGroup(),
                            String.valueOf(s.getRole()),
                            s.getEmail(),
                            s.getPassword()
                    };
                    writer.write(String.join(", ", parts));
                }
                if (u instanceof Teacher t) {

                    String[] parts = {
//                            Teacher(String name, String surname, String department, String degree, double salary, String email, String password)
                            "Teacher",
                            t.getName(),
                            t.getSurname(),
                            t.getDepartment(),
                            t.getDegree(),
                            String.valueOf(t.getSalary()),
                            t.getEmail(),
                            t.getPassword()
                    };
                    writer.write(String.join(", ", parts));
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }

    public static List<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        AuthService authService = new AuthServiceImpl();

        try {
            if (file.createNewFile()) {
                System.err.println("No DB found\nCreating new DB");
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(", ");
                String type = parts[0];
                if (type.equals("Student")) {
//                1 Surname, 2 Name, 3 Lastname, 4 GROUP, 5 REGULAR, 6 Email@example.com, 7 1281629883
                    String surname = parts[1];
                    String name = parts[2];
                    String lastname = parts[3];
                    String group = parts[4];
                    String email = parts[6];
                    String password = parts[7];

                    Student student = new Student(surname, name, lastname, group, email, password);

                    String role = parts[5];
                    if (role.equals("REGULAR")) {
                        student.setRole(REGULAR);
                    } else if (role.equals("HEAD_STUDENT")) {
                        student.setRole(HEAD_STUDENT);
                    }
                    student.assignId();
                    authService.registration(student);
                    student.setPassword(parts[7]);
                    users.add(student);
                }
                if (type.equals("Teacher")) {
//                1 Name, 2 Surname, 3 Dept, 4 Degree, 5 1.0, 6 Email2@example.com, 7 1281629883
                    String name = parts[1];
                    String surname = parts[2];
                    String department = parts[3];
                    String degree = parts[4];
                    double salary = Double.parseDouble(parts[5]);
                    String email = parts[6];
                    String password = parts[7];

                    Teacher teacher = new Teacher(name, surname, department, degree, salary, email, password);
                    teacher.assignId();
                    authService.registration(teacher);
                    teacher.setPassword(parts[7]);
                    users.add(teacher);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }

        return users;
    }

}
