package id.ac.ui.cs.advprog.authentication.services;

import net.datafaker.Faker;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.authentication.models.builder.UserBuilder;
import id.ac.ui.cs.advprog.authentication.models.entities.UserEntity;
import id.ac.ui.cs.advprog.authentication.repositories.UserRepository;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Service
public class SeedService {
  private final UserRepository userRepository;
  private final String[] GENDER = { "MALE", "FEMALE", "OTHER" };
  public static final int NUMBER_OF_USER = 10_000;
  private final UserBuilder userBuilder;

  public SeedService(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.userBuilder = new UserBuilder();
  }

  @Async("taskExecutorDefault")
  public CompletableFuture<Boolean> seedAuth() {
    @SuppressWarnings("deprecation")
    Faker faker = new Faker(new Locale("id_ID"));

    for (int i = 0; i < NUMBER_OF_USER; i++) {
      String fullName = faker.name().fullName();
      String email = faker.internet().emailAddress();
      String phone = faker.phoneNumber().phoneNumber();
      String password = faker.internet().password();
      String bio = faker.lorem().sentence();
      String gender = GENDER[faker.random().nextInt(0, 2)];

      try {
        UserEntity user = userBuilder
            .setFullName(fullName)
            .setEmail(email)
            .setPhone(phone)
            .setPassword(password)
            .setBio(bio)
            .setGender(gender)
            .setRole("USER")
            .build();
        userRepository.save(user);
      } catch (IllegalArgumentException e) {
        return CompletableFuture.completedFuture(false);
      }
    }
    return CompletableFuture.completedFuture(true);
  }
}
