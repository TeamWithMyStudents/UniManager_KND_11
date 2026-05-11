package ua.knd11.controller;

import ua.knd11.model.Lesson;
import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.service.JournalService;
import ua.knd11.service.ScheduleService;
import ua.knd11.service.StudentService;
import ua.knd11.service.impl.StudentServiceImpl;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Scanner;

/**
 * Controller class responsible for handling student-specific operations.
 * It provides an interface for students to view their academic performance,
 * check lesson schedules, and manage student profile data.
 */
public class StudentController {

    private final StudentService studentManagementService = new StudentServiceImpl();
    private final JournalService journalService;
    private final ScheduleService scheduleService;

    /**
     * Constructs a StudentController with the required academic services.
     *
     * @param journalService  the service handling student grades
     * @param scheduleService the service handling lesson schedules
     */
    public StudentController(JournalService journalService, ScheduleService scheduleService) {
        this.journalService = journalService;
        this.scheduleService = scheduleService;
    }

    /**
     * Displays the student dashboard menu and handles user interactions.
     *
     * @param scanner   the scanner used for console input
     * @param studentId the ID of the currently logged-in student
     */
    public void displayMenu(Scanner scanner, int studentId) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Student Dashboard ---");
            System.out.println("1. View My Grades (Record Book)");
            System.out.println("2. View Schedule for a Specific Day");
            System.out.println("3. View All Registered Students");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> System.out.println(journalService.generateRecordBook(studentId));
                case "2" -> handleViewSchedule(scanner);
                case "3" -> getAll();
                case "4" -> running = false;
                default -> System.out.println("[WARNING] Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Internal helper to prompt for a day and display the corresponding schedule.
     */
    private void handleViewSchedule(Scanner scanner) {
        System.out.print("Enter Day of Week (e.g., MONDAY or ПОНЕДІЛОК): ");
        String input = scanner.nextLine().trim().toUpperCase();

        try {
            // We use a helper similar to the one in ScheduleService to ensure consistent parsing
            DayOfWeek day = parseDay(input);
            List<Lesson> lessons = scheduleService.getLessonsByDay(day);

            if (lessons.isEmpty()) {
                System.out.println("No lessons scheduled for " + day);
            } else {
                System.out.println("\n--- Schedule for " + day + " ---");
                lessons.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("[ERROR] Invalid day entered. Please check your spelling.");
        }
    }

    /**
     * Helper to parse day input for schedule viewing.
     */
    private DayOfWeek parseDay(String day) {
        return switch (day) {
            case "ПОНЕДІЛОК", "MONDAY" -> DayOfWeek.MONDAY;
            case "ВІВТОРОК", "TUESDAY" -> DayOfWeek.TUESDAY;
            case "СЕРЕДА", "WEDNESDAY" -> DayOfWeek.WEDNESDAY;
            case "ЧЕТВЕР", "THURSDAY" -> DayOfWeek.THURSDAY;
            case "П'ЯТНИЦЯ", "FRIDAY" -> DayOfWeek.FRIDAY;
            case "СУБОТА", "SATURDAY" -> DayOfWeek.SATURDAY;
            case "НЕДІЛЯ", "SUNDAY" -> DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * Assigns the student with the given ID as a head student.
     *
     * @param id the student's unique identifier
     */
    public void assignHeadStudent(int id) {
        studentManagementService.assignHeadStudent(id);
    }

    /**
     * Creates and adds a new Student parsed from a single-line input string.
     * * @param input raw string containing student details (Name, Surname, Lastname, Group, Email, Password)
     */
    public void create(String input) {
        String normalized = input.trim().replace(",", " ");
        String[] parts = normalized.split("\\s+");
        if (parts.length != 6) {
            System.out.println("Error: Expected 6 fields (Name Surname Lastname Group Email Password).");
            return;
        }

        try {
            Student student = new Student(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
            if (studentManagementService.add(student)) {
                System.out.println("Student added successfully!");
            } else {
                System.out.println("Student wasn't added.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Removes the student with the given identifier from the system.
     *
     * @param id the identifier of the student to remove
     */
    public void delete(int id) {
        if (studentManagementService.delete(id)) {
            System.out.println("Student successfully deleted.");
        } else {
            System.out.println("Student with ID " + id + " was not found.");
        }
    }

    /**
     * Retrieves and prints a list of all Student instances in the repository.
     */
    public void getAll() {
        List<User> users = studentManagementService.getAll();
        boolean found = false;
        for (User user : users) {
            if (user instanceof Student student) {
                System.out.println(student);
                found = true;
            }
        }
        if (!found) {
            System.err.println("No students found in the repository.\n");
        }
    }
}