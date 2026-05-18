package ua.knd11.model.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link StudentRole} enum.
 * Tests enum values, display names, and standard enum operations.
 *
 * @see StudentRole
 */
class StudentRoleTest {

    /**
     * Tests that the enum contains exactly the expected roles.
     */
    @Test
    void enumValues_ShouldContainExpectedRoles() {
        assertEquals(2, StudentRole.values().length);
        assertNotNull(StudentRole.valueOf("HEAD_STUDENT"));
        assertNotNull(StudentRole.valueOf("REGULAR"));
    }

    /**
     * Tests that HEAD_STUDENT role has the correct display name.
     */
    @Test
    void headStudent_ShouldHaveCorrectDisplayName() {
        assertEquals("Head Student", StudentRole.HEAD_STUDENT.getDisplayName());
    }

    /**
     * Tests that REGULAR role has the correct display name.
     */
    @Test
    void regularStudent_ShouldHaveCorrectDisplayName() {
        assertEquals("Student", StudentRole.REGULAR.getDisplayName());
    }

    /**
     * Tests that all enum roles have their correct display names.
     *
     * @param roleName            the enum constant name
     * @param expectedDisplayName the expected display name
     */
    @ParameterizedTest
    @CsvSource({
            "HEAD_STUDENT, Head Student",
            "REGULAR, Student"
    })
    void allRoles_ShouldHaveCorrectDisplayNames(String roleName, String expectedDisplayName) {
        StudentRole role = StudentRole.valueOf(roleName);
        assertEquals(expectedDisplayName, role.getDisplayName());
    }

    /**
     * Tests that valueOf returns the correct enum constant for valid names.
     */
    @Test
    void valueOf_WithValidNames_ShouldReturnCorrectEnum() {
        assertEquals(StudentRole.HEAD_STUDENT, StudentRole.valueOf("HEAD_STUDENT"));
        assertEquals(StudentRole.REGULAR, StudentRole.valueOf("REGULAR"));
    }

    /**
     * Tests that values() method returns all enum constants.
     */
    @Test
    void values_ShouldReturnAllEnumConstants() {
        StudentRole[] roles = StudentRole.values();
        assertEquals(2, roles.length);
        assertTrue(java.util.Arrays.asList(roles).contains(StudentRole.HEAD_STUDENT));
        assertTrue(java.util.Arrays.asList(roles).contains(StudentRole.REGULAR));
    }
}
