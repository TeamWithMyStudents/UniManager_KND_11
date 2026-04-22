package ua.knd11.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ua.knd11.model.Student;
import ua.knd11.model.Teacher;
import ua.knd11.model.enums.StudentRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SQLActions {
    // TODO: можно реализовать возможность сеттеров (тяжело)
    private static final String DATABASE_URL = Objects.requireNonNull(System.getenv("DATABASE_URL"));

    @SuppressWarnings("SqlResolve")
    private static final String QUERY_ADD_STUDENT = """
            INSERT INTO STUDENTS (name, surname, "group" , role, email, password)
            VALUES (?, ?, ?, ?, ?, ?);
            """;

    @SuppressWarnings("SqlResolve")
    private static final String QUERY_ADD_TEACHER = """
            INSERT INTO TEACHERS (name, surname, department, degree, salary, email, password)
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_GET_STUDENTS = "SELECT * FROM STUDENTS";

    @SuppressWarnings("SqlResolve")
    private static final String QUERY_GET_TEACHERS = "SELECT * FROM TEACHERS";

    @SuppressWarnings("SqlResolve")
    private static final String QUERY_DELETE_TEACHER_BY_ID = "DELETE FROM TEACHERS WHERE id = ?";

    @SuppressWarnings("SqlResolve")
    private static final String QUERY_DELETE_STUDENT_BY_ID = "DELETE FROM STUDENTS WHERE id = ?";

    private static final String QUERY_SET_NAME_STUDENT_BY_ID = "UPDATE STUDENTS SET name = ? WHERE id = ?";
    private static final String QUERY_SET_SURNAME_STUDENT_BY_ID = "UPDATE STUDENTS SET surname = ? WHERE id = ?";
    private static final String QUERY_SET_GROUP_STUDENT_BY_ID = "UPDATE STUDENTS SET \"group\" = ? WHERE id = ?";
    private static final String QUERY_SET_EMAIL_STUDENT_BY_ID = "UPDATE STUDENTS SET email = ? WHERE id = ?";

    private static final String QUERY_SET_NAME_TEACHER_BY_ID = "UPDATE TEACHERS SET name = ? WHERE id = ?";
    private static final String QUERY_SET_SURNAME_TEACHER_BY_ID = "UPDATE TEACHERS SET surname = ? WHERE id = ?";
    private static final String QUERY_SET_DEPARTMENT_TEACHER_BY_ID = "UPDATE TEACHERS SET department = ? WHERE id = ?";
    private static final String QUERY_SET_DEGREE_TEACHER_BY_ID = "UPDATE TEACHERS SET degree = ? WHERE id = ?";
    private static final String QUERY_SET_SALARY_TEACHER_BY_ID = "UPDATE TEACHERS SET salary = ? WHERE id = ?";
    private static final String QUERY_SET_EMAIL_TEACHER_BY_ID = "UPDATE TEACHERS SET email = ? WHERE id = ?";

    private static final String QUERY_CREATE_STUDENTS_TABLE = """
            CREATE TABLE IF NOT EXISTS STUDENTS (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                surname VARCHAR(255) NOT NULL,
                "group" VARCHAR(255) NOT NULL,
                role VARCHAR(255) NOT NULL,
                email VARCHAR(255) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL
            );
            """;

    private static final String QUERY_CREATE_TEACHERS_TABLE = """
            CREATE TABLE IF NOT EXISTS TEACHERS (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                surname VARCHAR(255) NOT NULL,
                department VARCHAR(255) NOT NULL,
                degree VARCHAR(255) NOT NULL,
                salary DOUBLE PRECISION NOT NULL,
                email VARCHAR(255) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL
            );
            """;


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }


    public static void initDatabase() {// TODO: если коннекта к дб нету, код не должен продолжать работу
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(QUERY_CREATE_STUDENTS_TABLE);
            stmt.executeUpdate(QUERY_CREATE_TEACHERS_TABLE);
        } catch (SQLException e) {
            System.err.println("Database initialization failed");
            throw new RuntimeException(e);
        }
    }


    public static void addStudentToDB(Student student) {
        if (student == null) return;
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_ADD_STUDENT)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getSurname());
            stmt.setString(3, student.getGroup());
            stmt.setString(4, String.valueOf(student.getRole()));
            stmt.setString(5, student.getEmail());
            stmt.setString(6, student.getPassword());
            stmt.executeUpdate();
            System.out.println("Student added to database");
        } catch (SQLException e) {
            System.err.println("Failed to add student to database");
            throw new RuntimeException(e);
        }
    }

    public static void addTeacherToDB(Teacher teacher) {
        if (teacher == null) return;
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_ADD_TEACHER)) {
            stmt.setString(1, teacher.getName());
            stmt.setString(2, teacher.getSurname());
            stmt.setString(3, teacher.getDepartment());
            stmt.setString(4, teacher.getDegree());
            stmt.setDouble(5, teacher.getSalary());
            stmt.setString(6, teacher.getEmail());
            stmt.setString(7, teacher.getPassword());
            stmt.executeUpdate();
            System.out.println("Teacher added to database");
        } catch (SQLException e) {
            System.err.println("Failed to add teacher to database");
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Contract(" -> new")
    public static ArrayList<Student> retrieveStudentsFromDB() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY_GET_STUDENTS)) {
            return new ArrayList<>(parseStudentsFromResultSet(rs));

        } catch (SQLException e) {
            System.err.println("Error retrieving STUDENTS table");
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Contract(" -> new")
    public static ArrayList<Teacher> retrieveTeachersFromDB() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY_GET_TEACHERS)) {
            return new ArrayList<>(parseTeachersFromResultSet(rs));

        } catch (SQLException e) {
            System.err.println("Error retrieving TEACHERS table");
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private static List<Student> parseStudentsFromResultSet(@NotNull ResultSet resultSet) throws SQLException {
        List<Student> list = new ArrayList<>();
        while (resultSet.next()) {
            Student student = new Student(
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getString("group"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );

            String roleStr = resultSet.getString("role");
            if ("HEAD_STUDENT".equals(roleStr)) {
                student.setRole(StudentRole.HEAD_STUDENT);
            }
            student.setIdFromDB(resultSet.getInt("id"));
            list.add(student);
        }
        return list;
    }

    @NotNull
    private static List<Teacher> parseTeachersFromResultSet(@NotNull ResultSet resultSet) throws SQLException {
        List<Teacher> list = new ArrayList<>();
        while (resultSet.next()) {
            if (resultSet.getString("salary").equals("null")) {
                System.err.println("Teacher with email " + resultSet.getString("email") + " has wrong salary");
                continue;
            }
            Teacher teacher = new Teacher(
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getString("department"),
                    resultSet.getString("degree"),
                    resultSet.getDouble("salary"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );
            list.add(teacher);
        }
        return list;
    }

    public static void deleteStudentFromDBWithID(int id) {
        if (id < 0) return;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_DELETE_STUDENT_BY_ID)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Student with ID " + id + " deleted from database");
        } catch (SQLException e) {
            System.err.println("Failed to delete student with ID " + id + " from database");
            throw new RuntimeException(e);
        }
    }

    public static void deleteTeacherFromDBWithID(int id) {
        if (id < 0) return;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_DELETE_TEACHER_BY_ID)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Teacher with ID " + id + " deleted from database");
        } catch (SQLException e) {
            System.err.println("Failed to delete teacher with ID " + id + " from database");
            throw new RuntimeException(e);
        }
    }

    public static Student getStudentById(int id) {
        String query = "SELECT * FROM STUDENTS WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            var students = parseStudentsFromResultSet(stmt.executeQuery());

            return students.isEmpty() ? null : students.get(0);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find student with ID: " + id + "in database");
        }
    }
    public static void updateStudentName(int id, String name) {
        FieldValidator.validateId(id);
        FieldValidator.validateAlphabeticString(name, "name");
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_NAME_STUDENT_BY_ID)) {
            stmt.setString(1, name);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated student name: " + name);
        } catch (SQLException e){
            System.out.println("Failed to update student name: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public static void updateStudentSurname(int id, String surname) {
        FieldValidator.validateId(id);
        FieldValidator.validateAlphabeticString(surname, "surname");
        try(Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_SURNAME_STUDENT_BY_ID)) {
            stmt.setString(1, surname);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated student surname: " + surname);
        } catch (SQLException e){
            System.out.println("Failed to update student surname: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public static void updateStudentGroup(int id, String group) {
        FieldValidator.validateId(id);
        FieldValidator.validateGroup(group);
        try(Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_GROUP_STUDENT_BY_ID)){
            stmt.setString(1, group);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated student group: " + group);
        } catch (SQLException e){
            System.out.println("Failed to update student group: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public static void updateStudentEmail(int id, String email) {
        FieldValidator.validateId(id);
        FieldValidator.validateEmail(email);
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_EMAIL_STUDENT_BY_ID)){
            stmt.setString(1, email);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated student email: " + email);
        } catch (SQLException e){
            System.out.println("Failed to update student email: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public static void updateTeacherName(int id, String name) {
        FieldValidator.validateId(id);
        FieldValidator.validateAlphabeticString(name, "name");
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_NAME_TEACHER_BY_ID)){
            stmt.setString(1, name);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated teacher name: " + name);
        } catch (SQLException e){
            System.out.println("Failed to update teacher name: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public static void updateTeacherSurname(int id, String surname) {
        FieldValidator.validateId(id);
        FieldValidator.validateAlphabeticString(surname, "surname");
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_SURNAME_TEACHER_BY_ID)){
            stmt.setString(1, surname);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated teacher surname: " + surname);
        } catch (SQLException e){
            System.out.println("Failed to update teacher surname: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public static void updateTeacherDepartment(int id, String department) {
        FieldValidator.validateId(id);
        FieldValidator.validateAlphabeticString(department, "department");
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_DEPARTMENT_TEACHER_BY_ID)){
            stmt.setString(1, department);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated teacher department: " + department);
        } catch (SQLException e){
            System.out.println("Failed to update teacher department: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public static void updateTeacherDegree(int id, String degree) {
        FieldValidator.validateId(id);
        FieldValidator.validateAlphabeticString(degree, "degree");
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_DEGREE_TEACHER_BY_ID)){
            stmt.setString(1, degree);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated teacher degree: " + degree);
        } catch (SQLException e){
            System.out.println("Failed to update teacher degree: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public static void updateTeacherSalary(int id, double salary) {
        FieldValidator.validateId(id);
        FieldValidator.validateSalary(salary);
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_SALARY_TEACHER_BY_ID)){
            stmt.setDouble(1, salary);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated teacher salary: " + salary);
        } catch (SQLException e){
            System.out.println("Failed to update teacher salary: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public static void updateTeacherEmail(int id, String email) {
        FieldValidator.validateId(id);
        FieldValidator.validateEmail(email);
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_EMAIL_TEACHER_BY_ID)){
            stmt.setString(1, email);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated teacher email: " + email);
        } catch (SQLException e){
            System.out.println("Failed to update teacher email: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    private static void executeUpdate(String sql, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL: " + sql, e);
        }
    }

    public static void updateStudentRole(int id, StudentRole role) {
        executeUpdate("UPDATE STUDENTS SET role = ? WHERE id = ?", role.name(), id);
    }

    public static void demoteAllHeadsInGroup(String groupName) {
        executeUpdate("UPDATE STUDENTS SET role = 'REGULAR' WHERE \"group\" = ? AND role = 'HEAD_STUDENT'", groupName);
    }

    public enum TableName {
        USERS, TEACHERS, STUDENTS
    }
}
