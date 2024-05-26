package id.ac.ui.cs.advprog.authentication.controllers;

import id.ac.ui.cs.advprog.authentication.dtos.EditUserDto;
import id.ac.ui.cs.advprog.authentication.models.builder.UserBuilder;
import id.ac.ui.cs.advprog.authentication.dtos.ResponseUserDto;
import id.ac.ui.cs.advprog.authentication.models.entities.UserEntity;
import id.ac.ui.cs.advprog.authentication.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequestMapping("/api/user")
@RestController
public class UserController {
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;
  private final UserBuilder userBuilder;

  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userBuilder = new UserBuilder();
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/me")
  public ResponseEntity<ResponseUserDto> authenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserEntity currentUser = (UserEntity) authentication.getPrincipal();
    ResponseUserDto userDto = new ResponseUserDto(currentUser);
    return ResponseEntity.ok(userDto);
  }

  @PostMapping("/edit")
  public CompletableFuture<ResponseEntity<ResponseUserDto>> editUser(@RequestBody EditUserDto input) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserEntity currentUser = (UserEntity) authentication.getPrincipal();
    UserEntity newUser = userBuilder.fromEditUserDTO(input, passwordEncoder, currentUser).build();

    return userService.editUser(currentUser, newUser)
        .thenApply(updatedUser -> ResponseEntity.ok(new ResponseUserDto(updatedUser)))
        .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUserDto()));
  }

  @GetMapping("/get/{email}")
  public ResponseEntity<ResponseUserDto> getUserEmail(@PathVariable("email") String email) {
    Optional<UserEntity> currentUser = userService.getUserByEmail(email);
    if (currentUser.isPresent()) {
      ResponseUserDto userDto = new ResponseUserDto(currentUser.get());
      return ResponseEntity.ok(userDto);
    }else{
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseUserDto());
    }
  }

  @GetMapping("/all")
  public CompletableFuture<ResponseEntity<List<ResponseUserDto>>> allUsers() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
    }

    return userService.allUsers()
        .thenApply(users -> {
          List<ResponseUserDto> userDtos = users.stream()
              .map(ResponseUserDto::new)
              .collect(Collectors.toUnmodifiableList());
          return ResponseEntity.ok(userDtos);
        })
        .exceptionally(e -> ResponseEntity.badRequest().build());
  }
}