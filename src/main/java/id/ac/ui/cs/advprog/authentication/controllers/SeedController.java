package id.ac.ui.cs.advprog.authentication.controllers;

import id.ac.ui.cs.advprog.authentication.services.SeedService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/seed")
public class SeedController {
  private final SeedService seedService;

  public SeedController(SeedService seedService) {
    this.seedService = seedService;
  }

  @GetMapping("/auth")
  public CompletableFuture<ResponseEntity<String>> seedUser() {
    return seedService.seedAuth()
        .thenApply(isSeeded -> {
          if (Boolean.FALSE.equals(isSeeded)) {
            return ResponseEntity.badRequest().body("Seeding data auth failed.");
          }
          return ResponseEntity.ok("Seeding data auth completed successfully.");
        })
        .exceptionally(e -> ResponseEntity.badRequest().body("Seeding data auth failed due to an error."));
  }
}
