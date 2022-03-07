package com.sogeti.filmland.repository;

import com.sogeti.filmland.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

  final long ID = 1L;
  final String MAIL = "user1@mail.io";
  final String PASSWORD = "pass1";

  @Autowired
  private UserRepository userRepository;

  @Test
  void testFindAll() {
    List<User> userList = userRepository.findAll();

    assertThat(userList.get(0).getId()).isEqualTo(ID);
    assertThat(userList.get(0).getEmail()).isEqualTo(MAIL);
    assertThat(userList.get(0).getPassword()).isEqualTo(PASSWORD);
  }

  @Test
  void testFindById() {
    User user = userRepository.findById(ID).get();

    assertThat(user.getId()).isEqualTo(ID);
    assertThat(user.getEmail()).isEqualTo(MAIL);
    assertThat(user.getPassword()).isEqualTo(PASSWORD);
  }

  @Test
  void testFindByEmail() {
    User user = userRepository.findByEmail(MAIL).get();

    assertThat(user.getId()).isEqualTo(ID);
    assertThat(user.getEmail()).isEqualTo(MAIL);
    assertThat(user.getPassword()).isEqualTo(PASSWORD);
  }

  @Test
  void testSaveUser() {
    User user = new User();
    user.setEmail("user@mail.io");
    user.setPassword("pass");

    User data = userRepository.save(user);

    assertThat(data.getEmail()).isEqualTo(user.getEmail());
  }
}
