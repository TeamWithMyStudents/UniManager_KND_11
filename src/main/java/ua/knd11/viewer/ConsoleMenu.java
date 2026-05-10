package ua.knd11.viewer;

import ua.knd11.controller.StudentController;
import ua.knd11.controller.TeacherController;
import ua.knd11.security.UserSession;
import ua.knd11.security.impl.AuthServiceImpl;

import java.util.Scanner;

/**
 * The primary View component of the application.
 * Provides a text-based Command Line Interface (CLI) for user interaction,
 * including authentication flows and management sub-menus for students and teachers.
 */
public class ConsoleMenu {
    /**
     * Scanner instance for reading user input from the standard input stream
     */
    private final Scanner sc = new Scanner(System.in);
    /**
     * Controller for handling student-related operations
     */
    private final StudentController studentController = new StudentController();
    /**
     * Controller for handling teacher-related operations
     */
    private final TeacherController teacherController = new TeacherController();
    /**
     * Service implementation for user authentication and registration
     */
    private final AuthServiceImpl authService = new AuthServiceImpl();

    /**
     * Entry point for the console application.
     * Initiates the login/registration sequence and enters the main application loop.
     */
    public void startMenu() {
        LogOrReg();
        while (true) {
            System.out.print(""" 
                      \n MAIN MENU
                    1. STUDENT MANAGER
                    2. TEACHER MANAGER
                    3. ADMIN MANAGER
                    4. LOGOUT
                    0. EXIT
                    """);
            System.out.print("Select an option (number): ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    studentSubMenu();
                    break;
                case "2":
                    teacherSubMenu();
                    break;
                case "3":
                    adminSubMenu();
                    break;
                case "4":
                    UserSession.logout();
                    LogOrReg();
                    break;
                case "0":
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again ");
            }
        }
    }

    /**
     * Displays and manages the Student Management sub-menu.
     * Allows for adding, viewing, deleting, and assigning roles to students.
     * Permission checks are enforced via {@link UserSession}.
     */
    private void studentSubMenu() {
        boolean back = false;
        while (!back) {
            System.out.print("""
                    \n STUDENT MENU:
                    1. Show All Students
                    0. Back to Main Menu
                    """);
            System.out.print("Select an option (number): ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    studentController.getAll();
                    break;
                case "0":
                    System.out.println("returning to Main Menu");
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    /**
     * Displays and manages the Teacher Management sub-menu.
     * Allows for adding, viewing, and deleting teachers, as well as
     * performing budget calculations and degree filtering.
     * Permission checks are enforced via {@link UserSession}.
     */
    private void teacherSubMenu() {
        boolean back = false;
        while (!back) {
            System.out.print("""
                    \n TEACHER MANAGER:
                    1. Show All Teachers
                    2. Calculate Salary
                    3. Filter Degree
                    0. Back to Main Menu
                    """);
            System.out.print("Select an option (number): ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    teacherController.getAll();
                    break;
                case "2":
                    if (!UserSession.checkAccess()) break;
                    teacherController.calculateTotalSalary();
                    break;
                case "3":
                    if (!UserSession.checkAccess()) break;
                    System.out.print("Enter degree to filter by: ");
                    teacherController.filterByDegree(sc.nextLine().trim());
                    break;
                case "0":
                    System.out.println("returning to Main Menu ↺");
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
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
                    4. Remove teacher by id
                    0. Back to Main Menu
                    """);
            System.out.print("Select an option (number): ");
            String choice = sc.nextLine().trim();
            int id;
            switch (choice) {
                case "1":
                    authService.addStudent();
                    break;
                case "2":
                    authService.addTeacher();
                    break;
                case "3":
                    System.out.print("Enter student ID to remove: ");
                    id = sc.nextInt();
                    authService.removeStudent(id);
                    sc.nextLine();
                    break;
                case "4":
                    System.out.println("Enter teacher ID to remove: ");
                    id = sc.nextInt();
                    authService.removeTeacher(id);
                    sc.nextLine();
                    break;
                case "0":
                    System.out.println("returning to Main Menu");
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    /**
     * Handles the initial authentication phase of the application.
     * Provides an option for logging
     * The loop continues until a user is successfully authenticated or the application is exited.
     */
    public void LogOrReg() {
        while (true) {
            System.out.println("\n Please enter email and Password:");
            String emailLog = sc.next().trim();
            String passwordLog = sc.next().trim();
            sc.nextLine();
            authService.login(emailLog, passwordLog);
            if (UserSession.isAuthenticated()) {
                break;
            }
        }
    }
}