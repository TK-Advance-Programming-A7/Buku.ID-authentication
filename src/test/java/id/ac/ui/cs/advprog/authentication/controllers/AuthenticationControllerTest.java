package id.ac.ui.cs.advprog.authentication.controllers;

import id.ac.ui.cs.advprog.authentication.dtos.LoginUserDto;
import id.ac.ui.cs.advprog.authentication.dtos.RegisterUserDto;
import id.ac.ui.cs.advprog.authentication.models.entities.UserEntity;
import id.ac.ui.cs.advprog.authentication.services.AuthenticationService;
import id.ac.ui.cs.advprog.authentication.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

class AuthenticationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    void testRegister() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setFullName("Erz ZX");
        registerUserDto.setEmail("erz@example.com");
        registerUserDto.setPassword("password123");
        registerUserDto.setRole("USER");
        registerUserDto.setGender("MALE");
        registerUserDto.setPhone("1234567890");
        registerUserDto.setBio("A short bio");

        UserEntity userEntity = new UserEntity();
        userEntity.setFullName("Erz ZX");
        userEntity.setEmail("erz@example.com");

        when(authenticationService.signup(any(RegisterUserDto.class))).thenReturn(userEntity);

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fullName\": \"Erz ZX\", \"email\": \"erz@example.com\", \"password\": \"password123\", \"role\": \"USER\", \"gender\": \"MALE\", \"phone\": \"1234567890\", \"bio\": \"A short bio\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.fullName", is("Erz ZX")))
            .andExpect(jsonPath("$.email", is("erz@example.com")));
    }

    @Test
    void testAuthenticate() throws Exception {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("erz@example.com");
        loginUserDto.setPassword("password123");

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("erz@example.com");
        userEntity.setFullName("Erz ZX");

        String jwtToken = "mockedJwtToken";

        when(authenticationService.authenticate(any(LoginUserDto.class))).thenReturn(userEntity);
        when(jwtService.generateToken(any(UserEntity.class))).thenReturn(jwtToken);
        when(jwtService.getExpirationTime()).thenReturn(3600L);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"erz@example.com\", \"password\": \"password123\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token", is(jwtToken)))
            .andExpect(jsonPath("$.expiresIn", is(3600)));
    }
}