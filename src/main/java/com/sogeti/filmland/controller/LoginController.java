package com.sogeti.filmland.controller;

import com.sogeti.filmland.entity.User;
import com.sogeti.filmland.model.Response;
import com.sogeti.filmland.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("")
public class LoginController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping("login")
  public ResponseEntity<Response> loginUser(@Valid @RequestBody User user) {
    Response response = new Response();

    if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
      response.setStatus("Login failed");
      response.setMessage("User " + user.getEmail() + " is absent");
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    User _user = userRepository.findByEmail(user.getEmail()).get();

    if (user.getEmail().equals(_user.getEmail()) && user.getPassword().equals(_user.getPassword())) {
      response.setStatus("Login successful");
      response.setMessage("User " + user.getEmail() + " is accepted");
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      response.setStatus("Login failed");
      response.setMessage("User " + user.getEmail() + " is rejected");
      return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }
  }
}
