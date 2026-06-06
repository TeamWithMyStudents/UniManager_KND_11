package ua.knd11.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knd11.service.JournalService;
import ua.knd11.service.ScheduleService;
import ua.knd11.service.StudentService;
import ua.knd11.util.SQLActions;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link StudentController} class.
 * Tests are isolated from the database and verify the correctness of parsing terminal input
 * and the interaction between the controller and the service layer.
 */
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private JournalService journalService;

    @Mock
    private ScheduleService scheduleService;

    private StudentController studentController;

    /**
     * Executed before each test.
     * Initializes the {@link StudentController} and injects the {@link StudentService} mock object
     * into the private field of the controller using the Reflection API.
     *
     * @throws NoSuchFieldException   if the "studentService" field is not found in the controller class
     * @throws IllegalAccessException if there is no access to modify the value of the private field
     */
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        studentController = new StudentController(journalService, scheduleService);

        Field field = StudentController.class.getDeclaredField("studentService");
        field.setAccessible(true);
        field.set(studentController, studentService);
    }

    /**
     * Verifies that when a valid string containing student data is passed,
     * the controller successfully parses it and calls the student creation method in the service.
     */
    @Test
    void addStudentFromTerminal_WithValidInput_ShouldCallService() {
        String validInput = "John Doe KND-11 john.doe@example.com Password123!";

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(validInput));

        verify(studentService).createStudentWithParts(any(String[].class));
    }

    /**
     * Verifies that the controller rejects the input and does not call the service
     * if an insufficient number of fields is provided (less than the expected 5).
     */
    @Test
    void addStudentFromTerminal_WithTooFewFields_ShouldPrintError() {
        String invalidInput = "John Doe KND-11";

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(invalidInput));

        verify(studentService, never()).createStudentWithParts(any(String[].class));
    }

    /**
     * Verifies that the controller rejects the input and does not call the service
     * if an excessive number of fields is provided (more than the expected 5).
     */
    @Test
    void addStudentFromTerminal_WithTooManyFields_ShouldPrintError() {
        String invalidInput = "John Doe KND-11 john.doe@example.com Password123! extra";

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(invalidInput));

        verify(studentService, never()).createStudentWithParts(any(String[].class));
    }

    /**
     * Verifies that the controller catches and gracefully handles an {@link IllegalArgumentException}
     * thrown by the service due to an invalid student name format (e.g., contains numbers).
     */
    @Test
    void addStudentFromTerminal_WithInvalidName_ShouldHandleExceptionGracefully() {
        String invalidInput = "John123 Doe KND-11 john@example.com Password123!";

        when(studentService.createStudentWithParts(any(String[].class)))
                .thenThrow(new IllegalArgumentException("Invalid name format"));

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(invalidInput));
        verify(studentService).createStudentWithParts(any(String[].class));
    }

    /**
     * Verifies that the controller catches and gracefully handles an {@link IllegalArgumentException}
     * thrown by the service due to an invalid email format.
     */
    @Test
    void addStudentFromTerminal_WithInvalidEmail_ShouldHandleExceptionGracefully() {
        String invalidInput = "John Doe KND-11 invalid-email Password123!";

        when(studentService.createStudentWithParts(any(String[].class)))
                .thenThrow(new IllegalArgumentException("Invalid email format"));

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(invalidInput));
        verify(studentService).createStudentWithParts(any(String[].class));
    }

    /**
     * Verifies that the controller catches and gracefully handles an {@link IllegalArgumentException}
     * thrown by the service when the password does not meet security requirements (e.g., too short).
     */
    @Test
    void addStudentFromTerminal_WithShortPassword_ShouldHandleExceptionGracefully() {
        String invalidInput = "John Doe KND-11 john@example.com short";

        when(studentService.createStudentWithParts(any(String[].class)))
                .thenThrow(new IllegalArgumentException("Password too short"));

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(invalidInput));
        verify(studentService).createStudentWithParts(any(String[].class));
    }

    /**
     * Verifies the invocation of the static method to delete a student from the DB
     * by a valid (positive) identifier using {@link MockedStatic}.
     */
    @Test
    void deleteStudentFromDb_ShouldCallPositiveId() {
        try (MockedStatic<SQLActions> mockedSql = mockStatic(SQLActions.class)) {
            assertDoesNotThrow(() -> SQLActions.deleteStudentFromDBWithID(1));
            mockedSql.verify(() -> SQLActions.deleteStudentFromDBWithID(1));
        }
    }

    /**
     * Verifies the invocation of the static method to delete a student from the DB
     * by an invalid (negative) identifier, ensuring that the method does not throw exceptions.
     */
    @Test
    void deleteStudentFromDb_ShouldCallNegativeId() {
        try (MockedStatic<SQLActions> mockedSql = mockStatic(SQLActions.class)) {
            assertDoesNotThrow(() -> SQLActions.deleteStudentFromDBWithID(-1));
            mockedSql.verify(() -> SQLActions.deleteStudentFromDBWithID(-1));
        }
    }

    /**
     * Verifies that the request to retrieve all students
     * is successfully delegated to the corresponding method in {@link StudentService}.
     */
    @Test
    void getAll_ShouldCallService() {
        assertDoesNotThrow(() -> studentService.getAllStudents());
        verify(studentService).getAllStudents();
    }

    /**
     * Verifies that the request to assign a group head student by ID
     * is successfully delegated to the corresponding method in {@link StudentService}.
     */
    @Test
    void assignHeadStudent_ShouldCallService() {
        assertDoesNotThrow(() -> studentService.assignHeadStudent(1));
        verify(studentService).assignHeadStudent(1);
    }

    /**
     * Verifies that an input string with extra whitespace between arguments
     * is correctly normalized (split) by the controller before calling the service.
     */
    @Test
    void addStudentFromTerminal_WithExtraWhitespace_ShouldNormalizeAndCallService() {
        String inputWithExtraSpaces = "  John   Doe   KND-11   john.doe@example.com   Password123!  ";

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(inputWithExtraSpaces));
        verify(studentService).createStudentWithParts(any(String[].class));
    }

    /**
     * Verifies internationalization (Cyrillic) support: the controller should successfully
     * accept and pass names written in Ukrainian to the service.
     */
    @Test
    void addStudentFromTerminal_WithValidUkrainianName_ShouldCallService() {
        String validInput = "Олег Петренко KND-11 oleg@example.com Password123!";

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(validInput));
        verify(studentService).createStudentWithParts(any(String[].class));
    }
}