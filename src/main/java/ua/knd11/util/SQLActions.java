package ua.knd11.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ua.knd11.model.Student;
import ua.knd11.model.Teacher;
import ua.knd11.model.User;
import ua.knd11.model.enums.StudentRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Utility class providing static methods for interacting with the PostgreSQL database.
 * Handles the full lifecycle of data persistence, including table creation,
 * CRUD operations for Students and Teachers, and dynamic updates via JDBC.
 */
public final class SQLActions {
    /** The connection string for the database, retrieved from the environment variables. */
    private static final String DATABASE_URL = Objects.requireNonNull(System.getenv("DATABASE_URL"));

    /** SQL query to insert a new record into the STUDENTS table. */
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_ADD_STUDENT = """
            INSERT INTO STUDENTS (name, surname, "group" , role, email, password)
            VALUES (?, ?, ?, ?, ?, ?);
            """;

    /** SQL query to insert a new record into the TEACHERS table. */
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_ADD_TEACHER = """
            INSERT INTO TEACHERS (name, surname, department, degree, salary, email, password)
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;

    /** SQL query to retrieve all records from the STUDENTS table. */
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_GET_STUDENTS = "SELECT * FROM STUDENTS";

    /** SQL query to retrieve all records from the TEACHERS table. */
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_GET_TEACHERS = "SELECT * FROM TEACHERS";

    /** * SQL query that performs a UNION to retrieve base account data
     * (name, surname, email, password) for all users in the system.
     */
    private static final String QUERY_GET_ALL_USERS = "SELECT name, surname, email, password FROM students UNION SELECT name, surname, email, password FROM teachers";

    /** SQL query to delete a specific teacher based on their unique ID. */
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_DELETE_TEACHER_BY_ID = "DELETE FROM TEACHERS WHERE id = ?";

    /** SQL query to delete a specific student based on their unique ID. */
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_DELETE_STUDENT_BY_ID = "DELETE FROM STUDENTS WHERE id = ?";

    /** SQL query to retrieve a student's full record using their email address. */
    private static final String QUERY_GET_STUDENT_BY_EMAIL = "SELECT * FROM STUDENTS WHERE email = ?";

    /** SQL query to retrieve a teacher's full record using their email address. */
    private static final String QUERY_GET_TEACHER_BY_EMAIL = "SELECT * FROM TEACHERS WHERE email = ?";

    /* Update Queries for Students */

    /** SQL query to update a student's first name by ID. */
    private static final String QUERY_SET_NAME_STUDENT_BY_ID = "UPDATE STUDENTS SET name = ? WHERE id = ?";
    /** SQL query to update a student's last name by ID. */
    private static final String QUERY_SET_SURNAME_STUDENT_BY_ID = "UPDATE STUDENTS SET surname = ? WHERE id = ?";
    /** SQL query to update a student's academic group by ID. */
    private static final String QUERY_SET_GROUP_STUDENT_BY_ID = "UPDATE STUDENTS SET \"group\" = ? WHERE id = ?";
    /** SQL query to update a student's email address by ID. */
    private static final String QUERY_SET_EMAIL_STUDENT_BY_ID = "UPDATE STUDENTS SET email = ? WHERE id = ?";

    /* Update Queries for Teachers */

    /** SQL query to update a teacher's first name by ID. */
    private static final String QUERY_SET_NAME_TEACHER_BY_ID = "UPDATE TEACHERS SET name = ? WHERE id = ?";
    /** SQL query to update a teacher's last name by ID. */
    private static final String QUERY_SET_SURNAME_TEACHER_BY_ID = "UPDATE TEACHERS SET surname = ? WHERE id = ?";
    /** SQL query to update a teacher's academic department by ID. */
    private static final String QUERY_SET_DEPARTMENT_TEACHER_BY_ID = "UPDATE TEACHERS SET department = ? WHERE id = ?";
    /** SQL query to update a teacher's academic degree by ID. */
    private static final String QUERY_SET_DEGREE_TEACHER_BY_ID = "UPDATE TEACHERS SET degree = ? WHERE id = ?";
    /** SQL query to update a teacher's salary amount by ID. */
    private static final String QUERY_SET_SALARY_TEACHER_BY_ID = "UPDATE TEACHERS SET salary = ? WHERE id = ?";
    /** SQL query to update a teacher's email address by ID. */
    private static final String QUERY_SET_EMAIL_TEACHER_BY_ID = "UPDATE TEACHERS SET email = ? WHERE id = ?";

    /* DDL Table Creation Queries */

    /** SQL DDL statement to create the STUDENTS table if it does not already exist. */
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

    /** SQL DDL statement to create the TEACHERS table if it does not already exist. */
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

    /**
     * Establishes a raw connection to the database.
     * @return a {@link Connection} object
     * @throws SQLException if a database access error occurs
     */
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    /**
     * Initializes the database by creating the STUDENTS and TEACHERS tables if they do not exist.
     * @throws RuntimeException if table creation fails due to a database error.
     */
    public static void initDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(QUERY_CREATE_STUDENTS_TABLE);
            stmt.executeUpdate(QUERY_CREATE_TEACHERS_TABLE);
        } catch (SQLException e) {
            System.err.println("Database initialization failed");
            throw new RuntimeException(e);
        }
    }

    // --- INSERTION METHODS ---

    /**
     * Inserts a new student record into the database.
     * @param student the {@link Student} object to persist
     * @throws RuntimeException if the SQL execution fails
     */
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

    /**
     * Inserts a new teacher record into the database.
     * @param teacher the {@link Teacher} object to persist
     * @throws RuntimeException if the SQL execution fails
     */
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

    // --- RETRIEVAL METHODS ---

    /**
     * Retrieves a union of all unique users (name, surname, email, password) from both tables.
     * @return an {@link ArrayList} of anonymous {@link User} implementations
     */
    @NotNull
    @Contract(" -> new")
    public static ArrayList<User> retrieveUsersFromDB() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY_GET_ALL_USERS)) {
            return new ArrayList<>(parseUsersFromResultSet(rs));
        } catch (SQLException e) {
            System.err.println("Error retrieving STUDENTS table");
            throw new RuntimeException(e);
        }
    }

    /**
     * Fetches all records from the STUDENTS table.
     * @return an {@link ArrayList} of {@link Student} objects
     */
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

    /**
     * Fetches all records from the TEACHERS table.
     * @return an {@link ArrayList} of {@link Teacher} objects
     */
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

    // --- PARSING HELPERS ---

    /**
     * Internal helper to convert a ResultSet into a list of basic User objects.
     */
    @NotNull
    private static List<User> parseUsersFromResultSet(@NotNull ResultSet resultSet) throws SQLException {
        List<User> list = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User(
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            ){};
            list.add(user);
        }
        return list;
    }

    /**
     * Internal helper to convert a ResultSet into a list of Student objects.
     */
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

    /**
     * Internal helper to convert a ResultSet into a list of Teacher objects.
     */
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
            teacher.setIdFromDB(resultSet.getInt("id"));
            list.add(teacher);
        }
        return list;
    }

    // --- DELETION METHODS ---

    /**
     * Deletes a student record by ID.
     * @param id the unique identifier of the student
     */
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

    /**
     * Deletes a teacher record by ID.
     * @param id the unique identifier of the teacher
     */
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

    // --- SEARCH METHODS ---

    /**
     * Finds a student by their unique email.
     * @return {@link Student} or null if not found
     */
    public static Student getStudentByEmail(String email) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_GET_STUDENT_BY_EMAIL)) {
            stmt.setString(1, email);
            List<Student> students = parseStudentsFromResultSet(stmt.executeQuery());
            return students.isEmpty() ? null : students.get(0);
        } catch (SQLException e) {
            throw new RuntimeException("Error searching student by email", e);
        }
    }

    /**
     * Finds a teacher by their unique email.
     * @return {@link Teacher} or null if not found
     */
    public static Teacher getTeacherByEmail(String email) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_GET_TEACHER_BY_EMAIL)) {
            stmt.setString(1, email);
            List<Teacher> teachers = parseTeachersFromResultSet(stmt.executeQuery());
            return teachers.isEmpty() ? null : teachers.get(0);
        } catch (SQLException e) {
            throw new RuntimeException("Error searching teacher by email", e);
        }
    }

    /**
     * Finds a student by their unique database ID.
     * @return {@link Student} or null if not found
     */
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

    // --- UPDATE METHODS (STUDENTS) ---

    /** Updates the name of a student in the DB. */
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

    /** Updates the surname of a student in the DB. */
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

    /** Updates the group of a student in the DB. */
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

    /** Updates the email of a student in the DB. */
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

    // --- UPDATE METHODS (TEACHERS) ---

    /** Updates the name of a teacher in the DB. */
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

    /** Updates the surname of a teacher in the DB. */
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

    /** Updates the department of a teacher in the DB. */
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

    /** Updates the degree of a teacher in the DB. */
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

    /** Updates the salary of a teacher in the DB. */
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

    /** Updates the email of a teacher in the DB. */
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

    // --- CORE SQL EXECUTION HELPERS ---

    /**
     * Executes a generic SQL update with a variable number of parameters.
     * @param sql the SQL string to execute
     * @param params the objects to bind to the statement
     */
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

    /**
     * Changes a student's role in the database.
     * @param id the student ID
     * @param role the new {@link StudentRole}
     */
    public static void updateStudentRole(int id, StudentRole role) {
        executeUpdate("UPDATE STUDENTS SET role = ? WHERE id = ?", role.name(), id);
    }

    /**
     * Resets all 'HEAD_STUDENT' roles to 'REGULAR' for a specific group.
     * @param groupName the name of the group to demote
     */
    public static void demoteAllHeadsInGroup(String groupName) {
        executeUpdate("UPDATE STUDENTS SET role = 'REGULAR' WHERE \"group\" = ? AND role = 'HEAD_STUDENT'", groupName);
    }

    /** Enum representing the tables managed by this utility. */
    public enum TableName {
        USERS, TEACHERS, STUDENTS
    }
}
