package ua.knd11.controller;

import ua.knd11.model.Teacher;
import ua.knd11.service.JournalService;
import ua.knd11.service.ScheduleService;
import ua.knd11.service.impl.TeacherServiceImpl;
import ua.knd11.util.SQLActions;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Controller class responsible for handling teacher-related operations.
 * It acts as an intermediary between the user interface and the business logic layers
 * for managing teacher profiles, academic schedules, and student grades.
 */
public class TeacherController {

    private final TeacherServiceImpl teacherService = new TeacherServiceImpl();
    private final JournalService journalService;
    private final ScheduleService scheduleService;

    /**
     * Constructs a TeacherController with the required academic services.
     *
     * @param journalService  the service handling student grades
     * @param scheduleService the service handling lesson schedules
     */
    public TeacherController(JournalService journalService, ScheduleService scheduleService) {
        this.journalService = journalService;
        this.scheduleService = scheduleService;
    }

    /**
     * Parses a raw string input from the terminal and attempts to add a new teacher.
     * Expects exactly 7 space-separated fields: Name, Surname, Department, Degree, Salary, Email, and Password.
     *
     * @param value the raw string input containing teacher data
     */
    public void addTeacherFromTerminal(String value) {
        String[] parts = value.trim().split("\\s+");
        if (parts.length != 7) {
            System.err.println("Error: Expected 7 fields (Name Surname Dept Degree Salary Email@example.com Password).");
            return;
        }
        try {
            SQLActions.addTeacherToDB(teacherService.createTeacherWithParts(parts));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Fetches all teachers from the service layer and prints them to the terminal.
     * Displays an empty state message if no teachers are currently registered.
     */
    private void getAll() {
        List<Teacher> teachers = SQLActions.retrieveTeachersFromDB();
        teachers.stream().filter(Objects::nonNull).forEach(System.out::println);
        if (teachers.isEmpty()) {
            System.out.println("No teachers found");
        }
    }

    /**
     * Triggers the calculation of the total salary expenditure for all teachers.
     * Results are handled by the service layer's output.
     * Displays the teacher-specific menu and handles user interactions.
     *
     * @param scanner the scanner used for console input
     */
    public void displayMenu(Scanner scanner) { //TODO: Implement db integration to grades and lessons
        boolean running = true;
        while (running) {
            System.out.println("\n--- Teacher Management Menu ---");
            System.out.println("1. Assign Grade to Student");
            System.out.println("2. Add Lesson to Schedule");
            System.out.println("3. View All Teachers");
            System.out.println("4. Calculate Total Salaries");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> handleAssignGrade(scanner);
                case "2" -> handleAddLesson(scanner);
                case "3" -> getAll();
                case "4" -> calculateTotalSalary();
                case "5" -> running = false;
                default -> System.out.println("[WARNING] Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Internal helper to collect input and assign a grade via JournalService.
     */
    private void handleAssignGrade(Scanner scanner) {
        try {
            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Subject: ");
            String subject = scanner.nextLine();
            System.out.print("Enter Score: ");
            int score = Integer.parseInt(scanner.nextLine());

            journalService.assignGrade(id, subject, score);
        } catch (NumberFormatException e) {
            System.err.println("[ERROR] Student ID and Score must be numeric.");
        }
    }

    /**
     * Internal helper to collect input and add a lesson via ScheduleService.
     */
    private void handleAddLesson(Scanner scanner) {
        System.out.print("Enter Day (e.g., MONDAY): ");
        String day = scanner.nextLine();
        System.out.print("Enter Time (e.g., 10:30): ");
        String time = scanner.nextLine();
        System.out.print("Enter Subject: ");
        String subject = scanner.nextLine();
        System.out.print("Enter Teacher Surname: ");
        String surname = scanner.nextLine();

        scheduleService.addLesson(day, time, subject, surname);
    }

    /**
     * Calculates and displays the total salary of all teachers.
     */
    public void calculateTotalSalary() {
        // Просто викликаємо метод сервісу, бо він сам друкує результат у консоль
        teacherService.calculateTotalSalary();
    }

    /**
     * Filters and displays teachers based on their academic degree.
     *
     * @param degree the academic degree string to filter by (e.g., "PhD", "Master")
     */
    public void filterByDegree(String degree) {
        teacherService.filterByDegree(degree);
    }
}