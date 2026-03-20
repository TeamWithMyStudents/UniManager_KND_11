package ua.knd11.viewer;
    import ua.knd11.controller.StudentController;
    import ua.knd11.controller.TeacherController;

    import java.util.Scanner;

    public class ConsoleMenu {
        private final StudentController studentController = new StudentController();
        private final TeacherController teacherController = new TeacherController();
        private final Scanner sc = new Scanner(System.in);

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
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please try again ");
                }
            }
        }

        private void studentSubMenu() {
            boolean back = false;
          //  label:
            while (!back) {
                    System.out.print("""
                    \n STUDENT MANAGER:
                    1. Add Student
                    2. Show All Students
                    3. Delete Student by ID
                    0. Back to Main Menu
                    """);
                System.out.print("Select an option (number): ");
                    String choice = sc.nextLine().trim();;
                    switch (choice) {
                        case "1":
                            System.out.println("Please enter Student data (Surname Name Lastname Group):");
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
                        case "0":
                            System.out.println("returning to Main Menu ↺");
                            back = true;
                            break;
                          //  break label;
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
                        System.out.print("Please enter Teacher data (Name Surname Dept Degree Salary): ");
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

