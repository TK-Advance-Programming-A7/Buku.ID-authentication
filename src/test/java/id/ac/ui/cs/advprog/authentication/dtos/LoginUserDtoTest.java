package id.ac.ui.cs.advprog.authentication.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginUserDtoTest {

    @Test
    void testLoginUserDtoGettersAndSetters() {
        String expectedEmail = "user@example.com";
        String expectedPassword = "securePassword";

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(expectedEmail);
        loginUserDto.setPassword(expectedPassword);

        assertEquals(expectedEmail, loginUserDto.getEmail());
        assertEquals(expectedPassword, loginUserDto.getPassword());
    }
}
