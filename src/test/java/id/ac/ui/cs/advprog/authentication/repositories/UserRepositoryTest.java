package id.ac.ui.cs.advprog.authentication.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import id.ac.ui.cs.advprog.authentication.enums.GenderEnum;
import id.ac.ui.cs.advprog.authentication.models.builder.UserBuilder;
import id.ac.ui.cs.advprog.authentication.models.entities.UserEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class UserRepositoryTest {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TestEntityManager entityManager;

  private final UserBuilder userBuilder = new UserBuilder();

  @BeforeEach
  void setUp() {
    UserEntity user = userBuilder.setFullName("Andi")
        .setEmail("andi@gmail.com")
        .setPhone("081321543876")
        .setPassword("AXASW123ASXASEA")
        .setBio("An assembly competitive programmer")
        .setGender("MALE")
        .setRole("USER")
        .build();

    entityManager.persist(user);
  }

  @Test
  void testFindByEmail() {
    UserEntity found = userRepository.findByEmail("andi@gmail.com").orElse(null);

    assertNotNull(found);

    assertEquals("Andi", found.getFullName());
    assertEquals("andi@gmail.com", found.getEmail());
    assertEquals("081321543876", found.getPhone());
    assertEquals("AXASW123ASXASEA", found.getPassword());
    assertEquals("An assembly competitive programmer", found.getBio());
    assertEquals(GenderEnum.MALE.toString(), found.getGender());
  }
}
