package id.ac.ui.cs.advprog.authentication.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginResponseTest {

    @Test
    void testLoginResponseGettersAndSetters() {
        String expectedToken = "sampleToken";
        long expectedExpiresIn = 3600L;

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(expectedToken);
        loginResponse.setExpiresIn(expectedExpiresIn);

        assertEquals(expectedToken, loginResponse.getToken());
        assertEquals(expectedExpiresIn, loginResponse.getExpiresIn());
    }
}
