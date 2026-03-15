package ua.knd11.controller;

import ua.knd11.model.Student;
import ua.knd11.service.UserService;
import ua.knd11.service.impl.StudentServiceImpl;

public class StudentController {
    private final UserService service = new StudentServiceImpl();

    public void create(String input) {

        if (input == null || input.trim().isEmpty()) {
            System.out.println("Рядок є пустим. Помилка");
            return;
        }

        String[] parts = input.trim().split("\\s+");
        if (parts.length < 4) {
            System.out.println("Недостатньо данних");
            return;
        }

        String surname = parts[0];
        String name = parts[1];
        String lastname = parts[2];
        String group = parts[3];


        Student student = new Student(surname, name, lastname, group);
        service.add(student);
        System.out.println("Студента успішно додано!");
    }

    public void delete(int id) {
        service.delete(id);
        System.out.println("Операцію видалення виконано.");
    }


    public void getAll() {
        service.getAll();
    }


}
