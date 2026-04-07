package ua.knd11.model.enums;

/**
 * Enumeration of student roles within a group.
 */
public enum StudentRole {

    /**
     * The Head student.
     * Each group can only have one active Head Student.
     */
    HEAD_STUDENT("Head Student"),
    /**
     * The Deputy head student.
     */
    DEPUTY_HEAD_STUDENT("Deputy Head Student"),
    /**
     * Regular student role.
     */
    REGULAR("Student");
    //normal name to output in console
    private final String displayName;

    /**
     * Create a StudentRole with the given display name.
     *
     * @param displayName the label to use for this role when shown to users or in logs
     */
    StudentRole(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the human-readable label associated with this student role.
     *
     * @return the display name for this enum constant
     */
    public String getDisplayName() {
        return displayName;
    }
}
