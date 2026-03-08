package ua.knd11.viewer;

import java.util.Scanner;
import ua.knd11.controller.StudentController;

public class ApplicationDisplay {
    private final StudentController controller = new StudentController();


    Scanner sc = new Scanner(System.in);
    public void start(){
        while(true){
            System.out.println(""" 
                    
                    
                    Параметри меню:
                    1. Додати Студента
                    2. Показати всіх студентів
                    3. Видалити Студент по ID
                    4. Вихід
                    """);
            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                System.out.println("Будь ласка, введіть дані студента: ПІБ, Група");
                String input = sc.nextLine();
                controller.create(input);
            }

            else if(choice.equals("2")){
                controller.getService().getAll();
            }

            else if(choice.equals("3")){
                System.out.println("Введіть ID студента щоб видалити");
                if (sc.hasNextInt()) {
                    int id = sc.nextInt();
                    sc.nextLine();
                    controller.delete(id);

                } else {
                    System.out.println("Помилка: ID має бути числом!");
                    sc.nextLine();
                }
            }
            else if(choice.equals("4")){
                System.out.println("Вихід");
                sc.close();
                break;
            }
            else {
                System.out.println("Напишіть число від 1 до 4");
            }
        }
    }
}
