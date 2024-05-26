package id.ac.ui.cs.advprog.authentication.models.builder;

import java.security.InvalidParameterException;

import id.ac.ui.cs.advprog.authentication.dtos.EditUserDto;
import id.ac.ui.cs.advprog.authentication.dtos.RegisterUserDto;
import id.ac.ui.cs.advprog.authentication.models.entities.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Getter;

@Getter
public class UserBuilder {
  UserEntity currentUser = new UserEntity();

  public UserBuilder setFullName(String fullName) {
    currentUser.setFullName(fullName);
    return this;
  }

  public UserBuilder setEmail(String email) {
    currentUser.setEmail(email);
    return this;
  }

  public UserBuilder setPhone(String phone) {
    currentUser.setPhone(phone);
    return this;
  }

  public UserBuilder setPassword(String password) {
    currentUser.setPassword(password);
    return this;
  }

  public UserBuilder setBio(String bio) {
    currentUser.setBio(bio);
    return this;
  }

  public UserBuilder setGender(String gender) {
    currentUser.setGender(gender.toUpperCase());
    return this;
  }

  public UserBuilder setRole(String role) {
    currentUser.setRole(role.toUpperCase());
    return this;
  }

  public UserEntity build() throws InvalidParameterException {
    UserEntity finalUser = currentUser;
    reset();
    return finalUser;
  }

  public UserBuilder fromRegisterUserDTO(RegisterUserDto input, PasswordEncoder passwordEncoder) {
    return setFullName(input.getFullName())
        .setEmail(input.getEmail())
        .setPassword(passwordEncoder.encode(input.getPassword()))
        .setRole(input.getRole())
        .setGender(input.getGender())
        .setPhone(input.getPhone())
        .setBio(input.getBio());
  }

  public UserBuilder fromEditUserDTO(EditUserDto input, PasswordEncoder passwordEncoder, UserEntity currentUser) {
    return setFullName(input.getFullName())
        .setEmail(input.getEmail())
        .setPassword(passwordEncoder.encode(currentUser.getPassword()))
        .setRole(input.getRole())
        .setGender(input.getGender())
        .setPhone(input.getPhone())
        .setBio(input.getBio());
  }

  private void reset() {
    currentUser = new UserEntity();
  }
}
