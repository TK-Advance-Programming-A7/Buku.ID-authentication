package id.ac.ui.cs.advprog.authentication.services;

import id.ac.ui.cs.advprog.authentication.dtos.LoginUserDto;
import id.ac.ui.cs.advprog.authentication.dtos.RegisterUserDto;
import id.ac.ui.cs.advprog.authentication.models.entities.UserEntity;
import id.ac.ui.cs.advprog.authentication.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateSuccess() {
        String email = "erz@example.com";
        String password = "samplePassword";

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(email);
        loginUserDto.setPassword(password);

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setFullName("Erz");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        UserEntity result = authenticationService.authenticate(loginUserDto);

        verify(authenticationManager).authenticate(
            new UsernamePasswordAuthenticationToken(email, password));
        verify(userRepository).findByEmail(email);
        assertEquals(userEntity, result);
    }

    @Test
    void testAuthenticateFailure() {
        String email = "zzxx@example.com";
        String password = "samplePassword";

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(email);
        loginUserDto.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(loginUserDto));
        verify(authenticationManager).authenticate(
            new UsernamePasswordAuthenticationToken(email, password));
        verify(userRepository).findByEmail(email);
    }

    @Test
    void testSignup() {
        // Given
        String fullName = "Erz";
        String email = "erz@example.com";
        String rawPassword = "samplePassword";
        String encodedPassword = "encodedPassword";
        String role = "USER";
        String gender = "MALE";
        String phone = "1234567890";
        String bio = "A short bio";

        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setFullName(fullName);
        registerUserDto.setEmail(email);
        registerUserDto.setPassword(rawPassword);
        registerUserDto.setRole(role);
        registerUserDto.setGender(gender);
        registerUserDto.setPhone(phone);
        registerUserDto.setBio(bio);

        UserEntity expectedUserEntity = new UserEntity();
        expectedUserEntity.setFullName(fullName);
        expectedUserEntity.setEmail(email);
        expectedUserEntity.setPassword(encodedPassword);
        expectedUserEntity.setRole(role);
        expectedUserEntity.setGender(gender);
        expectedUserEntity.setPhone(phone);
        expectedUserEntity.setBio(bio);

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(expectedUserEntity);

        UserEntity result = authenticationService.signup(registerUserDto);

        assertEquals(expectedUserEntity, result);
        verify(passwordEncoder).encode(rawPassword);
        verify(userRepository).save(any(UserEntity.class));
    }
}
