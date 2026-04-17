package ua.knd11.viewer;

import ua.knd11.controller.StudentController;
import ua.knd11.controller.TeacherController;
import ua.knd11.util.SQLActions;

import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner sc = new Scanner(System.in);
    private final StudentController studentController = new StudentController();
    private final TeacherController teacherController = new TeacherController();

    public void startMenu() {
        while (true) {
            System.out.print(""" 
                      \n MAIN MENU
                    1. STUDENT MANAGER
                    2. TEACHER MANAGER
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
                case "0":
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again ");
            }
        }
    }

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
                    System.out.println("Please enter Student data (Name Surname Group Email@example.com Password):");
                    String input = sc.nextLine();
                    studentController.addStudentFromTerminal(input);
                    break;
                case "2":
                    studentController.getAll();
                    break;
                case "3":
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
                    System.out.println("Enter Student's ID to assign as Head Student:");
                    if (sc.hasNextInt()) {
                        int assignId = sc.nextInt();
                        sc.nextLine();
                        studentController.assignHeadStudent(assignId);
                        SQLActions.assignStudentById(assignId);
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

    private void teacherSubMenu() {
        boolean back = false;
        while (!back) {
            System.out.print("""
                    \n TEACHER MANAGER:
                    1. Add Teacher
                    2. Show All Teachers
                    3. Calculate Budget
                    4. Filter by Degree
                    0. Back to Main Menu
                    """);
            System.out.print("Select an option (number): ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Please enter Teacher data (Name Surname Dept Degree Salary Email@example.com Password): ");
                    teacherController.addTeacherFromTerminal(sc.nextLine());
                    break;
                case "2":
                    teacherController.getAll();
                    break;
                case "3":
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
                    teacherController.calculateTotalSalary();
                    break;
                case "5":
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
}

