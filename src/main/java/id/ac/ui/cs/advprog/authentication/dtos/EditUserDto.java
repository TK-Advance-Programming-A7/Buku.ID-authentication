package id.ac.ui.cs.advprog.authentication.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserDto {
  private String fullName;
  private String email;
  private String role;
  private String gender;
  private String phone;
  private String bio;
}
