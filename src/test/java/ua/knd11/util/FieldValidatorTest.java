package ua.knd11.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link FieldValidator} utility class.
 * Tests validation logic for various field types including names, emails, passwords,
 * groups, salaries, IDs, and scores. Also tests password hashing and verification functionality.
 *
 * <p>This test class uses parameterized tests extensively to verify both valid
 * and invalid inputs across multiple test scenarios.</p>
 *
 * @see FieldValidator
 */
class FieldValidatorTest {

    /**
     * Tests that valid alphabetic names (including spaces, apostrophes, and hyphens) pass validation.
     * Supports both English and Ukrainian (Cyrillic) characters.
     *
     * @param validName a valid name string to test
     */
    @ParameterizedTest
    @ValueSource(strings = {"John", "Jane", "O'Connor", "Anna-Marie", "Mary", "Van der Berg"})
    void validateAlphabeticString_WithValidNames_ShouldPass(String validName) {
        assertDoesNotThrow(() -> FieldValidator.validateAlphabeticString("Name", validName));
    }

    /**
     * Tests that names with invalid characters (digits, special symbols) throw exceptions.
     *
     * @param invalidName an invalid name string containing non-alphabetic characters
     */
    @ParameterizedTest
    @ValueSource(strings = {"John123", "Jane@Doe", "Mary#Jane", "12345"})
    void validateAlphabeticString_WithInvalidNames_ShouldThrowException(String invalidName) {
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validateAlphabeticString("Name", invalidName));
    }

    /**
     * Tests that null and empty/blank strings are rejected for alphabetic fields.
     *
     * @param invalidValue null or empty string to test
     */
    @ParameterizedTest
    @NullAndEmptySource
    void validateAlphabeticString_WithNullOrEmpty_ShouldThrowException(String invalidValue) {
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validateAlphabeticString("Name", invalidValue));
    }

    /**
     * Tests that strings exceeding the maximum length of 100 characters are rejected.
     */
    @Test
    void validateAlphabeticString_WithTooLongString_ShouldThrowException() {
        String tooLong = "a".repeat(101);
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validateAlphabeticString("Name", tooLong));
    }

    /**
     * Tests that strings at the maximum allowed length of 100 characters pass validation.
     */
    @Test
    void validateAlphabeticString_WithMaxLengthString_ShouldPass() {
        String maxLength = "a".repeat(100);
        assertDoesNotThrow(() -> FieldValidator.validateAlphabeticString("Name", maxLength));
    }

    /**
     * Tests that valid group identifiers pass validation.
     * Groups may contain letters (English/Ukrainian), digits, underscores, and hyphens.
     *
     * @param validGroup a valid group string to test
     */
    @ParameterizedTest
    @ValueSource(strings = {"KND-11", "CS-101", "GROUP_A", "Class123", "ABC-XYZ"})
    void validateGroup_WithValidGroups_ShouldPass(String validGroup) {
        assertDoesNotThrow(() -> FieldValidator.validateGroup(validGroup));
    }

    /**
     * Tests that group identifiers with invalid characters throw exceptions.
     *
     * @param invalidGroup an invalid group string containing unsupported characters
     */
    @ParameterizedTest
    @ValueSource(strings = {"Group@Name", "Group#123", ""})
    void validateGroup_WithInvalidGroups_ShouldThrowException(String invalidGroup) {
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validateGroup(invalidGroup));
    }

    /**
     * Tests that null and empty/blank strings are rejected for group fields.
     *
     * @param invalidValue null or empty string to test
     */
    @ParameterizedTest
    @NullAndEmptySource
    void validateGroup_WithNullOrEmpty_ShouldThrowException(String invalidValue) {
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validateGroup(invalidValue));
    }

    /**
     * Tests that valid email addresses pass validation.
     * Supports various formats including dots, plus signs, and hyphens in local part.
     *
     * @param validEmail a valid email string to test
     */
    @ParameterizedTest
    @CsvSource({
            "user@example.com",
            "first.last@company.org",
            "user+tag@example.co.uk",
            "123@example.com",
            "user_name@example.com",
            "user-name@example.com"
    })
    void validateEmail_WithValidEmails_ShouldPass(String validEmail) {
        assertDoesNotThrow(() -> FieldValidator.validateEmail(validEmail));
    }

    /**
     * Tests that malformed email addresses throw exceptions.
     * Includes missing @ symbol, empty local part, empty domain, and consecutive dots.
     *
     * @param invalidEmail an invalid email string to test
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "invalid-email",
            "@example.com",
            "user@",
            "user@.com",
            "user..name@example.com"
    })
    void validateEmail_WithInvalidEmails_ShouldThrowException(String invalidEmail) {
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validateEmail(invalidEmail));
    }

    /**
     * Tests that null and empty/blank strings are rejected for email fields.
     *
     * @param invalidValue null or empty string to test
     */
    @ParameterizedTest
    @NullAndEmptySource
    void validateEmail_WithNullOrEmpty_ShouldThrowException(String invalidValue) {
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validateEmail(invalidValue));
    }

    /**
     * Tests that valid passwords meeting complexity requirements pass validation.
     * Valid passwords must be at least 8 characters and contain at least one letter.
     *
     * @param validPassword a valid password string to test
     */
    @ParameterizedTest
    @ValueSource(strings = {"Password123!", "SecurePass@99", "MyPass#word8"})
    void validatePassword_WithValidPasswords_ShouldPass(String validPassword) {
        assertDoesNotThrow(() -> FieldValidator.validatePassword(validPassword));
    }

    /**
     * Tests that passwords shorter than 8 characters are rejected.
     *
     * @param shortPassword a password string with fewer than 8 characters
     */
    @ParameterizedTest
    @ValueSource(strings = {"short", "1234567", "pass", "Pass1"})
    void validatePassword_WithTooShortPasswords_ShouldThrowException(String shortPassword) {
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validatePassword(shortPassword));
    }

    /**
     * Tests that passwords without at least one letter are rejected.
     *
     * @param invalidPassword a password string containing no alphabetic characters
     */
    @ParameterizedTest
    @ValueSource(strings = {"password!@#", "!!!!!!!!", "$$$$$$$$"})
    void validatePassword_WithInvalidCharacters_ShouldThrowException(String invalidPassword) {
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validatePassword(invalidPassword));
    }

    /**
     * Tests that null and empty/blank strings are rejected for password fields.
     *
     * @param invalidValue null or empty string to test
     */
    @ParameterizedTest
    @NullAndEmptySource
    void validatePassword_WithNullOrEmpty_ShouldThrowException(String invalidValue) {
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validatePassword(invalidValue));
    }

    /**
     * Tests that valid positive salary amounts pass validation.
     *
     * @param validSalary a positive salary value to test
     */
    @ParameterizedTest
    @ValueSource(doubles = {1000.0, 50000.0, 0.01, 999999.99})
    void validateSalary_WithValidSalaries_ShouldPass(double validSalary) {
        assertDoesNotThrow(() -> FieldValidator.validateSalary(validSalary));
    }

    /**
     * Tests that zero and negative salary amounts are rejected.
     *
     * @param invalidSalary a zero or negative salary value
     */
    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0, -1000.0})
    void validateSalary_WithZeroOrNegative_ShouldThrowException(double invalidSalary) {
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validateSalary(invalidSalary));
    }

    /**
     * Tests that NaN (Not a Number) salary values are rejected.
     */
    @Test
    void validateSalary_WithNaN_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validateSalary(Double.NaN));
    }

    /**
     * Tests that infinite salary values (positive and negative) are rejected.
     */
    @Test
    void validateSalary_WithInfinite_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validateSalary(Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validateSalary(Double.NEGATIVE_INFINITY));
    }

    /**
     * Tests that valid positive ID values pass validation.
     *
     * @param validId a positive integer ID to test
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 42, 999999})
    void validateId_WithValidIds_ShouldPass(int validId) {
        assertDoesNotThrow(() -> FieldValidator.validateId(validId));
    }

    /**
     * Tests that zero and negative ID values are rejected.
     *
     * @param invalidId a zero or negative integer ID
     */
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -42, -999999})
    void validateId_WithZeroOrNegative_ShouldThrowException(int invalidId) {
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validateId(invalidId));
    }

    /**
     * Tests that valid student scores within 0-100 range pass validation.
     *
     * @param validScore a score value between 0 and 100 (inclusive)
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 50, 100})
    void validateScore_WithValidScores_ShouldPass(int validScore) {
        assertDoesNotThrow(() -> FieldValidator.validateScore(validScore));
    }

    /**
     * Tests that scores outside the valid 0-100 range are rejected.
     *
     * @param invalidScore a score value outside the 0-100 range
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 101, -10, 150})
    void validateScore_WithOutOfRangeScores_ShouldThrowException(int invalidScore) {
        assertThrows(IllegalArgumentException.class,
                () -> FieldValidator.validateScore(invalidScore));
    }

    /**
     * Tests that salt generation produces a non-null, non-empty string.
     */
    @Test
    void makeProtectedSalt_ShouldReturnNonNullString() {
        String salt = FieldValidator.makeProtectedSalt();
        assertNotNull(salt);
        assertFalse(salt.isEmpty());
    }

    /**
     * Tests that each salt generation produces a unique value.
     */
    @Test
    void makeProtectedSalt_ShouldReturnDifferentValues() {
        String salt1 = FieldValidator.makeProtectedSalt();
        String salt2 = FieldValidator.makeProtectedSalt();
        assertNotEquals(salt1, salt2);
    }

    /**
     * Tests that password hashing produces a non-null, non-empty hash different from the original.
     */
    @Test
    void makeProtectedPasswordWithSalt_ShouldReturnHashedPassword() {
        String salt = FieldValidator.makeProtectedSalt();
        String hashedPassword = FieldValidator.makeProtectedPasswordWithSalt("Password123!", salt);

        assertNotNull(hashedPassword);
        assertFalse(hashedPassword.isEmpty());
        assertNotEquals("Password123!", hashedPassword);
    }

    /**
     * Tests that the same password with different salts produces different hashes.
     */
    @Test
    void makeProtectedPasswordWithSalt_WithSameInputAndDifferentSalts_ShouldProduceDifferentHashes() {
        String salt1 = FieldValidator.makeProtectedSalt();
        String salt2 = FieldValidator.makeProtectedSalt();

        String hash1 = FieldValidator.makeProtectedPasswordWithSalt("Password123!", salt1);
        String hash2 = FieldValidator.makeProtectedPasswordWithSalt("Password123!", salt2);

        assertNotEquals(hash1, hash2);
    }

    /**
     * Tests that password verification returns true for the correct password.
     */
    @Test
    void verifyPassword_WithCorrectPassword_ShouldReturnTrue() {
        String salt = FieldValidator.makeProtectedSalt();
        String hashedPassword = FieldValidator.makeProtectedPasswordWithSalt("Password123!", salt);

        assertTrue(FieldValidator.verifyPassword("Password123!", hashedPassword, salt));
    }

    /**
     * Tests that password verification returns false for an incorrect password.
     */
    @Test
    void verifyPassword_WithIncorrectPassword_ShouldReturnFalse() {
        String salt = FieldValidator.makeProtectedSalt();
        String hashedPassword = FieldValidator.makeProtectedPasswordWithSalt("Password123!", salt);

        assertFalse(FieldValidator.verifyPassword("WrongPassword!", hashedPassword, salt));
    }
}
