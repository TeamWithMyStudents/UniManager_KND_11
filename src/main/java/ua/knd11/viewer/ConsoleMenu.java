package ua.knd11.viewer;

import ua.knd11.controller.StudentController;
import ua.knd11.controller.TeacherController;
import ua.knd11.security.UserSession;
import ua.knd11.security.impl.AuthServiceImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main application management via the console.
 * This class serves as the central hub, allowing users to navigate between
 * the student and teacher management dashboards.
 */
public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final StudentController studentController;
    private final TeacherController teacherController;

    /**
     * Constructs the ConsoleMenu with required controller dependencies.
     *
     * @param studentController handles student-related UI logic
     * @param teacherController handles teacher-related UI logic
     */
    public ConsoleMenu(StudentController studentController, TeacherController teacherController) {
        this.studentController = studentController;
        this.teacherController = teacherController;
    }

    /**
     * Starts the application's main loop.
     * Directs the user to the appropriate sub-menus based on their choice.
     */
    public void startMenu() {
        LogOrReg();
        while (true) {
            System.out.println("\n===== UNIVERSITY MANAGEMENT SYSTEM =====");
            System.out.println("1. STUDENT SECTION (Grades, Schedule, Profile)");
            System.out.println("2. TEACHER SECTION (Grading, Scheduling, Admin)");
            System.out.println("3. EXIT");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> studentSubMenu();
                case "2" -> teacherSubMenu();
                case "3" -> {
                    System.out.println("Exiting system. Goodbye!");
                    return;
                }
                default -> System.out.println("[WARNING] Invalid option. Please try again.");
            }
        }
    }

    /**
     * Routing for student-related tasks.
     */
    private void studentSubMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Student Section ---");
            System.out.println("1. Go to Student Dashboard (Academic)");
            System.out.println("2. Add New Student Profile");
            System.out.println("3. Delete Student by ID");
            System.out.println("4. Return to Main Menu");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> {
                    // Simulated login: using ID 1 as default for testing
                    System.out.print("Enter Student ID to login: ");
                    try {
                        int id = Integer.parseInt(scanner.nextLine());
                        studentController.displayMenu(scanner, id);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid ID format.");
                    }
                }
                case "2" -> {
                    System.out.println("Format: Name Surname Lastname Group Email Password");
                    studentController.create(scanner.nextLine());
                }
                case "3" -> {
                    System.out.print("Enter ID to delete: ");
                    try {
                        studentController.delete(Integer.parseInt(scanner.nextLine()));
                    } catch (NumberFormatException e) {
                        System.err.println("ID must be a number.");
                    }
                }
                case "4" -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Routing for teacher-related tasks.
     */
    private void teacherSubMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Teacher Section ---");
            System.out.println("1. Go to Teacher Dashboard (Academic)");
            System.out.println("2. Add New Teacher Profile");
            System.out.println("3. Return to Main Menu");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> teacherController.displayMenu(scanner);
                case "2" -> {
                    System.out.println("Format: Name Surname Dept Degree Salary Email Password");
                    teacherController.create(scanner.nextLine());
                }
                case "3" -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }
}