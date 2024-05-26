package id.ac.ui.cs.advprog.authentication.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
  ADMIN("ADMIN"),
  USER("USER");

  private final String value;

  private RoleEnum(String value) {
    this.value = value;
  }

  public static boolean contains(String value) {
    for (RoleEnum role : RoleEnum.values()) {
      if (role.name().equals(value)) {
        return true;
      }
    }
    return false;
  }
}
