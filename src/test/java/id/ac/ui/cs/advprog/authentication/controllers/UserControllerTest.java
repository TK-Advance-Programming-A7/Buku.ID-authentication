package id.ac.ui.cs.advprog.authentication.controllers;

import id.ac.ui.cs.advprog.authentication.dtos.RegisterUserDto;
import id.ac.ui.cs.advprog.authentication.dtos.ResponseUserDto;
import id.ac.ui.cs.advprog.authentication.models.entities.UserEntity;
import id.ac.ui.cs.advprog.authentication.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testAuthenticatedUser() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName("Erz ZX");
        userEntity.setEmail("erz@example.com");

        when(authentication.getPrincipal()).thenReturn(userEntity);

        mockMvc.perform(get("/api/user/me"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.fullName", is("Erz ZX")))
            .andExpect(jsonPath("$.email", is("erz@example.com")));
    }

    @Test
    public void testAllUsers() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName("John Doe");
        userEntity.setEmail("john.doe@example.com");

        List<UserEntity> users = Collections.singletonList(userEntity);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(userService.allUsers()).thenReturn(CompletableFuture.completedFuture(users));

        mockMvc.perform(get("/api/user/all"))
            .andExpect(status().isOk());
    }
}