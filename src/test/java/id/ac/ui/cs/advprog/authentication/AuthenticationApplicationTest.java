package id.ac.ui.cs.advprog.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class AuthenticationApplicationTests {

  @Autowired
  private AuthenticationApplication authenticationApplication;

  @Test
  void contextLoads() {
    assertNotNull(authenticationApplication);
  }

  @Test
  void mainMethodDoesNotThrowException() {
    assertDoesNotThrow(() -> AuthenticationApplication.main(new String[] {}));
  }
}
