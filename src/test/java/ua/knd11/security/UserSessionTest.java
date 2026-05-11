package ua.knd11.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.knd11.model.User;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link UserSession} class.
 * Tests authentication state management, login/logout functionality,
 * super user access, and session lifecycle.
 *
 * <p>Ensures clean state between tests by logging out before and after each test.</p>
 *
 * @see UserSession
 */
class UserSessionTest {

    /**
     * Ensures no user is authenticated before each test.
     */
    @BeforeEach
    void setUp() {
        if (UserSession.isAuthenticated()) {
            UserSession.logout();
        }
    }

    /**
     * Cleans up by logging out after each test.
     */
    @AfterEach
    void tearDown() {
        if (UserSession.isAuthenticated()) {
            UserSession.logout();
        }
    }

    /**
     * Tests that the super user is properly initialized and accessible.
     */
    @Test
    void getSuperUser_ShouldReturnNonNullSuperUser() {
        User superUser = UserSession.getSuperUser();
        assertNotNull(superUser);
        assertEquals("admin", superUser.getName());
        assertEquals("admin", superUser.getSurname());
    }

    /**
     * Tests that login with a valid user authenticates successfully.
     */
    @Test
    void login_WithValidUser_ShouldAuthenticate() {
        User user = new User("John", "Doe", "john@example.com", "Password123!") {
        };

        UserSession.login(user);

        assertTrue(UserSession.isAuthenticated());
        assertEquals(user, UserSession.getCurrentUser());
    }

    /**
     * Tests that login with null user throws NullPointerException.
     */
    @Test
    void login_WithNullUser_ShouldThrowException() {
        assertThrows(NullPointerException.class, () -> UserSession.login(null));
    }

    /**
     * Tests that logout clears the current session.
     */
    @Test
    void logout_WhenAuthenticated_ShouldLogoutSuccessfully() {
        User user = new User("John", "Doe", "john@example.com", "Password123!") {
        };
        UserSession.login(user);

        UserSession.logout();

        assertFalse(UserSession.isAuthenticated());
        assertNull(UserSession.getCurrentUser());
    }

    /**
     * Tests that logout when not authenticated throws exception.
     */
    @Test
    void logout_WhenNotAuthenticated_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> UserSession.logout());
    }

    /**
     * Tests that isAuthenticated returns false when no user is logged in.
     */
    @Test
    void isAuthenticated_WhenNotLoggedIn_ShouldReturnFalse() {
        assertFalse(UserSession.isAuthenticated());
    }

    /**
     * Tests that isAuthenticated returns true after successful login.
     */
    @Test
    void isAuthenticated_AfterLogin_ShouldReturnTrue() {
        User user = new User("John", "Doe", "john@example.com", "Password123!") {
        };
        UserSession.login(user);

        assertTrue(UserSession.isAuthenticated());
    }

    /**
     * Tests that getCurrentUser returns null when not authenticated.
     */
    @Test
    void getCurrentUser_WhenNotAuthenticated_ShouldReturnNull() {
        assertNull(UserSession.getCurrentUser());
    }

    /**
     * Tests that getCurrentUser returns the authenticated user.
     */
    @Test
    void getCurrentUser_WhenAuthenticated_ShouldReturnCurrentUser() {
        User user = new User("John", "Doe", "john@example.com", "Password123!") {
        };
        UserSession.login(user);

        User currentUser = UserSession.getCurrentUser();
        assertNotNull(currentUser);
        assertEquals(user.getName(), currentUser.getName());
        assertEquals(user.getEmail(), currentUser.getEmail());
    }

    /**
     * Tests that checkAccess returns false when not authenticated.
     */
    @Test
    void checkAccess_WhenNotAuthenticated_ShouldReturnFalse() {
        assertFalse(UserSession.checkAccess());
    }

    /**
     * Tests that checkAccess returns true when authenticated as super user.
     */
    @Test
    void checkAccess_WhenAuthenticatedAsSuperUser_ShouldReturnTrue() {
        UserSession.login(UserSession.getSuperUser());

        assertTrue(UserSession.checkAccess());
    }

    /**
     * Tests that checkAccess returns false for regular users (not super user).
     */
    @Test
    void checkAccess_WhenAuthenticatedAsRegularUser_ShouldReturnFalse() {
        User user = new User("John", "Doe", "john@example.com", "Password123!") {
        };
        UserSession.login(user);

        assertFalse(UserSession.checkAccess());
    }

    /**
     * Tests that subsequent login overwrites the previous session.
     */
    @Test
    void login_OverwritesPreviousSession() {
        User user1 = new User("John", "Doe", "john@example.com", "Password123!") {
        };
        User user2 = new User("Jane", "Smith", "jane@example.com", "Password123!") {
        };

        UserSession.login(user1);
        UserSession.login(user2);

        assertEquals(user2, UserSession.getCurrentUser());
    }

    /**
     * Tests multiple complete login/logout cycles.
     */
    @Test
    void multipleLoginLogoutCycles_ShouldWorkCorrectly() {
        User user = new User("John", "Doe", "john@example.com", "Password123!") {
        };

        UserSession.login(user);
        assertTrue(UserSession.isAuthenticated());

        UserSession.logout();
        assertFalse(UserSession.isAuthenticated());

        UserSession.login(user);
        assertTrue(UserSession.isAuthenticated());

        UserSession.logout();
        assertFalse(UserSession.isAuthenticated());
    }
}
