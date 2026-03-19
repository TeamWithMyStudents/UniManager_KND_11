package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.TeacherService;

public class TeacherServiceImpl extends UserServiceImpl implements TeacherService {

    public TeacherServiceImpl(Teacher[] initialArray) {
        super(initialArray);
    }

    public void calculateTotalSalary(){
        double result = 0;
        for(User user : repository){
            if (user == null) continue;
            Teacher t = (Teacher) user;
            result += t.getSalary();
        }
        System.out.println("Total University Budget: " + result);
    }

    public void filterByDegree(String degree){
        for (User user : repository){
            if (user == null) continue;
            Teacher t = (Teacher) user;
            if (t.getDegree().equalsIgnoreCase(degree) || t.getDegree().toLowerCase().contains(degree.toLowerCase())){
                System.out.println(t);
            }
        }
    }

    @Override
    public void add(User u) {
        Teacher t = (Teacher) u;
        if (t.getSalary() < 0) {
            System.out.println("Error salary in: " + t);
        } else
            super.add(t);
    }
}
