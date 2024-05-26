package id.ac.ui.cs.advprog.authentication.dtos;

import id.ac.ui.cs.advprog.authentication.models.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserDto {
  private String fullName;
  private String email;
  private String phone;
  private String bio;
  private String gender;
  private String role;

  public ResponseUserDto() {
  }

  public ResponseUserDto(UserEntity userEntity) {
    this.fullName = userEntity.getFullName();
    this.email = userEntity.getEmail();
    this.phone = userEntity.getPhone();
    this.bio = userEntity.getBio();
    this.gender = userEntity.getGender();
    this.role = userEntity.getRole();
  }
}
