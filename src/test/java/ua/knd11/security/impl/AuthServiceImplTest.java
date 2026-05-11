package ua.knd11.security.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knd11.model.User;
import ua.knd11.security.UserSession;
import ua.knd11.util.SQLActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

/**
 * Unit tests for the {@link AuthServiceImpl} class.
 * Tests authentication logic, input validation, and session management.
 * Uses Mockito for mocking static SQLActions class.
 *
 * @see AuthServiceImpl
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    private AuthServiceImpl authService;

    /**
     * Sets up the auth service and ensures clean session state before each test.
     */
    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl();
        if (UserSession.isAuthenticated()) {
            UserSession.logout();
        }
    }

    /**
     * Cleans up session state after each test.
     */
    @AfterEach
    void tearDown() {
        if (UserSession.isAuthenticated()) {
            UserSession.logout();
        }
    }

    /**
     * Tests that login with invalid email format does not authenticate.
     */
    @Test
    void login_WithInvalidEmail_ShouldNotAuthenticate() {
        authService.login("invalid-email", "Password123!");

        assertFalse(UserSession.isAuthenticated());
    }

    /**
     * Tests that login with invalid password does not authenticate.
     */
    @Test
    void login_WithInvalidPassword_ShouldNotAuthenticate() {
        authService.login("user@example.com", "short");

        assertFalse(UserSession.isAuthenticated());
    }

    /**
     * Tests that login when already authenticated keeps the current session.
     */
    @Test
    void login_WhenAlreadyAuthenticated_ShouldNotAuthenticateAgain() {
        User user = new User("John", "Doe", "john@example.com", "Password123!") {
        };
        UserSession.login(user);

        authService.login("jane@example.com", "Password123!");

        assertEquals(user, UserSession.getCurrentUser());
    }

    /**
     * Tests that login validates email format and attempts database lookup.
     * Uses mocked SQLActions to avoid actual database calls.
     */
    @Test
    void findUserByEmail_WithValidEmail_ShouldValidateEmail() {
        assertDoesNotThrow(() -> {
            try (MockedStatic<SQLActions> sqlActions = mockStatic(SQLActions.class)) {
                sqlActions.when(() -> SQLActions.getTeacherByEmail(anyString())).thenReturn(null);
                sqlActions.when(() -> SQLActions.getStudentByEmail(anyString())).thenReturn(null);

                authService.login("nonexistent@example.com", "Password123!");
            }
        });
    }
}
