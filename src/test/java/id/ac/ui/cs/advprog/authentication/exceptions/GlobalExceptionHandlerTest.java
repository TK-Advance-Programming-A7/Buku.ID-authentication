package id.ac.ui.cs.advprog.authentication.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(new TestController())
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void testHandleBadCredentialsException() throws Exception {
        mockMvc.perform(get("/test/badCredentials"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.status", is(401)))
            .andExpect(jsonPath("$.detail", is("Bad credentials")))
            .andExpect(jsonPath("$.description", is("The username or password is incorrect")));
    }

    @Test
    void testHandleAccountStatusException() throws Exception {
        mockMvc.perform(get("/test/accountStatus"))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.status", is(403)))
            .andExpect(jsonPath("$.detail", is("Account status issue")))
            .andExpect(jsonPath("$.description", is("Your account is disabled. Please contact the administrator for further information.")));
    }

    @Test
    void testHandleAccessDeniedException() throws Exception {
        mockMvc.perform(get("/test/accessDenied"))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.status", is(403)))
            .andExpect(jsonPath("$.detail", is("Access denied")))
            .andExpect(jsonPath("$.description", is("You are not authorized to access this resource.")));
    }

    @Test
    void testHandleSignatureException() throws Exception {
        mockMvc.perform(get("/test/signature"))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.status", is(403)))
            .andExpect(jsonPath("$.detail", is("Invalid signature")))
            .andExpect(jsonPath("$.description", is("The JWT signature is invalid.")));
    }

    @Test
    void testHandleExpiredJwtException() throws Exception {
        mockMvc.perform(get("/test/expiredJwt"))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.status", is(403)))
            .andExpect(jsonPath("$.detail", is("Token expired")))
            .andExpect(jsonPath("$.description", is("The JWT token has expired. Please login again.")));
    }

    @Test
    void testHandleUnknownException() throws Exception {
        mockMvc.perform(get("/test/unknown"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.status", is(500)))
            .andExpect(jsonPath("$.detail", is("Unknown error")))
            .andExpect(jsonPath("$.description", is("Unknown internal server error.")));
    }

    @RestController
    private static class TestController {
        @GetMapping("/test/badCredentials")
        public void badCredentials() {
            throw new BadCredentialsException("Bad credentials");
        }

        @GetMapping("/test/accountStatus")
        public void accountStatus() {
            throw new AccountStatusException("Account status issue") {};
        }

        @GetMapping("/test/accessDenied")
        public void accessDenied() {
            throw new AccessDeniedException("Access denied");
        }

        @GetMapping("/test/signature")
        public void signature() {
            throw new SignatureException("Invalid signature");
        }

        @GetMapping("/test/expiredJwt")
        public void expiredJwt() {
            throw new ExpiredJwtException(null, null, "Token expired");
        }

        @GetMapping("/test/unknown")
        public void unknown() {
            throw new RuntimeException("Unknown error");
        }
    }
}
