package ua.knd11.viewer;

import ua.knd11.controller.StudentController;
import ua.knd11.controller.TeacherController;
import ua.knd11.model.Student;
import ua.knd11.model.Teacher;
import ua.knd11.security.UserSession;
import ua.knd11.security.impl.AuthServiceImpl;
import ua.knd11.util.FieldValidator;

import java.util.Scanner;

/**
 * The primary View component of the application.
 * Provides a text-based Command Line Interface (CLI) for user interaction,
 * including authentication flows and management sub-menus for students and teachers.
 */
public class ConsoleMenu {
    /** Scanner instance for reading user input from the standard input stream */
    private final Scanner sc = new Scanner(System.in);
    /** Controller for handling student-related operations */
    private final StudentController studentController = new StudentController();
    /** Controller for handling teacher-related operations */
    private final TeacherController teacherController = new TeacherController();
    /** Service implementation for user authentication and registration */
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
                    3. LOGOUT
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
                    \n STUDENT MANAGER:
                    1. Add Student
                    2. Show All Students
                    3. Delete Student by ID
                    4. Assign Group Head Student
                    0. Back to Main Menu
                    """);
            System.out.print("Select an option (number): ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    if(UserSession.checkAccess()) break;
                    System.out.println("Please enter Student data (Name Surname Group Email@example.com Password):");
                    String input = sc.nextLine();
                    studentController.addStudentFromTerminal(input);
                    break;
                case "2":
                    studentController.getAll();
                    break;
                case "3":
                    if(UserSession.checkAccess()) break;
                    System.out.println("Enter Student's ID to delete");
                    if (sc.hasNextInt()) {
                        int id = sc.nextInt();
                        sc.nextLine();
                        studentController.deleteStudent(id);
                    } else {
                        System.out.println("Error, ID must be a number!");
                        sc.nextLine();
                    }
                    break;
                case "4": // TODO: Реализовать
                    if(UserSession.checkAccess()) break;
                    System.out.println("Enter Student's ID to assign as Head Student:");
                    if (sc.hasNextInt()) {
                        int assignId = sc.nextInt();
                        sc.nextLine();
                        studentController.assignHeadStudent(assignId);
                    } else {
                        System.out.println("Error, ID must be a number!");
                        sc.nextLine(); // Clearing error input
                    }
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
                    1. Add Teacher
                    2. Show All Teachers
                    3. Delete Teacher
                    4. Calculate Salary
                    5. Filter Degree
                    0. Back to Main Menu
                    """);
            System.out.print("Select an option (number): ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    if(UserSession.checkAccess()) break;
                    System.out.print("Please enter Teacher data (Name Surname Dept Degree Salary Email@example.com Password): ");
                    teacherController.addTeacherFromTerminal(sc.nextLine());
                    break;
                case "2":
                    teacherController.getAll();
                    break;
                case "3":
                    if(UserSession.checkAccess()) break;
                    System.out.println("Enter teachers's ID to delete");
                    if (sc.hasNextInt()) {
                        int id = sc.nextInt();
                        sc.nextLine();
                        teacherController.deleteTeacher(id);
                    } else {
                        System.err.println("Error, ID must be a number!");
                        sc.nextLine();
                    }
                    break;
                case "4":
                    if(UserSession.checkAccess()) break;
                    teacherController.calculateTotalSalary();
                    break;
                case "5":
                    if(UserSession.checkAccess()) break;
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

    /**
     * Handles the initial authentication phase of the application.
     * Provides options for logging in or registering new Student/Teacher accounts.
     * The loop continues until a user is successfully authenticated or the application is exited.
     */
    public void LogOrReg(){
        while (true) {
            System.out.print("""
                       \n LOGIN OR REGISTER:
               1. LOGIN
               2. REGISTER STUDENT
               3. REGISTER TEACHER
               0. EXIT
            """);
            System.out.print("Select an option (number): ");
            String choice = sc.next().trim();
            switch (choice) {
                case "1":
                    System.out.println("Please enter email and Password:");
                    String emailLog = sc.next().trim();
                    String passwordLog = sc.next().trim();
                    sc.nextLine();
                    authService.login(emailLog, passwordLog);
                    if (UserSession.isAuthenticated()){
                        return;
                    }
                    break;
                case "2":
                    System.out.println("Please enter name, surname, group, email, password:");
                    String [] parts = new String[5];
                    for (int i = 0; i < parts.length; i++){
                        parts[i] = sc.next().trim();
                    }

                    FieldValidator.validateGroup(parts[2]);
                    authService.registerUser(new Student(parts[0], parts[1], parts[2], parts[3], parts[4]));
                    System.out.println("Student successfully registered!");
                    break;
                case "3":
                    System.out.println("Please enter name, surname, department, degree, salary, email, password:");
                    String [] partsT = new String[7];
                    for (int i = 0; i < partsT.length; i++){
                        partsT[i] = sc.next().trim();
                    }

                    FieldValidator.validateAlphabeticString("department", partsT[2]);
                    FieldValidator.validateAlphabeticString("degree", partsT[3]);
                    FieldValidator.validateSalary(Double.parseDouble(partsT[4]));

                    authService.registerUser(new Teacher(partsT[0], partsT[1], partsT[2], partsT[3], Double.parseDouble(partsT[4]), partsT[5], partsT[6]));
                    System.out.println("Teacher successfully registered!");
                    break;
                case "0":
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}