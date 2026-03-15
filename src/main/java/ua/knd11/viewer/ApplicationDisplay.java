package ua.knd11.viewer;

import java.util.Scanner;
import ua.knd11.controller.StudentController;

public class ApplicationDisplay {
    private final StudentController controller = new StudentController();
    private final Scanner sc = new Scanner(System.in);

    public void start(){
        label:
        while(true){
            System.out.println(""" 
                    
                    
                    Параметри меню:
                    1. Додати Студента
                    2. Показати всіх студентів
                    3. Видалити Студент по ID
                    4. Вихід
                    """);
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Будь ласка, введіть дані студента: ПІБ, Група");
                    String input = sc.nextLine();
                    controller.create(input);
                    break;
                case "2":
                    controller.getAll();
                    break;
                case "3":
                    System.out.println("Введіть ID студента щоб видалити");
                    if (sc.hasNextInt()) {
                        int id = sc.nextInt();
                        sc.nextLine();
                        controller.delete(id);
                    } else {
                        System.out.println("Помилка: ID має бути числом!");
                        sc.nextLine();
                    }
                    break;
                case "4":
                    System.out.println("Вихід");
                    sc.close();
                    break label;
                default:
                    System.out.println("Напишіть число від 1 до 4");
                    break;
            }
        }
    }
}
