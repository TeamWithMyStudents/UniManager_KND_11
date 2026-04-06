package ua.knd11.util;

import ua.knd11.model.Student;
import ua.knd11.model.Teacher;
import ua.knd11.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static ua.knd11.model.enums.StudentRole.HEAD_STUDENT;
import static ua.knd11.model.enums.StudentRole.REGULAR;

/**
 * Handles file operations for persisting user data.
 */
public class UserFileHandler {
    private static final String FILE_PATH = "users_db.txt";
    private static final File file = new File(FILE_PATH);
    /**
     * The constant savedList.
     */
    public static ArrayList<User> savedList = new ArrayList<>();

    /**
     * Gets a saved list.
     *
     * @return cached list of saved users, loading from a file if empty
     */
    public static ArrayList<User> getSavedList() {
        if (savedList.isEmpty()) savedList.addAll(loadUsers());
        return new ArrayList<>(savedList);
    }

    /**
     * Sets saved list.
     *
     * @param savedList the saved list
     */
    public static void setSavedList(ArrayList<User> savedList) {
        UserFileHandler.savedList = savedList;
    }

    /**
     * Save users.
     *
     * @param u the User object to persist to file
     * @throws IOException if an I/O error occurs
     */
    public static void saveUsers(User u) throws IOException {
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

    }

    /**
     * Load the users list.
     *
     * @return the list
     */
    public static List<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();

        try {
            if (file.createNewFile()) {
                System.err.println("No DB found\nCreating new DB");
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            ArrayList<String> seenEmails = new ArrayList<>();
            String line;
            int lineNumber = 0;
            loop:
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                try {
                    String[] parts = line.split(", ");

                    if (parts.length < 1) {
                        System.err.println("Warning: Line " + lineNumber + " is empty, skip");
                        continue;
                    }

                    String type = parts[0];

                    if (type.equals("Student")) {
                        if (parts.length != 8) {
                            System.err.println("Warning: Line " + lineNumber + " has invalid Student format (expected 8 fields, got " + parts.length + "), skip");
                            continue;
                        }

                        String surname = parts[1];
                        String name = parts[2];
                        String lastname = parts[3];
                        String group = parts[4];
                        String role = parts[5];
                        String email = parts[6];
                        String password = parts[7];

                        for (String seen_email : seenEmails) {
                            if (seen_email.equals(email)) {
                                System.err.println("Warning: Line " + lineNumber + " has duplicate email, skip");
                                continue loop;
                            }
                        }
                        seenEmails.add(email);

                        Student student = new Student(surname, name, lastname, group, email, password);

                        try {
                            if (role.equals("REGULAR")) {
                                student.setRole(REGULAR);
                            } else if (role.equals("HEAD_STUDENT")) {
                                student.setRole(HEAD_STUDENT);
                            } else {
                                System.err.println("Warning: Line " + lineNumber + " has invalid Student role '" + role + "', using REGULAR");
                                student.setRole(REGULAR);
                            }
                        } catch (IllegalArgumentException e) {
                            System.err.println("Warning: Line " + lineNumber + " has invalid Student role, using REGULAR" + e.getMessage());
                            student.setRole(REGULAR);
                        }

                        student.assignId();
                        student.setPassword(password);
                        users.add(student);

                    } else if (type.equals("Teacher")) {
                        if (parts.length != 8) {
                            System.err.println("Warning: Line " + lineNumber + " has invalid Teacher format (expected 8 fields, got " + parts.length + "), skip");
                            continue;
                        }

                        String name = parts[1];
                        String surname = parts[2];
                        String department = parts[3];
                        String degree = parts[4];
                        String email = parts[6];
                        String password = parts[7];

                        double salary;
                        try {
                            salary = Double.parseDouble(parts[5]);
                        } catch (NumberFormatException e) {
                            System.err.println("Warning: Line " + lineNumber + " has invalid salary format, skipping: " + e.getMessage());
                            continue;
                        }

                        Teacher teacher = new Teacher(name, surname, department, degree, salary, email, password);
                        teacher.assignId();
                        teacher.setPassword(password);
                        users.add(teacher);

                    } else {
                        System.err.println("Warning: Line " + lineNumber + " has unknown user type '" + type + "', skip");
                    }

                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Warning: Line " + lineNumber + " has insufficient fields, skipping: " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.err.println("Warning: Line " + lineNumber + " has invalid number format, skipping: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.err.println("Warning: Line " + lineNumber + " has invalid data, skipping: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Warning: Line " + lineNumber + " caused unexpected error, skipping: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        setSavedList(users);
        return users;
    }
}
