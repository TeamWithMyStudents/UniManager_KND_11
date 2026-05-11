package ua.knd11.viewer;

import ua.knd11.controller.StudentController;
import ua.knd11.controller.TeacherController;

import java.util.Scanner;

/**
 * Main application console menu responsible for initial user routing.
 * Acts as the entry interface delegating operations to specific controllers based on the user role.
 */
public class ConsoleMenu {

    /** Controller handling student operations. */
    private final StudentController studentController;

    /** Controller handling teacher operations. */
    private final TeacherController teacherController;

    /** Standard input scanner. */
    private final Scanner scanner;

    /**
     * Constructs the ConsoleMenu with required controller dependencies.
     *
     * @param studentController the controller for student actions
     * @param teacherController the controller for teacher actions
     */
    public ConsoleMenu(StudentController studentController, TeacherController teacherController) {
        this.studentController = studentController;
        this.teacherController = teacherController;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the main interactive loop of the application.
     * Displays role selection and routes the user to the appropriate dashboard.
     */
    public void startMenu() {
        boolean isRunning = true;
        System.out.println("Welcome to UniManager");

        while (isRunning) {
            System.out.println("\n=== Main Authorization Menu ===");
            System.out.println("1. Login as Student");
            System.out.println("2. Login as Teacher");
            System.out.println("3. Exit System");
            System.out.print("Select your role: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("[LOG] Authenticated as Student (ID: 1)");
                    studentController.displayMenu(scanner, 1);
                    break;
                case "2":
                    System.out.println("[LOG] Authenticated as Teacher");
                    teacherController.displayMenu(scanner);
                    break;
                case "3":
                    System.out.println("Shutting down UniManager. Goodbye!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("[WARNING] Invalid selection. Please choose 1, 2, or 3.");
            }
        }
        scanner.close();
    }
}