package id.ac.ui.cs.advprog.authentication.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private String secretKey = "dGhpcy1pcy1hLXNlY3JldC1rZXktZm9yLXRlc3RpbmctcHVycG9zZXM=";
    private long jwtExpiration = 1000 * 60 * 60;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService.secretKey = secretKey;
        jwtService.jwtExpiration = jwtExpiration;

        when(userDetails.getUsername()).thenReturn("testUser");
    }

    @Test
    void testExtractUsername() {
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);
        assertEquals("testUser", username);
    }

    @Test
    void testGenerateToken() {
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void testIsTokenValid() {
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }
}
