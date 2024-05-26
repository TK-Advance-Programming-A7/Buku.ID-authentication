package id.ac.ui.cs.advprog.authentication.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {
    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHER("OTHER");

    private final String value;

    private GenderEnum(String value) {
        this.value = value;
    }

    public static boolean contains(String value) {
        for (GenderEnum gender : GenderEnum.values()) {
            if (gender.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
