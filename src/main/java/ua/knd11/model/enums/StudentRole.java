package ua.knd11.model.enums;

public enum StudentRole {

    //Each group can only have one active Head Student
    HEAD_STUDENT("Head Student"),
    DEPUTY_HEAD_STUDENT("Deputy Head Student"),
    REGULAR("Student");
    //normal name to output in console
    private final String displayName;

    StudentRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
