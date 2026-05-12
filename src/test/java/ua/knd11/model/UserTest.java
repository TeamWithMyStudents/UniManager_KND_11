package ua.knd11.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the abstract {@link User} base class.
 * Tests user creation, validation, ID management, and basic property access.
 *
 * <p>Uses anonymous inner classes to instantiate the abstract User class.</p>
 *
 * @see User
 */
class UserTest {

    private User user;

    /**
     * Sets up a valid User instance before each test using an anonymous subclass.
     */
    @BeforeEach
    void setUp() {
        user = new User("John", "Doe", "john.doe@example.com", "Password123!") {
        };
    }

    /**
     * Tests that a user is created correctly with valid data and hashed password.
     */
    @Test
    void constructor_WithValidData_ShouldCreateUser() {
        assertNotNull(user);
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("john.doe@example.com", user.getEmail());
        assertNotNull(user.getPassword());
        assertNotNull(user.getSalt());
    }

    /**
     * Tests the database-specific constructor that accepts pre-hashed password and salt.
     */
    @Test
    void constructor_WithHashedPassword_ShouldCreateUser() {
        User dbUser = new User("Jane", "Smith", "jane.smith@example.com", "hashed_password", "salt_value") {
        };
        assertNotNull(dbUser);
        assertEquals("hashed_password", dbUser.getPassword());
        assertEquals("salt_value", dbUser.getSalt());
    }

    /**
     * Tests that invalid names (containing digits) are rejected during construction.
     */
    @Test
    void constructor_WithInvalidName_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new User("John123", "Doe", "john@example.com", "Password123!") {
                });
    }

    /**
     * Tests that invalid surnames (containing digits) are rejected during construction.
     */
    @Test
    void constructor_WithInvalidSurname_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new User("John", "Doe123", "john@example.com", "Password123!") {
                });
    }

    /**
     * Tests that invalid email addresses are rejected during construction.
     */
    @Test
    void constructor_WithInvalidEmail_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new User("John", "Doe", "invalid-email", "Password123!") {
                });
    }

    /**
     * Tests that passwords shorter than 8 characters are rejected.
     */
    @Test
    void constructor_WithShortPassword_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new User("John", "Doe", "john@example.com", "short") {
                });
    }

    /**
     * Tests that ID from database can be set correctly.
     */
    @Test
    void setIdFromDB_ShouldSetId() {
        user.setIdFromDB(42);
        assertEquals(42, user.getId());
    }

    /**
     * Tests that name can be updated to a valid value.
     */
    @Test
    void setName_WithValidName_ShouldUpdateName() {
        user.setName("Jane");
        assertEquals("Jane", user.getName());
    }

    /**
     * Tests that various valid name formats can be set.
     *
     * @param validName a valid name string to test
     */
    @ParameterizedTest
    @ValueSource(strings = {"Jane", "Mary", "Anna-Marie", "O'Connor"})
    void setName_WithVariousValidNames_ShouldUpdateName(String validName) {
        user.setName(validName);
        assertEquals(validName, user.getName());
    }

    /**
     * Tests that invalid names are rejected when updating.
     */
    @Test
    void setName_WithInvalidName_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> user.setName("Jane123"));
    }

    /**
     * Tests that surname can be updated to a valid value.
     */
    @Test
    void setSurname_WithValidSurname_ShouldUpdateSurname() {
        user.setSurname("Smith");
        assertEquals("Smith", user.getSurname());
    }

    /**
     * Tests that invalid surnames are rejected when updating.
     */
    @Test
    void setSurname_WithInvalidSurname_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> user.setSurname("Smith123"));
    }

    /**
     * Tests that email can be updated to a valid value.
     */
    @Test
    void setEmail_WithValidEmail_ShouldUpdateEmail() {
        user.setEmail("jane.smith@example.com");
        assertEquals("jane.smith@example.com", user.getEmail());
    }

    /**
     * Tests that various valid email formats can be set.
     *
     * @param validEmail a valid email string to test
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "user@example.com",
            "first.last@company.org",
            "user+tag@example.co.uk",
            "123@example.com"
    })
    void setEmail_WithVariousValidEmails_ShouldUpdateEmail(String validEmail) {
        User testUser = new User("Test", "User", "initial@example.com", "Password123!") {
        };
        testUser.setEmail(validEmail);
        assertEquals(validEmail, testUser.getEmail());
    }

    /**
     * Tests that invalid emails are rejected when updating.
     */
    @Test
    void setEmail_WithInvalidEmail_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("invalid-email"));
    }

    /**
     * Tests that getPassword returns the hashed password, not the plaintext.
     */
    @Test
    void getPassword_ShouldReturnHashedPassword() {
        assertNotNull(user.getPassword());
        assertNotEquals("Password123!", user.getPassword());
    }

    /**
     * Tests that salt is generated and accessible.
     */
    @Test
    void getSalt_ShouldReturnSalt() {
        assertNotNull(user.getSalt());
    }

    /**
     * Tests that toString contains relevant user info but not the password.
     */
    @Test
    void toString_ShouldContainRelevantInfo() {
        user.setIdFromDB(1);
        String result = user.toString();

        assertTrue(result.contains("Id: 1"));
        assertTrue(result.contains("Name: John"));
        assertTrue(result.contains("Surname: Doe"));
        assertTrue(result.contains("Email: john.doe@example.com"));
        assertFalse(result.contains("password"), "toString should not contain password");
    }

    /**
     * Tests that all getter methods return the correct values.
     */
    @Test
    void getters_ShouldReturnCorrectValues() {
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals(0, user.getId());
    }
}