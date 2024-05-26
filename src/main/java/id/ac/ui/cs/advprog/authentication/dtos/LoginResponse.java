package id.ac.ui.cs.advprog.authentication.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginResponse {
  private String token;
  private long expiresIn;
}
