package com.sogeti.filmland.controller;

import com.sogeti.filmland.entity.Category;
import com.sogeti.filmland.entity.Subscribe;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping
public class CategoryController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @GetMapping("categories")
  public ResponseEntity<List<Category>> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();
    if (categories.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(categories, HttpStatus.OK);
    }
  }

  @GetMapping("category/id/{id}")
  public ResponseEntity<Category> getCategoryById(@Valid @PathVariable("id") long id) {
    Category category = categoryRepository.findById(id)
      .orElseThrow(() -> new ResourceAccessException("Not found Category with id = " + id));
    return new ResponseEntity<>(category, HttpStatus.OK);
  }

  @GetMapping("category/name/{name}")
  public ResponseEntity<Category> getCategoryByName(@Valid @PathVariable("name") String name) {
    Category category = categoryRepository.findByName(name)
      .orElseThrow(() -> new ResourceAccessException("Not found Category with id = " + name));
    return new ResponseEntity<>(category, HttpStatus.OK);
  }

  @GetMapping("categories/email/{email}")
  public ResponseEntity<CategoryWrapper> getCategoriesByUserMail(@Valid @PathVariable("email") String email) {
    long userId = userRepository.findByEmail(email).get().getId();
    CategoryWrapper categoryWrapper = new CategoryWrapper();
    List<Category> allCategories = categoryRepository.findAll();
    List<Category> subscribedCategories = categoryRepository.findCategoriesByUsersId(userId);
    List<Category> availableCategories = allCategories
      .stream()
      .filter(category -> allCategories.contains(category))
      .collect(Collectors.toList());

    categoryWrapper.setAvailableCategories(availableCategories);
    categoryWrapper.setSubscribedCategories(subscribedCategories);

    System.out.println(categoryWrapper);

    return new ResponseEntity<>(categoryWrapper, HttpStatus.OK);
  }

  @GetMapping("categories/subscribed/{email}")
  public ResponseEntity<List<Category>> getSubscribedCategoriesByUserMail(@Valid @PathVariable("email") String email) {
    long userId = userRepository.findByEmail(email).get().getId();
    List<Category> categories = categoryRepository.findCategoriesByUsersId(userId);
    if (categories.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(categories, HttpStatus.OK);
    }
  }
}
