package ua.knd11.model.enums;

/**
 * Enumeration of student roles within the university system.
 * Defines the hierarchy and responsibilities available to students.
 */
public enum StudentRole {

    /**
     * Student with leadership responsibilities for their group.
     * Only one Head Student can exist per group.
     */
//Each group can only have one active Head Student
    HEAD_STUDENT("Head Student"),
    /**
     * Student with secondary leadership responsibilities.
     * Assists the Head Student in group management.
     */
    DEPUTY_HEAD_STUDENT("Deputy Head Student"),
    /**
     * Standard student role with no additional responsibilities.
     */
    REGULAR("Student");
    /**
     * Display name for console output and user interface.
     */
    private final String displayName;

    StudentRole(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name for this student role.
     *
     * @return the formatted display name
     */
    public String getDisplayName() {
        return displayName;
    }
}
