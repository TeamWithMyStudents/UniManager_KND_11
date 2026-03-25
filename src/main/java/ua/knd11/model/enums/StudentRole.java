package ua.knd11.model.enums;

public enum StudentRole {
    HEAD_STUDENT("Head Student"),
    DEPUTY_HEAD_STUDENT("Deputy Head Student"),
    REGULAR("Student");

    private final String displayName;

    StudentRole(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}
