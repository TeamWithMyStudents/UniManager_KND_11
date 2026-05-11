package ua.knd11.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Teacher} model class.
 * Tests teacher creation, department/degree management, salary validation,
 * and database-specific constructor scenarios.
 *
 * @see Teacher
 */
class TeacherTest {

    private Teacher teacher;

    /**
     * Sets up a valid Teacher instance before each test.
     */
    @BeforeEach
    void setUp() {
        teacher = new Teacher("Jane", "Smith", "Computer Science", "PhD", 50000.0,
                "jane.smith@university.edu", "Password123!");
    }

    /**
     * Tests that a teacher is created correctly with valid data.
     */
    @Test
    void constructor_WithValidData_ShouldCreateTeacher() {
        assertNotNull(teacher);
        assertEquals("Jane", teacher.getName());
        assertEquals("Smith", teacher.getSurname());
        assertEquals("Computer Science", teacher.getDepartment());
        assertEquals("PhD", teacher.getDegree());
        assertEquals(50000.0, teacher.getSalary());
        assertEquals("jane.smith@university.edu", teacher.getEmail());
    }

    /**
     * Tests the database-specific constructor that accepts pre-hashed password and salt.
     */
    @Test
    void constructor_ForDatabase_ShouldCreateTeacher() {
        Teacher dbTeacher = new Teacher("John", "Doe", "Mathematics", "Master", 45000.0,
                "john.doe@university.edu", "hashed_password", "salt_value");

        assertNotNull(dbTeacher);
        assertEquals("hashed_password", dbTeacher.getPassword());
        assertEquals("salt_value", dbTeacher.getSalt());
    }

    /**
     * Tests that invalid department names (containing digits) are rejected.
     */
    @Test
    void constructor_WithInvalidDepartment_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Teacher("Jane", "Smith", "CS123", "PhD", 50000.0, "jane@university.edu", "Password123!"));
    }

    /**
     * Tests that invalid degree names (containing digits) are rejected.
     */
    @Test
    void constructor_WithInvalidDegree_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Teacher("Jane", "Smith", "Computer Science", "PhD123", 50000.0, "jane@university.edu", "Password123!"));
    }

    /**
     * Tests that invalid salary values (zero, negative, NaN, infinite) are rejected.
     *
     * @param invalidSalary an invalid salary value to test
     */
    @ParameterizedTest
    @ValueSource(doubles = {0.0, -100.0, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY})
    void constructor_WithInvalidSalary_ShouldThrowException(double invalidSalary) {
        assertThrows(IllegalArgumentException.class, () ->
                new Teacher("Jane", "Smith", "Computer Science", "PhD", invalidSalary,
                        "jane@university.edu", "Password123!"));
    }

    /**
     * Tests that department can be updated to a valid value.
     */
    @Test
    void setDepartment_WithValidDepartment_ShouldUpdate() {
        teacher.setDepartment("Mathematics");
        assertEquals("Mathematics", teacher.getDepartment());
    }

    /**
     * Tests that invalid department values are rejected when updating.
     */
    @Test
    void setDepartment_WithInvalidDepartment_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> teacher.setDepartment("Math123"));
    }

    /**
     * Tests that degree can be updated to a valid value.
     */
    @Test
    void setDegree_WithValidDegree_ShouldUpdate() {
        teacher.setDegree("Professor");
        assertEquals("Professor", teacher.getDegree());
    }

    /**
     * Tests that invalid degree values are rejected when updating.
     */
    @Test
    void setDegree_WithInvalidDegree_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> teacher.setDegree("Prof123"));
    }

    /**
     * Tests that salary can be updated to a valid positive value.
     */
    @Test
    void setSalary_WithValidSalary_ShouldUpdate() {
        teacher.setSalary(75000.0);
        assertEquals(75000.0, teacher.getSalary());
    }

    /**
     * Tests that invalid salary values are rejected when updating.
     *
     * @param invalidSalary an invalid salary value to test
     */
    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0, Double.NaN, Double.POSITIVE_INFINITY})
    void setSalary_WithInvalidSalary_ShouldThrowException(double invalidSalary) {
        assertThrows(IllegalArgumentException.class, () -> teacher.setSalary(invalidSalary));
    }

    /**
     * Tests that toString contains all relevant teacher information.
     */
    @Test
    void toString_ShouldContainAllRelevantInfo() {
        teacher.setIdFromDB(1);
        String result = teacher.toString();

        assertTrue(result.contains("Id: 1"));
        assertTrue(result.contains("Name: Jane"));
        assertTrue(result.contains("Surname: Smith"));
        assertTrue(result.contains("Department: Computer Science"));
        assertTrue(result.contains("Degree: PhD"));
        assertTrue(result.contains("Salary: 50000.0"));
    }

    /**
     * Tests that inherited methods from User class work correctly.
     */
    @Test
    void inheritedMethods_ShouldWork() {
        teacher.setName("Janet");
        assertEquals("Janet", teacher.getName());

        teacher.setSurname("Doe");
        assertEquals("Doe", teacher.getSurname());

        teacher.setEmail("janet.doe@university.edu");
        assertEquals("janet.doe@university.edu", teacher.getEmail());
    }

    /**
     * Tests that all getter methods return the correct values.
     */
    @Test
    void getters_ShouldReturnCorrectValues() {
        assertEquals("Computer Science", teacher.getDepartment());
        assertEquals("PhD", teacher.getDegree());
        assertEquals(50000.0, teacher.getSalary());
    }
}
