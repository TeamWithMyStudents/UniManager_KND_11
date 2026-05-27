package ua.knd11.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ua.knd11.model.Grade;
import ua.knd11.model.Lesson;
import ua.knd11.model.Student;
import ua.knd11.model.Teacher;
import ua.knd11.model.enums.StudentRole;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Utility class providing static methods for interacting with the PostgreSQL database.
 * Handles the full lifecycle of data persistence, including table creation,
 * CRUD operations for Students and Teachers, and dynamic updates via JDBC.
 */
@SuppressWarnings({"SqlResolve", "unused"})
public final class SQLActions {
    /**
     * SQL query to insert a new record into the STUDENTS table.
     */
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_ADD_STUDENT = """
            INSERT INTO STUDENTS (name, surname, "group" , role, email, password, salt)
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;
    /**
     * SQL query to insert a new record into the TEACHERS table.
     */
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_ADD_TEACHER = """
            INSERT INTO TEACHERS (name, surname, department, degree, salary, email, password, salt)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
            """;
    /**
     * SQL query to insert a new record into the GRADES table.
     */
    private static final String QUERY_ADD_GRADE = """
            INSERT INTO GRADES (name, student_id, subject_name, score) 
            VALUES (?, ?, ?, ?);
            """;

    /**
     * SQL query to insert a new record into the SCHEDULE table.
     */
    private static final String QUERY_ADD_SCHEDULE = """
            INSERT INTO SCHEDULE (dayOfweek, time, subject, teacherSurname) 
            VALUES (?, ?, ?, ?);
            """;

    /**
     * SQL query to retrieve all records from the STUDENTS table.
     */
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_GET_STUDENTS = "SELECT * FROM STUDENTS";

    /**
     * SQL query to retrieve all records from the TEACHERS table.
     */
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_GET_TEACHERS = "SELECT * FROM TEACHERS";

    /**
     * SQL query to delete a specific teacher based on their unique email.
     */
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_DELETE_TEACHER_BY_EMAIL = "DELETE FROM TEACHERS WHERE email = ?";

    /**
     * SQL query to delete a specific student based on their unique email.
     */
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_DELETE_STUDENT_BY_EMAIL = "DELETE FROM STUDENTS WHERE email = ?";

    /**
     * SQL query to delete a specific teacher based on their unique ID.
     */
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_DELETE_TEACHER_BY_ID = "DELETE FROM TEACHERS WHERE id = ?";

    /**
     * SQL query to delete a specific student based on their unique ID.
     */
    @SuppressWarnings("SqlResolve")
    private static final String QUERY_DELETE_STUDENT_BY_ID = "DELETE FROM STUDENTS WHERE id = ?";

    /**
     * SQL query to delete a specific lesson based on their unique ID.
     */
    private static final String QUERY_DELETE_LESSON_BY_ID = "DELETE FROM SCHEDULE WHERE id = ?";

    /**
     * SQL query to delete a specific grade based on their unique ID.
     */
    private static final String QUERY_DELETE_GRADE_BY_ID = "DELETE FROM GRADES WHERE id = ?";

    /**
     * SQL query to retrieve a student's full record using their email address.
     */
    private static final String QUERY_GET_STUDENT_BY_EMAIL = "SELECT * FROM STUDENTS WHERE email = ?";

    /**
     * SQL query to retrieve a teacher's full record using their email address.
     */
    private static final String QUERY_GET_TEACHER_BY_EMAIL = "SELECT * FROM TEACHERS WHERE email = ?";

    /**
     * SQL query to retrieve all SCHEDULE from the database.
     */
    private static final String QUERY_GET_LESSONS = "SELECT * FROM SCHEDULE";

    /**
     * SQL query to retrieve all SCHEDULE for a specific day of the week.
     */
    private static final String QUERY_GET_LESSONS_BY_DAY = "SELECT * FROM SCHEDULE WHERE dayOfWeek = ?";

    /**
     * SQL query to retrieve all grades for a specific student.
     */
    private static final String QUERY_GET_GRADES_BY_STUDENT = "SELECT name, score FROM GRADES WHERE student_id = ?";

    /**
     * SQL query to demote all head students in a group to regular students.
     */
    private static final String QUERY_DEMOTE_HEADS_IN_GROUP = "UPDATE STUDENTS SET role = 'REGULAR' WHERE \"group\" = ? AND role = 'HEAD_STUDENT'";

    /**
     * SQL query to promote a student to a specific role.
     */
    private static final String QUERY_PROMOTE_STUDENT_ROLE = "UPDATE STUDENTS SET role = ? WHERE id = ?";

    /**
     * SQL query to update a student's first name by ID.
     */
    private static final String QUERY_SET_NAME_STUDENT_BY_ID = "UPDATE STUDENTS SET name = ? WHERE id = ?";

    /**
     * SQL query to update a student's last name by ID.
     */
    private static final String QUERY_SET_SURNAME_STUDENT_BY_ID = "UPDATE STUDENTS SET surname = ? WHERE id = ?";

    /**
     * SQL query to update a student's academic group by ID.
     */
    private static final String QUERY_SET_GROUP_STUDENT_BY_ID = "UPDATE STUDENTS SET \"group\" = ? WHERE id = ?";

    /**
     * SQL query to update a student's email address by ID.
     */
    private static final String QUERY_SET_EMAIL_STUDENT_BY_ID = "UPDATE STUDENTS SET email = ? WHERE id = ?";

    // Update Queries for Teachers

    /**
     * SQL query to update a teacher's first name by ID.
     */
    private static final String QUERY_SET_NAME_TEACHER_BY_ID = "UPDATE TEACHERS SET name = ? WHERE id = ?";

    /**
     * SQL query to update a teacher's last name by ID.
     */
    private static final String QUERY_SET_SURNAME_TEACHER_BY_ID = "UPDATE TEACHERS SET surname = ? WHERE id = ?";

    /**
     * SQL query to update a teacher's academic department by ID.
     */
    private static final String QUERY_SET_DEPARTMENT_TEACHER_BY_ID = "UPDATE TEACHERS SET department = ? WHERE id = ?";

    /**
     * SQL query to update a teacher's academic degree by ID.
     */
    private static final String QUERY_SET_DEGREE_TEACHER_BY_ID = "UPDATE TEACHERS SET degree = ? WHERE id = ?";

    /**
     * SQL query to update a teacher's salary amount by ID.
     */
    private static final String QUERY_SET_SALARY_TEACHER_BY_ID = "UPDATE TEACHERS SET salary = ? WHERE id = ?";

    /**
     * SQL query to update a teacher's email address by ID.
     */
    private static final String QUERY_SET_EMAIL_TEACHER_BY_ID = "UPDATE TEACHERS SET email = ? WHERE id = ?";

    /*Update Queries for SCHEDULE*/

    private static final String QUERY_SET_LESSON_TIME_BY_ID = "UPDATE SCHEDULE SET time = ? WHERE id = ?";

    /**
     * SQL query to update a lesson's teacher's last name by ID.
     */
    private static final String QUERY_SET_LESSON_TEACHER_BY_ID = "UPDATE SCHEDULE SET teacherSurname = ? WHERE id = ?";

    /**
     * SQL query to update a grade's score by ID.
     */
    private static final String QUERY_SET_GRADE_SCORE_BY_ID = "UPDATE GRADES SET score = ? WHERE id = ?";

    /**
     * SQL DDL statement to create the STUDENTS table if it does not already exist.
     */
    private static final String QUERY_CREATE_STUDENTS_TABLE = """
            CREATE TABLE IF NOT EXISTS STUDENTS (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                surname VARCHAR(255) NOT NULL,
                "group" VARCHAR(255) NOT NULL,
                role VARCHAR(255) NOT NULL,
                email VARCHAR(255) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL,
                salt VARCHAR(255) NOT NULL
            );
            """;

    /* DDL Table Creation Queries */
    /**
     * SQL DDL statement to create the TEACHERS table if it does not already exist.
     */
    private static final String QUERY_CREATE_TEACHERS_TABLE = """
            CREATE TABLE IF NOT EXISTS TEACHERS (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                surname VARCHAR(255) NOT NULL,
                department VARCHAR(255) NOT NULL,
                degree VARCHAR(255) NOT NULL,
                salary DOUBLE PRECISION NOT NULL,
                email VARCHAR(255) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL,
                salt VARCHAR(255) NOT NULL
            );
            """;
    private static final String QUERY_CREATE_SCHEDULE_TABLE = """
            CREATE TABLE IF NOT EXISTS SCHEDULE (
                id SERIAL PRIMARY KEY,
                dayOfWeek VARCHAR(255) NOT NULL,
                time TIME NOT NULL,
                subject VARCHAR(255) NOT NULL,
                teacherSurname VARCHAR(255) NOT NULL
            );
            """;
    private static final String QUERY_CREATE_GRADES_TABLE = """
            CREATE TABLE IF NOT EXISTS GRADES (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                student_id INT NOT NULL,
                subject_name VARCHAR(255) NOT NULL,
                score INT NOT NULL
            );
            """;

    /**
     * Lazy resolver for the database connection string from environment variables.
     *
     * @return the DATABASE_URL environment variable value
     * @throws NullPointerException if DATABASE_URL is not set
     */
    private static String getDatabaseUrl() {
        return Objects.requireNonNull(System.getenv("DATABASE_URL"), "Environment variable DATABASE_URL must be set");
    }

    /**
     * Establishes a raw connection to the database.
     *
     * @return a {@link Connection} object
     * @throws SQLException if a database access error occurs
     */
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getDatabaseUrl());
    }

    /**
     * Initializes the database by creating the STUDENTS and TEACHERS tables if they do not exist.
     *
     * @throws RuntimeException if database initialization fails
     */
    public static void initDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(QUERY_CREATE_STUDENTS_TABLE);
            stmt.executeUpdate(QUERY_CREATE_TEACHERS_TABLE);
            stmt.executeUpdate(QUERY_CREATE_SCHEDULE_TABLE);
            stmt.executeUpdate(QUERY_CREATE_GRADES_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    // --- INSERTION METHODS ---

    /**
     * Inserts a new student record into the database.
     *
     * @param student the {@link Student} object to persist
     * @throws RuntimeException if the database operation fails
     */
    public static void addStudentToDB(Student student) {
        if (student == null) return;
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_ADD_STUDENT)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getSurname());
            stmt.setString(3, student.getGroup());
            stmt.setString(4, student.getRole() != null ? student.getRole().name() : null);
            stmt.setString(5, student.getEmail());
            stmt.setString(6, student.getPassword());
            stmt.setString(7, student.getSalt());
            stmt.executeUpdate();
            System.out.println("Student added to database");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add student to database", e);
        }
    }

    /**
     * Inserts a new teacher record into the database.
     *
     * @param teacher the {@link Teacher} object to persist
     * @throws RuntimeException if the database operation fails
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
            stmt.setString(8, teacher.getSalt());
            stmt.executeUpdate();
            System.out.println("Teacher added to database");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add teacher to database", e);
        }
    }

    public static void addLessonToDB(Lesson lesson) {
        if (lesson == null) return;
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_ADD_SCHEDULE)) {
            stmt.setString(1, lesson.getDayOfWeek().name());
            stmt.setTime(2, java.sql.Time.valueOf(lesson.getTime()));
            stmt.setString(3, lesson.getSubject());
            stmt.setString(4, lesson.getTeacherSurname());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to add lesson to schedule");
        }
    }

    public static void addGradeToDB(int studentId, String subject_name, int score) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_ADD_GRADE)) {
            String studentName = Objects.requireNonNull(getStudentById(studentId)).getName();
            stmt.setString(1, studentName);
            stmt.setInt(2, studentId);
            stmt.setString(3, subject_name);
            stmt.setInt(4, score);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to add grade");
        }
    }

    // --- RETRIEVAL METHODS ---

    /**
     * Fetches all records from the STUDENTS table.
     *
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
            throw new RuntimeException(e + " Error retrieving STUDENTS table");
        }
    }

    /**
     * Fetches all records from the TEACHERS table.
     *
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
            throw new RuntimeException(e + " Error retrieving TEACHERS table");
        }
    }

    public static List<Lesson> retrieveLessonsFromDB() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY_GET_LESSONS)) {
            return parseLessonsFromResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving SCHEDULE table", e);
        }
    }

    // --- PARSING HELPERS ---

    /**
     * Internal helper to convert a ResultSet into a list of Student objects.
     */
    @NotNull
    private static List<Student> parseStudentsFromResultSet(@NotNull ResultSet resultSet) throws SQLException {
        List<Student> list = new ArrayList<>();
        while (resultSet.next()) {
            String roleStr = resultSet.getString("role");
            int studentId = resultSet.getInt("id");
            String studentName = resultSet.getString("name");
            String studentSurname = resultSet.getString("surname");
            String studentGroup = resultSet.getString("group");
            String studentEmail = resultSet.getString("email");
            String studentPassword = resultSet.getString("password");
            String studentSalt = resultSet.getString("salt");

            StudentRole role;
            try {
                role = StudentRole.valueOf(roleStr);
            } catch (IllegalArgumentException | NullPointerException e) {
                System.err.println("Warning: Unknown role '" + roleStr + "' for student id=" + studentId + ", name=" + studentName + ", defaulting to REGULAR");
                role = StudentRole.REGULAR;
            }

            Student student = new Student(
                    studentName,
                    studentSurname,
                    studentGroup,
                    studentEmail,
                    studentPassword,
                    studentSalt,
                    role
            );
            student.setIdFromDB(studentId);
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
            Teacher teacher = new Teacher(
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getString("department"),
                    resultSet.getString("degree"),
                    resultSet.getDouble("salary"),
                    resultSet.getString("email"),

                    resultSet.getString("password"),
                    resultSet.getString("salt")
            );
            teacher.setIdFromDB(resultSet.getInt("id"));
            list.add(teacher);
        }
        return list;
    }

    @NotNull
    private static List<Lesson> parseLessonsFromResultSet(@NotNull ResultSet resultSet) throws SQLException {
        List<Lesson> list = new ArrayList<>();
        while (resultSet.next()) {
            DayOfWeek day = DayOfWeek.valueOf(resultSet.getString("dayOfWeek"));
            LocalTime time = resultSet.getTime("time").toLocalTime();
            String subject = resultSet.getString("subject");
            String teacherSurname = resultSet.getString("teacherSurname");

            list.add(new Lesson(day, time, subject, teacherSurname));
        }
        return list;
    }

    // --- DELETION METHODS ---

    /**
     * Deletes a student record by ID.
     *
     * @param id the unique identifier of the student
     * @throws RuntimeException if the database operation fails
     */
    public static void deleteStudentFromDBWithID(int id) {
        if (id <= 0) return;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_DELETE_STUDENT_BY_ID)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            boolean deleted = affectedRows > 0;
            if (deleted) {
                System.out.println("Student with ID " + id + " deleted from database");
            } else {
                System.out.println("No student found with ID " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete student with ID " + id, e);
        }
    }

    /**
     * Deletes a teacher record by ID.
     *
     * @param id the unique identifier of the teacher
     * @throws RuntimeException if the database operation fails
     */
    public static void deleteTeacherFromDBWithID(int id) {
        if (id <= 0) return;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_DELETE_TEACHER_BY_ID)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            boolean deleted = affectedRows > 0;
            if (deleted) {
                System.out.println("Teacher with ID " + id + " deleted from database");
            } else {
                System.out.println("No teacher found with ID " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete teacher with ID " + id, e);
        }
    }

    public static void deleteLessonFromDBWithID(int lessonId) {
        if (lessonId <= 0) return;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_DELETE_LESSON_BY_ID)) {
            stmt.setInt(1, lessonId);
            int affectedRows = stmt.executeUpdate();
            boolean deleted = affectedRows > 0;
            if (deleted) {
                System.out.println("LESSON with ID " + lessonId + " deleted from database");
            } else {
                System.out.println("No SCHEDULE found with ID " + lessonId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete LESSON with ID " + lessonId, e);
        }
    }

    public static void deleteGradeById(int gradeId) {
        if (gradeId <= 0) return;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_DELETE_GRADE_BY_ID)) {
            stmt.setInt(1, gradeId);
            stmt.executeUpdate();
            System.out.println("Grade with ID " + gradeId + " deleted.");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete grade", e);
        }
    }


    /**
     * Deletes a teacher record by email.
     *
     * @param email the unique email of the teacher
     * @throws RuntimeException if the database operation fails
     */
    public static void deleteTeacherFromDBWithEmail(String email) {
        if (email == null || email.isEmpty()) return;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_DELETE_TEACHER_BY_EMAIL)) {
            stmt.setString(1, email.toLowerCase().toLowerCase());
            int affectedRows = stmt.executeUpdate();
            boolean deleted = affectedRows > 0;
            if (deleted) {
                System.out.println("Teacher with email " + email + " deleted from database");
            } else {
                System.out.println("No teacher found with email " + email);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete teacher with email " + email, e);
        }
    }

    /**
     * Deletes a student record by email.
     *
     * @param email the unique email of the student
     * @return true if a row was deleted, false otherwise
     * @throws RuntimeException if the database operation fails
     */
    public static boolean deleteStudentFromDBWithEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_DELETE_STUDENT_BY_EMAIL)) {
            stmt.setString(1, email.toLowerCase().toLowerCase());
            int affectedRows = stmt.executeUpdate();
            boolean deleted = affectedRows > 0;
            if (deleted) {
                System.out.println("Student with email " + email + " deleted from database");
            } else {
                System.out.println("No student found with email " + email);
            }
            return deleted;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete student with email " + email, e);
        }
    }

    // --- SEARCH METHODS ---

    /**
     * Finds a student by their unique email.
     *
     * @return {@link Student} or null if not found
     */
    public static Student getStudentByEmail(String email) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_GET_STUDENT_BY_EMAIL)) {
            stmt.setString(1, email.toLowerCase().toLowerCase());
            try (ResultSet rs = stmt.executeQuery()) {
                List<Student> students = parseStudentsFromResultSet(rs);
                return students.isEmpty() ? null : students.getFirst();
            }
        } catch (SQLException e) {
            System.err.println("Error searching student by email: " + e.getMessage());
            return null;
        }
    }

    /**
     * Finds a teacher by their unique email.
     *
     * @return {@link Teacher} or null if not found
     */
    public static Teacher getTeacherByEmail(String email) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_GET_TEACHER_BY_EMAIL)) {
            stmt.setString(1, email.toLowerCase().toLowerCase());
            try (ResultSet rs = stmt.executeQuery()) {
                List<Teacher> teachers = parseTeachersFromResultSet(rs);
                return teachers.isEmpty() ? null : teachers.getFirst();
            }
        } catch (SQLException e) {
            System.err.println("Error searching teacher by email: " + e.getMessage());
            return null;
        }
    }

    /**
     * Finds a student by their unique database ID.
     *
     * @return {@link Student} or null if not found
     */
    public static Student getStudentById(int id) {
        String query = "SELECT * FROM STUDENTS WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                var students = parseStudentsFromResultSet(rs);
                return students.isEmpty() ? null : students.getFirst();
            }
        } catch (SQLException e) {
            System.err.println("Failed to find student with ID: " + id + " in database: " + e.getMessage());
            return null;
        }
    }

    /**
     * Finds a teacher by their unique database ID.
     *
     * @return {@link Teacher} or null if not found
     */
    public static Teacher getTeacherById(int id) {
        String query = "SELECT * FROM TEACHERS WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                var teachers = parseTeachersFromResultSet(rs);
                return teachers.isEmpty() ? null : teachers.getFirst();
            }
        } catch (SQLException e) {
            System.err.println("Failed to find student with ID: " + id + " in database: " + e.getMessage());
            return null;
        }
    }

    public static List<Lesson> getLessonsByDay(DayOfWeek day) {
        if (day == null) return new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_GET_LESSONS_BY_DAY)) {
            stmt.setString(1, day.name().toUpperCase());
            try (ResultSet rs = stmt.executeQuery()) {
                return parseLessonsFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving SCHEDULE for day: " + day);
        }
        return List.of();
    }

    public static List<Grade> getGradesForStudent(int studentId) {
        if (studentId <= 0) return new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_GET_GRADES_BY_STUDENT)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                return parseGradesFromResultSet(rs, studentId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving grades for student: " + studentId, e);
        }
    }

    @NotNull
    private static List<Grade> parseGradesFromResultSet(@NotNull ResultSet resultSet, int studentId) throws SQLException {
        List<Grade> list = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int score = resultSet.getInt("score");
            list.add(new Grade(studentId, name, score));
        }
        return list;
    }
    // --- UPDATE METHODS (STUDENTS) ---

    /**
     * Updates the name of a student in the DB.
     */
    public static void updateStudentName(int id, String name) {
        FieldValidator.validateId(id);
        FieldValidator.validateAlphabeticString("name", name);
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_NAME_STUDENT_BY_ID)) {
            stmt.setString(1, name);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated student name: " + name);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update student name for id=" + id, e);
        }
    }

    /**
     * Updates the surname of a student in the DB.
     */
    public static void updateStudentSurname(int id, String surname) {
        FieldValidator.validateId(id);
        FieldValidator.validateAlphabeticString("surname", surname);
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_SURNAME_STUDENT_BY_ID)) {
            stmt.setString(1, surname);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated student surname: " + surname);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update student surname for id=" + id, e);
        }
    }

    /**
     * Updates the group of students in the DB.
     */
    public static void updateStudentGroup(int id, String group) {
        FieldValidator.validateId(id);
        FieldValidator.validateGroup(group);
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_GROUP_STUDENT_BY_ID)) {
            stmt.setString(1, group);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated student group: " + group);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update student group for id=" + id, e);
        }
    }

    /**
     * Updates the email of a student in the DB.
     */
    public static void updateStudentEmail(int id, String email) {
        FieldValidator.validateId(id);
        FieldValidator.validateEmail(email);
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_EMAIL_STUDENT_BY_ID)) {
            stmt.setString(1, email.toLowerCase().toLowerCase());
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated student email: " + email);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update student email for id=" + id, e);
        }
    }

    // --- UPDATE METHODS (TEACHERS) ---

    /**
     * Updates the name of a teacher in the DB.
     */
    public static void updateTeacherName(int id, String name) {
        FieldValidator.validateId(id);
        FieldValidator.validateAlphabeticString("name", name);
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_NAME_TEACHER_BY_ID)) {
            stmt.setString(1, name);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated teacher name: " + name);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update teacher name for id=" + id, e);
        }
    }

    /**
     * Updates the surname of a teacher in the DB.
     */
    public static void updateTeacherSurname(int id, String surname) {
        FieldValidator.validateId(id);
        FieldValidator.validateAlphabeticString("surname", surname);
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_SURNAME_TEACHER_BY_ID)) {
            stmt.setString(1, surname);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated teacher surname: " + surname);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update teacher surname for id=" + id, e);
        }
    }

    /**
     * Updates the department of a teacher in the DB.
     */
    public static void updateTeacherDepartment(int id, String department) {
        FieldValidator.validateId(id);
        FieldValidator.validateAlphabeticString("department", department);
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_DEPARTMENT_TEACHER_BY_ID)) {
            stmt.setString(1, department);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated teacher department: " + department);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update teacher department for id=" + id, e);
        }
    }

    /**
     * Updates the degree of a teacher in the DB.
     */
    public static void updateTeacherDegree(int id, String degree) {
        FieldValidator.validateId(id);
        FieldValidator.validateAlphabeticString("degree", degree);
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_DEGREE_TEACHER_BY_ID)) {
            stmt.setString(1, degree);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated teacher degree: " + degree);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update teacher degree for id=" + id, e);
        }
    }

    /**
     * Updates the salary of a teacher in the DB.
     */
    public static void updateTeacherSalary(int id, double salary) {
        FieldValidator.validateId(id);
        FieldValidator.validateSalary(salary);
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_SALARY_TEACHER_BY_ID)) {
            stmt.setDouble(1, salary);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated teacher salary: " + salary);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update teacher salary for id=" + id, e);
        }
    }

    /**
     * Updates the email of a teacher in the DB.
     */
    public static void updateTeacherEmail(int id, String email) {
        FieldValidator.validateId(id);
        FieldValidator.validateEmail(email);
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY_SET_EMAIL_TEACHER_BY_ID)) {
            stmt.setString(1, email.toLowerCase());
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Successfully updated teacher email: " + email);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update teacher email for id=" + id, e);
        }
    }

    /**
     * Updates the start time of a specific lesson.
     */
    public static void updateLessonTime(int lessonId, java.time.LocalTime newTime) {
        if (lessonId <= 0 || newTime == null) return;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_SET_LESSON_TIME_BY_ID)) {
            stmt.setTime(1, java.sql.Time.valueOf(newTime));
            stmt.setInt(2, lessonId);
            stmt.executeUpdate();
            System.out.println("Lesson ID " + lessonId + " time updated to " + newTime);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update lesson time", e);
        }
    }

    /**
     * Updates the teacher assigned to a specific lesson.
     */
    public static void updateLessonTeacher(int lessonId, String teacherSurname) {
        if (lessonId <= 0) return;
        FieldValidator.validateAlphabeticString("Teacher Surname", teacherSurname);
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_SET_LESSON_TEACHER_BY_ID)) {
            stmt.setString(1, teacherSurname.strip());
            stmt.setInt(2, lessonId);
            stmt.executeUpdate();
            System.out.println("Lesson ID " + lessonId + " teacher updated to " + teacherSurname);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update lesson teacher", e);
        }
    }

    /**
     * Updates the score value of an existing grade record.
     */
    public static void updateGradeScore(int gradeId, int newScore) {
        if (gradeId <= 0) return;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_SET_GRADE_SCORE_BY_ID)) {
            stmt.setInt(1, newScore);
            stmt.setInt(2, gradeId);
            stmt.executeUpdate();
            System.out.println("Grade ID " + gradeId + " successfully updated to " + newScore);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update grade score", e);
        }
    }

    // --- CORE SQL EXECUTION HELPERS ---

    /**
     * Executes a generic SQL update with a variable number of parameters.
     *
     * @param sql    the SQL string to execute
     * @param params the objects to bind to the statement
     * @throws RuntimeException if the database operation fails
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
     *
     * @param id   the student ID
     * @param role the new {@link StudentRole}
     */
    public static void updateStudentRole(int id, StudentRole role) {
        executeUpdate(QUERY_PROMOTE_STUDENT_ROLE, role.name(), id);
    }

    /**
     * Resets all 'HEAD_STUDENT' roles to 'REGULAR' for a specific group.
     *
     * @param groupName the name of the group to demote
     */
    public static void demoteAllHeadsInGroup(String groupName) {
        executeUpdate(QUERY_DEMOTE_HEADS_IN_GROUP, groupName);
    }

    /**
     * Atomically assigns a head student by first demoting all existing heads in the same group,
     * then promoting the target student. Uses a transaction to ensure data consistency.
     *
     * @param studentId the ID of the student to promote
     * @param groupName the group name of the student
     * @throws RuntimeException if the transaction fails
     */
    public static void assignHeadStudentTransactional(int studentId, String groupName) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try {
                // Demote existing heads in the group
                try (PreparedStatement demoteStmt = conn.prepareStatement(
                        QUERY_DEMOTE_HEADS_IN_GROUP)) {
                    demoteStmt.setString(1, groupName);
                    demoteStmt.executeUpdate();
                }

                // Promote the target student
                try (PreparedStatement promoteStmt = conn.prepareStatement(
                        QUERY_PROMOTE_STUDENT_ROLE)) {
                    promoteStmt.setString(1, StudentRole.HEAD_STUDENT.name());
                    promoteStmt.setInt(2, studentId);
                    promoteStmt.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    e.addSuppressed(rollbackEx);
                }
                throw new RuntimeException("Failed to assign head student transactionally", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to assign head student transactionally", e);
        }
    }
}