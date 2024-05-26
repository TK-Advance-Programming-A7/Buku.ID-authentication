package id.ac.ui.cs.advprog.authentication.services;

import id.ac.ui.cs.advprog.authentication.dtos.LoginUserDto;
import id.ac.ui.cs.advprog.authentication.dtos.RegisterUserDto;
import id.ac.ui.cs.advprog.authentication.models.builder.UserBuilder;
import id.ac.ui.cs.advprog.authentication.models.entities.UserEntity;
import id.ac.ui.cs.advprog.authentication.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final UserBuilder userBuilder;

  public AuthenticationService(
      UserRepository userRepository,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userBuilder = new UserBuilder();
  }

  public UserEntity signup(RegisterUserDto input) {
    UserEntity user = userBuilder.fromRegisterUserDTO(input, passwordEncoder).build();
    return userRepository.save(user);
  }

  public UserEntity authenticate(LoginUserDto input) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            input.getEmail(),
            input.getPassword()));

    return userRepository.findByEmail(input.getEmail())
        .orElseThrow();
  }
}
