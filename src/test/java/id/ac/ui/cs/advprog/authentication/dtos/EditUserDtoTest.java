package id.ac.ui.cs.advprog.authentication.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EditUserDtoTest {

  @Test
  void testDefaultConstructorAndSetters() {
    String expectedFullName = "Jane Doe";
    String expectedEmail = "jane.doe@example.com";
    String expectedPhone = "0987654321";
    String expectedBio = "A different bio";
    String expectedGender = "FEMALE";
    String expectedRole = "ADMIN";

    EditUserDto editUserDto = new EditUserDto();
    editUserDto.setFullName(expectedFullName);
    editUserDto.setEmail(expectedEmail);
    editUserDto.setPhone(expectedPhone);
    editUserDto.setBio(expectedBio);
    editUserDto.setGender(expectedGender);
    editUserDto.setRole(expectedRole);

    assertEquals(expectedFullName, editUserDto.getFullName());
    assertEquals(expectedEmail, editUserDto.getEmail());
    assertEquals(expectedPhone, editUserDto.getPhone());
    assertEquals(expectedBio, editUserDto.getBio());
    assertEquals(expectedGender, editUserDto.getGender());
    assertEquals(expectedRole, editUserDto.getRole());
  }
}
