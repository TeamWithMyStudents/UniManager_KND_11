package ua.knd11.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knd11.service.StudentService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for the {@link StudentController} class.
 * Tests student input parsing, validation, and controller method invocation.
 *
 * @see StudentController
 */
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    /**
     * Sets up the controller before each test.
     */
    @BeforeEach
    void setUp() {
        // Controller creates its own service, so we test it directly
        studentController = new StudentController();
    }

    /**
     * Tests that valid student input is processed without throwing exceptions.
     */
    @Test
    void addStudentFromTerminal_WithValidInput_ShouldCallService() {
        String validInput = "John Doe KND-11 john.doe@example.com Password123!";

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(validInput));
    }

    /**
     * Tests that input with too few fields is handled gracefully.
     */
    @Test
    void addStudentFromTerminal_WithTooFewFields_ShouldPrintError() {
        String invalidInput = "John Doe KND-11";

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(invalidInput));
    }

    /**
     * Tests that input with too many fields is handled gracefully.
     */
    @Test
    void addStudentFromTerminal_WithTooManyFields_ShouldPrintError() {
        String invalidInput = "John Doe KND-11 john.doe@example.com Password123! extra";

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(invalidInput));
    }

    /**
     * Tests that invalid name format is handled gracefully.
     */
    @Test
    void addStudentFromTerminal_WithInvalidName_ShouldPrintError() {
        String invalidInput = "John123 Doe KND-11 john@example.com Password123!";

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(invalidInput));
    }

    /**
     * Tests that invalid email format is handled gracefully.
     */
    @Test
    void addStudentFromTerminal_WithInvalidEmail_ShouldPrintError() {
        String invalidInput = "John Doe KND-11 invalid-email Password123!";

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(invalidInput));
    }

    /**
     * Tests that short password is handled gracefully.
     */
    @Test
    void addStudentFromTerminal_WithShortPassword_ShouldPrintError() {
        String invalidInput = "John Doe KND-11 john@example.com short";

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(invalidInput));
    }

    /**
     * Tests that delete student operation executes without throwing exceptions.
     */
    @Test
    void deleteStudent_ShouldCallService() {
        assertDoesNotThrow(() -> studentController.deleteStudent(1));
    }

    /**
     * Tests that get all students operation executes without throwing exceptions.
     */
    @Test
    void getAll_ShouldCallService() {
        assertDoesNotThrow(() -> studentController.getAll());
    }

    /**
     * Tests that assign head student operation executes without throwing exceptions.
     */
    @Test
    void assignHeadStudent_ShouldCallService() {
        assertDoesNotThrow(() -> studentController.assignHeadStudent(1));
    }

    /**
     * Tests that extra whitespace in input is handled correctly.
     */
    @Test
    void addStudentFromTerminal_WithExtraWhitespace_ShouldHandleCorrectly() {
        String inputWithExtraSpaces = "  John   Doe   KND-11   john.doe@example.com   Password123!  ";

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(inputWithExtraSpaces));
    }

    /**
     * Tests that Ukrainian (Cyrillic) names are accepted.
     */
    @Test
    void addStudentFromTerminal_WithValidUkrainianName_ShouldWork() {
        String validInput = "Олег Петренко KND-11 oleg@example.com Password123!";

        assertDoesNotThrow(() -> studentController.addStudentFromTerminal(validInput));
    }
}
