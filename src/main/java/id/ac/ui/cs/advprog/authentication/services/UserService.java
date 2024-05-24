package id.ac.ui.cs.advprog.authentication.services;

import id.ac.ui.cs.advprog.authentication.models.entities.UserEntity;
import id.ac.ui.cs.advprog.authentication.repositories.UserRepository;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Async("taskExecutorForHeavyTasks")
  public CompletableFuture<List<UserEntity>> allUsers() {
    return CompletableFuture.supplyAsync(() -> {
      return userRepository.findAll();
    });
  }

  @Async()
  public CompletableFuture<UserEntity> editUser(UserEntity user, UserEntity newUser) {
    return CompletableFuture.supplyAsync(() -> {
      user.setFullName(newUser.getFullName());
      user.setEmail(newUser.getEmail());
      user.setPassword(newUser.getPassword());
      user.setGender(newUser.getGender());
      user.setBio(newUser.getBio());
      user.setPhone(newUser.getPhone());

      return userRepository.save(user);
    });
  }
}