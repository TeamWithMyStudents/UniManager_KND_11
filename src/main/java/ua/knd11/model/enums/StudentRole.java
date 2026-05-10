package ua.knd11.model.enums;

import lombok.Getter;

@Getter
public enum StudentRole {

    HEAD_STUDENT("Head Student"),
    REGULAR("Student");
    //normal name to output in console
    private final String displayName;

    StudentRole(String displayName) {
        this.displayName = displayName;
    }

}
