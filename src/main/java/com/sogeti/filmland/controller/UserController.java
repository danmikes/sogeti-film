package com.sogeti.filmland.controller;

import com.sogeti.filmland.entity.Category;
import com.sogeti.filmland.entity.Share;
import com.sogeti.filmland.entity.Subscription;
import com.sogeti.filmland.entity.User;
import com.sogeti.filmland.repository.CategoryRepository;
import com.sogeti.filmland.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale.Builder;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Validated
@RestController
@RequestMapping
public class UserController {

  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;

  public UserController(UserRepository userRepository, CategoryRepository categoryRepository) {
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
  }

  @GetMapping("users")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userRepository.findAll();
    return ResponseEntity.ok(users);
  }

  @GetMapping("user/id/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
    return userRepository.findById(id)
      .map(ResponseEntity::ok)
      .orElseThrow();
  }

  @GetMapping("user/email/{email}")
  @Pattern(regexp = "^(.+)@(.+)$")
  public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
    return userRepository.findByEmail(email)
      .map(ResponseEntity::ok)
      .orElseThrow();
  }

  @PostMapping("user/subscribe")
  public ResponseEntity<Category> subscribeCategory(@RequestBody Subscription subscription) {
    Category category = userRepository.findByEmail(subscription.getEmail()).map(user -> {
      if (subscription.getAvailableCategory() != null) {
        Category _category = categoryRepository.findByName(subscription.getAvailableCategory())
          .orElseThrow();
        _category.setStartDate(LocalDate.now());
        user.addCategory(_category);
        userRepository.save(user);
        return _category;
      }
      return null;
    }).orElseThrow();
    return new ResponseEntity<>(category, HttpStatus.OK);
  }

  @PostMapping("user/share")
  public ResponseEntity<Category> shareCategory(@RequestBody Share share) {
    return categoryRepository.findByName(share.getAvailableCategory())
      .map(availableCategory ->
        subscribeCategory(Subscription.builder()
        .email(share.getEmail())
        .email(share.getCustomer())
        .availableCategory(share.getAvailableCategory())
        .build())).orElseThrow();
  }
}
