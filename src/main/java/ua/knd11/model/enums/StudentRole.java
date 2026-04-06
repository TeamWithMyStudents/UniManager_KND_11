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

    StudentRole(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }
}
