package ua.knd11.service.impl;

import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.service.TeacherService;

public class TeacherServiceImpl extends UserServiceImpl implements TeacherService {
   // public TeacherServiceImpl() {super();}
    public void calculateTotalSalary() {
        double result = repository.stream().mapToDouble(user -> user instanceof Teacher t ? t.getSalary() : 0).sum();
        System.out.println("Total University Budget: " + result);
    }

    public boolean filterByDegree(String degreeQuery) {
                if (degreeQuery == null) {return false;}
                String normalizedDegree = degreeQuery.trim();
                if (normalizedDegree.isEmpty()) {return false;}
                var matches = repository.stream().filter(user -> user instanceof Teacher t &&
                        t.getDegree() != null &&
                        !t.getDegree().isBlank() &&
                        normalizedDegree.equalsIgnoreCase(t.getDegree().trim())).toList();
                matches.forEach(System.out::println);
                return !matches.isEmpty(); }
@Override
public void add(User u) {
    if (u == null) {throw new IllegalArgumentException("User cannot be null");}
    if (!(u instanceof Teacher t)) {throw new IllegalArgumentException("Expected Teacher instance");}
    boolean hasEmptyFields = isNullOrBlank(t.getName())
            || isNullOrBlank(t.getSurname())
            || isNullOrBlank(t.getDepartment())
            || isNullOrBlank(t.getDegree());
    if (hasEmptyFields) {throw new IllegalArgumentException("Teacher has empty required fields: " + t);}
    if (!Double.isFinite(t.getSalary()) || t.getSalary() <= 0) {throw new IllegalArgumentException("Teacher salary must be a finite positive number");}
    super.add(t);
}
private boolean isNullOrBlank(String str) {
    return str == null || str.isBlank();
}}

