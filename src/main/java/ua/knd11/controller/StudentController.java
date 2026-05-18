package ua.knd11.controller;

import ua.knd11.model.Lesson;
import ua.knd11.model.Student;
import ua.knd11.model.User;
import ua.knd11.service.JournalService;
import ua.knd11.service.ScheduleService;
import ua.knd11.service.StudentService;
import ua.knd11.service.impl.StudentServiceImpl;
import ua.knd11.util.FieldValidator;

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
     * Service layer instance for student data processing
     */
    private final StudentService service;

    /**
     * Constructs a StudentController with the default service implementation.
     */
    public StudentController() {
        this(new StudentServiceImpl());
    }

    /**
     * Constructs a StudentController with a provided service instance.
     * Allows for dependency injection, primarily for testing purposes.
     *
     * @param service the StudentService implementation to use
     */
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * Assigns a specific student as the head student based on their unique ID.
     *
     * @param id the student's unique identifier
     */
    public void assignHeadStudent(int id) {
        studentManagementService.assignHeadStudent(id);
    }

    /**
     * Parses a raw string input from the terminal to add a new student.
     * The input is expected to contain exactly 5 space-separated fields:
     * Name, Surname, Group, Email, and Password.
     *
     * @param value the raw string containing student data from the terminal
     */
    public void addStudentFromTerminal(String value) {
        String[] parts = value.trim().split("\\s+");
        if (parts.length != 5) {
            System.out.println("Error: Expected 5 fields (Name Surname Group Email@example.com Password).");
            return;
        }
        try {
            service.addStudent(createStudentWithParts(parts));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Validates individual data parts and constructs a new Student object.
     * Performs field-level validation using {@link FieldValidator}.
     *
     * @param parts an array of strings representing the student's attributes
     * @return a new {@link Student} object populated with the validated data
     */
    private Student createStudentWithParts(String[] parts) throws IllegalArgumentException {
        FieldValidator.validateAlphabeticString("name", parts[0]);
        FieldValidator.validateAlphabeticString("surname", parts[1]);
        FieldValidator.validateGroup(parts[2]);
        FieldValidator.validateEmail(parts[3]);
        FieldValidator.validatePassword(parts[4]);
        return new Student(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    /**
     * Deletes a student from the system using their unique identifier.
     *
     * @param id the unique ID of the student to be removed
     */
    public void deleteStudent(int id) {
        service.deleteStudent(id);
    }

    /**
     * Retrieves all students from the service and prints them to the console.
     * Displays a "No students found" message if the collection is empty.
     */
    public void getAll() {
        List<Student> students = service.getAllStudents();
        students.stream().filter(Objects::nonNull).forEach(System.out::println);
        if (students.isEmpty()) {
            System.out.println("No students found");
        }
    }
}