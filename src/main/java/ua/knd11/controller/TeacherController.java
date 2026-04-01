package ua.knd11.controller;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.impl.TeacherServiceImpl;

import java.util.List;

public class TeacherController {

    private final TeacherServiceImpl service = new TeacherServiceImpl();

    //The method that creates a teacher for the ConsoleMenu class
    public void create(String input) {
        //a variable that replaces spaces with commas
        String normalized = input.trim().replace(",", " ");
        //an array that divides the values entered by the user and stores them in cells
        String[] parts = normalized.split("\\s+");

        //simple checking the length of an array
        if (parts.length != 7) {
            System.out.println("Error: Expected 7 fields (Name Surname Dept Degree Salary Email@example.com Password).");
            return;
        }

        //assigning variables to specific array cells
        String name = parts[0];
        String surname = parts[1];
        String department = parts[2];
        String degree = parts[3];
        double salary;
        String email = parts[5];
        String password = parts[6];

        //A new teacher is created, into which the previously created variables are entered.
        //The add method from the user class is used to add the teacher.
        try {
            salary = Double.parseDouble(parts[4]);
            Teacher teacher = new Teacher(name, surname, department, degree, salary, email, password);
            if (service.add(teacher)) {
                System.out.println("Teacher added successfully!");
            } else {
                System.out.println("Teacher wasn't added.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //A method that displays all teachers
    public void getAll() {
        //A list is created to which users are transferred from the repository
        List<User> teachers = service.getAll();
        //Variable to store "Teacher found?"
        boolean found = false;
        //Iterates through all users from the list, where if a teacher is found, he is displayed
        //The variable is set to true
        for (User teacher : teachers) {
            if (teacher instanceof Teacher) {
                System.out.println(teacher);
                found = true;
            }
        }
        //Checks whether a variable contains a teacher.
        //If not found, an error is displayed.
        if (!found) {
            System.err.println("No teachers found in the repository.\n");
        }
    }

    //The method calculates teachers' salaries
    public void calculateTotalSalary() {
        service.calculateTotalSalary();
    }

    //A method that filters teachers by degree
    public void filterByDegree(String degree) {
        service.filterByDegree(degree);
    }
}

