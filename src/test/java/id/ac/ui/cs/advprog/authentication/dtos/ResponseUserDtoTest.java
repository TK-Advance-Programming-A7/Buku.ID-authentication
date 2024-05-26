package id.ac.ui.cs.advprog.authentication.dtos;

import id.ac.ui.cs.advprog.authentication.models.entities.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseUserDtoTest {

    @Test
    void testDefaultConstructorAndSetters() {
        String expectedFullName = "Jane Doe";
        String expectedEmail = "jane.doe@example.com";
        String expectedPhone = "0987654321";
        String expectedBio = "A different bio";
        String expectedGender = "FEMALE";
        String expectedRole = "ADMIN";

        ResponseUserDto responseUserDto = new ResponseUserDto();
        responseUserDto.setFullName(expectedFullName);
        responseUserDto.setEmail(expectedEmail);
        responseUserDto.setPhone(expectedPhone);
        responseUserDto.setBio(expectedBio);
        responseUserDto.setGender(expectedGender);
        responseUserDto.setRole(expectedRole);

        assertEquals(expectedFullName, responseUserDto.getFullName());
        assertEquals(expectedEmail, responseUserDto.getEmail());
        assertEquals(expectedPhone, responseUserDto.getPhone());
        assertEquals(expectedBio, responseUserDto.getBio());
        assertEquals(expectedGender, responseUserDto.getGender());
        assertEquals(expectedRole, responseUserDto.getRole());
    }

    @Test
    void testConstructorWithUserEntity() {
        // Given
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName("John Doe");
        userEntity.setEmail("john.doe@example.com");
        userEntity.setPhone("1234567890");
        userEntity.setBio("A short bio");
        userEntity.setGender("MALE");
        userEntity.setRole("USER");

        // When
        ResponseUserDto responseUserDto = new ResponseUserDto(userEntity);

        // Then
        assertEquals(userEntity.getFullName(), responseUserDto.getFullName());
        assertEquals(userEntity.getEmail(), responseUserDto.getEmail());
        assertEquals(userEntity.getPhone(), responseUserDto.getPhone());
        assertEquals(userEntity.getBio(), responseUserDto.getBio());
        assertEquals(userEntity.getGender(), responseUserDto.getGender());
        assertEquals(userEntity.getRole(), responseUserDto.getRole());
    }
}
