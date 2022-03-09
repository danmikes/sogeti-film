package com.sogeti.filmland.controller;

import com.sogeti.filmland.entity.Category;
import com.sogeti.filmland.entity.User;
import com.sogeti.filmland.model.CategoryWrapper;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class CategoryController {

  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;

  public CategoryController(UserRepository userRepository, CategoryRepository categoryRepository) {
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
  }

  @GetMapping("categories")
  public ResponseEntity<List<Category>> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();
    return ResponseEntity.ok(categories);
  }

  @GetMapping("category/id/{id}")
  public ResponseEntity<Category> getCategoryById(@PathVariable("id") long id) {
    return categoryRepository.findById(id)
      .map(ResponseEntity::ok)
      .orElseThrow();
  }

  @GetMapping("category/name/{name}")
  @Pattern(regexp = "/^[a-zA-Z0-9]+([_ -]?[a-zA-Z0-9])*$/")
  public ResponseEntity<Category> getCategoryByName(@PathVariable("name") String name) {
    return categoryRepository.findByName(name)
      .map(ResponseEntity::ok)
      .orElseThrow();
  }

  @GetMapping("categories/email/{email}")
  @Pattern(regexp = "^(.+)@(.+)$")
  public ResponseEntity<CategoryWrapper> getCategoriesByUserMail(@PathVariable("email") String email) {
    long userId = userRepository.findByEmail(email)
      .map(User::getId)
      .orElseThrow();
    CategoryWrapper categoryWrapper = new CategoryWrapper();
    List<Category> allCategories = categoryRepository.findAll();
    List<Category> subscribedCategories = categoryRepository.findCategoriesByUsersId(userId);
    List<Category> availableCategories = allCategories.stream()
      .filter(allCategories::contains)
      .collect(Collectors.toList());

    categoryWrapper.setAvailableCategories(availableCategories);
    categoryWrapper.setSubscribedCategories(subscribedCategories);

    return new ResponseEntity<>(categoryWrapper, HttpStatus.OK);
  }

  @GetMapping("categories/subscribed/{email}")
  @Pattern(regexp = "^(.+)@(.+)$")
  public ResponseEntity<List<Category>> getSubscribedCategoriesByUserMail(@PathVariable("email") String email) {
    long userId = userRepository.findByEmail(email)
      .map(User::getId)
      .orElseThrow();
    List<Category> categories = categoryRepository.findCategoriesByUsersId(userId);
    return ResponseEntity.ok(categories);
  }
}
