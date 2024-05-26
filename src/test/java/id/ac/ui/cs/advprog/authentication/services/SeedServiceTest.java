package id.ac.ui.cs.advprog.authentication.services;

import id.ac.ui.cs.advprog.authentication.models.entities.UserEntity;
import id.ac.ui.cs.advprog.authentication.repositories.UserRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SeedServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SeedService seedService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSeedAuth() throws Exception {
        // Given
        @SuppressWarnings("deprecation")
        Faker faker = new Faker(new Locale("id_ID"));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity user = invocation.getArgument(0);
            user.setIdUser(Long.toString(faker.number().randomNumber())); // Mock ID assignment
            return user;
        });

        // When
        CompletableFuture<Boolean> result = seedService.seedAuth();

        // Then
        assertTrue(result.get());
        verify(userRepository, times(SeedService.NUMBER_OF_USER)).save(any(UserEntity.class));

        // Capture the saved entities to perform additional assertions if necessary
        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository, atLeastOnce()).save(userCaptor.capture());
        List<UserEntity> savedUsers = userCaptor.getAllValues();

        // Additional assertions to verify the properties of the saved users
        for (UserEntity user : savedUsers) {
            assertTrue(user.getFullName() != null && !user.getFullName().isEmpty());
            assertTrue(user.getEmail() != null && !user.getEmail().isEmpty());
            assertTrue(user.getPhone() != null && !user.getPhone().isEmpty());
            assertTrue(user.getPassword() != null && !user.getPassword().isEmpty());
            assertTrue(user.getBio() != null && !user.getBio().isEmpty());
            assertTrue(user.getGender() != null && !user.getGender().isEmpty());
            assertEquals("USER", user.getRole());
        }
    }
}
