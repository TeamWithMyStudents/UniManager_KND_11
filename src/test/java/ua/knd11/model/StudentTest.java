package ua.knd11.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.knd11.model.enums.StudentRole;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Student} model class.
 * Tests student creation, group management, role assignment, and validation logic.
 *
 * <p>Covers both standard constructor usage and database-specific constructor scenarios.</p>
 *
 * @see Student
 * @see StudentRole
 */
class StudentTest {

    private Student student;

    /**
     * Sets up a valid Student instance before each test.
     */
    @BeforeEach
    void setUp() {
        student = new Student("John", "Doe", "KND-11", "john.doe@example.com", "Password123!");
    }

    /**
     * Tests that a student is created correctly with valid data and default REGULAR role.
     */
    @Test
    void constructor_WithValidData_ShouldCreateStudent() {
        assertNotNull(student);
        assertEquals("John", student.getName());
        assertEquals("Doe", student.getSurname());
        assertEquals("KND-11", student.getGroup());
        assertEquals("john.doe@example.com", student.getEmail());
        assertEquals(StudentRole.REGULAR, student.getRole());
    }

    /**
     * Tests that group identifiers are normalized to uppercase on creation.
     */
    @Test
    void constructor_WithLowerCaseGroup_ShouldNormalizeToUpperCase() {
        Student lowerCaseStudent = new Student("Jane", "Smith", "knd-12", "jane@example.com", "Password123!");
        assertEquals("KND-12", lowerCaseStudent.getGroup());
    }

    /**
     * Tests the database-specific constructor that accepts pre-hashed password and role.
     */
    @Test
    void constructor_ForDatabase_ShouldCreateStudentWithRole() {
        Student dbStudent = new Student("Jane", "Smith", "KND-11", "jane@example.com",
                "hashed_password", "salt", StudentRole.HEAD_STUDENT);

        assertNotNull(dbStudent);
        assertEquals("hashed_password", dbStudent.getPassword());
        assertEquals("salt", dbStudent.getSalt());
        assertEquals(StudentRole.HEAD_STUDENT, dbStudent.getRole());
    }

    /**
     * Tests that empty group identifiers are rejected during construction.
     */
    @Test
    void constructor_WithInvalidGroup_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Student("John", "Doe", "", "john@example.com", "Password123!"));
    }

    /**
     * Tests that group can be updated to a valid value.
     */
    @Test
    void setGroup_WithValidGroup_ShouldUpdateGroup() {
        student.setGroup("KND-22");
        assertEquals("KND-22", student.getGroup());
    }

    /**
     * Tests that group identifiers are normalized to uppercase when updated.
     */
    @Test
    void setGroup_WithLowerCase_ShouldNormalizeToUpperCase() {
        student.setGroup("abc-123");
        assertEquals("ABC-123", student.getGroup());
    }

    /**
     * Tests that empty group values are rejected when updating.
     */
    @Test
    void setGroup_WithInvalidGroup_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> student.setGroup(""));
    }

    /**
     * Tests that student role can be updated.
     */
    @Test
    void setRole_ShouldUpdateRole() {
        student.setRole(StudentRole.HEAD_STUDENT);
        assertEquals(StudentRole.HEAD_STUDENT, student.getRole());
    }

    /**
     * Tests that toString contains all relevant student information.
     */
    @Test
    void toString_ShouldContainAllRelevantInfo() {
        student.setIdFromDB(1);
        String result = student.toString();

        assertTrue(result.contains("Id: 1"));
        assertTrue(result.contains("Name: John"));
        assertTrue(result.contains("Surname: Doe"));
        assertTrue(result.contains("Group: KND-11"));
        assertTrue(result.contains("Email: john.doe@example.com"));
        assertTrue(result.contains("Role: REGULAR"));
    }

    /**
     * Tests that inherited methods from User class work correctly.
     */
    @Test
    void inheritedMethods_ShouldWork() {
        assertEquals("John", student.getName());
        assertEquals("Doe", student.getSurname());

        student.setIdFromDB(42);
        assertEquals(42, student.getId());
    }

    /**
     * Tests the group getter returns the correct value.
     */
    @Test
    void getGroup_ShouldReturnGroup() {
        assertEquals("KND-11", student.getGroup());
    }

    /**
     * Tests that default role for new students is REGULAR.
     */
    @Test
    void getRole_DefaultShouldBeRegular() {
        assertEquals(StudentRole.REGULAR, student.getRole());
    }
}
