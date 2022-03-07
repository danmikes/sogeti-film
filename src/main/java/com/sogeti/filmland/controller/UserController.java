package com.sogeti.filmland.controller;

import com.sogeti.filmland.entity.Category;
import com.sogeti.filmland.entity.Share;
import com.sogeti.filmland.entity.Subscribe;
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
import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @GetMapping("users")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userRepository.findAll();
    if (users.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(users, HttpStatus.OK);
    }
  }

  @GetMapping("user/id/{id}")
  public ResponseEntity<User> getUserById(@Valid @PathVariable("id") long id) {
    User user = userRepository.findById(id)
      .orElseThrow(() -> new ResourceAccessException("Not found User with id = " + id));
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @GetMapping("user/email/{email}")
  public ResponseEntity<User> getUserByEmail(@Valid @PathVariable("email") String email) {
    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new ResourceAccessException("Not found User with id = " + email));
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @PostMapping("user/subscribe")
  public ResponseEntity<Category> subscribeCategory(@Valid @RequestBody Subscribe subscribe) {
    String email = subscribe.getEmail();
    String availableCategory = subscribe.getAvailableCategory();

    Category category = userRepository.findByEmail(email).map(user -> {

      if (availableCategory != null) {
        Category _category = categoryRepository.findByName(availableCategory)
          .orElseThrow(() -> new ResourceAccessException("Not found Category with name = " + availableCategory));
        // TODO : SubscribedCategoryMapper
        _category.setStartDate(LocalDate.now());
        user.addCategory(_category);
        userRepository.save(user);
        return _category;
      }
      return null;
    }).orElseThrow(() -> new ResourceAccessException("Not found User with email = " + availableCategory));
    return new ResponseEntity<>(category, HttpStatus.OK);
  }

  @PostMapping("user/share")
  public ResponseEntity<Category> shareCategory(@Valid @RequestBody Share share) {
    String email = share.getEmail();
    String customer = share.getEmail();
    String availableCategory = share.getAvailableCategory();

    Category _category = categoryRepository.findByName(availableCategory)
      .orElseThrow(() -> new ResourceAccessException("Not found Category with name = " + availableCategory));

    Subscribe subscribeEmail = new Subscribe();
    subscribeEmail.setEmail(email);
    // TODO : AvailableCategoryMapper
    subscribeEmail.setAvailableCategory(availableCategory);
    subscribeCategory(subscribeEmail);

    Subscribe subscribeCustomer = new Subscribe();
    subscribeCustomer.setEmail(customer);
    subscribeCustomer.setAvailableCategory(availableCategory);
    subscribeCategory(subscribeCustomer);

    return new ResponseEntity<>(_category, HttpStatus.OK);
  }
}