package id.ac.ui.cs.advprog.authentication.controllers;

import id.ac.ui.cs.advprog.authentication.models.entities.UserEntity;
import id.ac.ui.cs.advprog.authentication.dtos.LoginUserDto;
import id.ac.ui.cs.advprog.authentication.dtos.RegisterUserDto;
import id.ac.ui.cs.advprog.authentication.dtos.ResponseUserDto;
import id.ac.ui.cs.advprog.authentication.dtos.LoginResponse;
import id.ac.ui.cs.advprog.authentication.services.AuthenticationService;
import id.ac.ui.cs.advprog.authentication.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
  private final JwtService jwtService;

  private final AuthenticationService authenticationService;

  public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
    this.jwtService = jwtService;
    this.authenticationService = authenticationService;
  }

  @PostMapping("/signup")
  public ResponseEntity<ResponseUserDto> register(@RequestBody RegisterUserDto registerUserDto) {
    UserEntity registeredUser = authenticationService.signup(registerUserDto);
    ResponseUserDto responseUser = new ResponseUserDto(registeredUser);
    return ResponseEntity.ok(responseUser);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
    UserEntity authenticatedUser = authenticationService.authenticate(loginUserDto);

    String jwtToken = jwtService.generateToken(authenticatedUser);

    LoginResponse loginResponse = new LoginResponse();
    loginResponse.setToken(jwtToken);
    loginResponse.setExpiresIn(jwtService.getExpirationTime());

    return ResponseEntity.ok(loginResponse);
  }
}
