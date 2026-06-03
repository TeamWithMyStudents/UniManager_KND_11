package ua.knd11.viewer;

import ua.knd11.controller.StudentController;
import ua.knd11.controller.TeacherController;
import ua.knd11.security.AuthService;
import ua.knd11.security.UserSession;
import ua.knd11.util.SQLActions;

import java.util.InputMismatchException;
import java.util.Scanner;

import static ua.knd11.util.FieldValidator.validateEmail;

/**
 * Main application management via the console.
 * This class serves as the central hub, allowing users to navigate between
 * the student and teacher management dashboards.
 */
public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final StudentController studentController;
    private final TeacherController teacherController;
    private final AuthService authService;

    /**
     * Constructs the ConsoleMenu with required controller dependencies.
     *
     * @param studentController handles student-related UI logic
     * @param teacherController handles teacher-related UI logic
     */
    public ConsoleMenu(StudentController studentController, TeacherController teacherController, AuthService authService) {
        this.studentController = studentController;
        this.teacherController = teacherController;
        this.authService = authService;
    }

    /**
     * Starts the application's main loop.
     * Directs the user to the appropriate submenus based on their choice.
     */
    public void startMenu() {
        while (!UserSession.isAuthenticated()) {
            System.out.println("\n Please enter email and Password:");
            String[] parts = scanner.nextLine().trim().split("\\s+");
            authService.login(parts[0], parts[1]);
        }
        boolean isAdmin = UserSession.checkAccess();

        while (true) {
            System.out.println("""
                    ===== UNIVERSITY MANAGEMENT SYSTEM =====
                    1. STUDENT SECTION (Grades, Schedule)
                    2. TEACHER SECTION (Grading, Scheduling)
                    3. EXIT""");
            if (isAdmin) {
                System.out.print("4. ADMIN SECTION (User Management) \n");
            }

            System.out.println("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> studentController.displayMenu(scanner, UserSession.getCurrentUser().getId());
                case "2" -> teacherController.displayMenu(scanner);
                case "3" -> {
                    System.out.println("Exiting system. Goodbye!");
                    return;
                }
                case "4" -> {
                    if (isAdmin) {
                        adminSubMenu();
                    }else System.out.println("Invalid option. Please try again.");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void adminSubMenu() {
        if (!UserSession.checkAccess()) {
            return;
        }
        boolean back = false;
        while (!back) {
            System.out.print("""
                    \n ADMIN MENU:
                    1. Add student
                    2. Add teacher
                    3. Remove student by id
                    4. Remove student by email
                    5. Remove teacher by id
                    6. Remove teacher by email
                    0. Back to Main Menu
                    """);
            System.out.print("Select an option (number): ");
            String choice = scanner.nextLine().trim();
            String id;
            switch (choice) {
                case "1" -> {
                    System.out.println("Enter student details: Name Surname Group Email@example.com Password");
                    studentController.addStudentFromTerminal(scanner.nextLine());
                }
                case "2" -> {
                    System.out.println("Enter teacher details: Name Surname Dept Degree Salary Email@example.com Password");
                    teacherController.addTeacherFromTerminal(scanner.nextLine());
                }
                case "3" -> {
                    System.out.println("Enter student ID to remove: ");
                    try {
                        id = scanner.nextLine();
                        SQLActions.deleteStudentFromDBWithID(Integer.parseInt(id));
                    } catch (NumberFormatException e) {
                        System.err.println("Id must be integer");
                    }
                }
                case "4" -> {
                    System.out.println("Enter student email to remove: ");
                    try {
                       String email = scanner.nextLine();
                        validateEmail(email);
                        SQLActions.deleteStudentFromDBWithEmail(email);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Email must be in the format Email@example.com");
                    }
                }

                case "5" -> {
                    System.out.println("Enter teacher ID to remove: ");
                    try {
                        id = scanner.nextLine();
                        SQLActions.deleteTeacherFromDBWithID(Integer.parseInt(id));
                    } catch (NumberFormatException e) {
                        System.err.println("Id must be integer");
                    }
                }
                case "6" -> {
                    System.out.println("Enter teacher email to remove: ");
                    try {
                        String email = scanner.nextLine();
                        validateEmail(email);
                        SQLActions.deleteTeacherFromDBWithEmail(email);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Email must be in the format Email@example.com " + e.getMessage());
                    }
                }
                case "0" -> {
                    System.out.println("returning to Main Menu");
                    back = true;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}