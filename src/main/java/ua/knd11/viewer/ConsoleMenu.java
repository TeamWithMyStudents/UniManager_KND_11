package ua.knd11.viewer;

import ua.knd11.controller.StudentController;
import ua.knd11.controller.TeacherController;

import java.util.Scanner;

/**
 * Application management via the console.
 * This class allows the user to navigate between the student and teacher management sections.
 * @see StudentController
 * @see TeacherController
 */
public class ConsoleMenu {
    private final Scanner sc = new Scanner(System.in);
    private final StudentController studentController = new StudentController();
    private final TeacherController teacherController = new TeacherController();

    /**
     * Method starts the application's main loop.
     * Displays the root menu and directs the user to the appropriate
     * submenus depending on the number entered.
     * Terminates the program when the "0" option is selected.
     */
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
/**Submenu for managing student data.
 * Allows you to add, show,delete, assign sb as a Head Student or go back to main menu.
 * Checks the correctness of the input of int data (ID)
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
                    System.out.println("Please enter Student data (Surname Name Lastname Group Email@example.com Password):");
                    String input = sc.nextLine();
                    studentController.create(input);
                    break;
                case "2":
                    studentController.getAll();
                    break;
                case "3":
                    System.out.println("Enter Student's ID to delete");
                    if (sc.hasNextInt()) {
                        int id = sc.nextInt();
                        sc.nextLine();
                        studentController.delete(id);
                    } else {
                        System.out.println("Error, ID must be a number!");
                        sc.nextLine();
                    }
                    break;
                case "4":
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
/**Submenu for managing teacher data.
 * Allows you to add, show, delete, calculate budget, filter by degree or go back to main menu.
 */
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
                    teacherController.create(sc.nextLine());
                    break;
                case "2":
                    teacherController.getAll();
                    break;
                case "3":
                    teacherController.calculateTotalSalary();
                    break;
                case "4":
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

