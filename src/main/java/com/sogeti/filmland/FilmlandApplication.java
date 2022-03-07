package com.sogeti.filmland;

import com.sogeti.filmland.entity.Category;
import com.sogeti.filmland.entity.User;
import com.sogeti.filmland.repository.CategoryRepository;
import com.sogeti.filmland.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmlandApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(FilmlandApplication.class, args);
  }

  @Autowired
  UserRepository userRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Override
  public void run(String... args) {
    User user1 = new User();
    user1.setEmail("user1@mail.io");
    user1.setPassword("pass1");
    userRepository.save(user1);

    User user2 = new User();
    user2.setEmail("user2@mail.io");
    user2.setPassword("pass2");
    userRepository.save(user2);

    Category category1 = new Category();
    category1.setName("Dutch Films");
    category1.setPrice(4.0);
    category1.setAvailableContent(10);
    category1.setRemainingContent(category1.getAvailableContent());
    categoryRepository.save(category1);

    Category category2 = new Category();
    category2.setName("Dutch Series");
    category2.setPrice(6.0);
    category2.setAvailableContent(20);
    category2.setRemainingContent(category2.getAvailableContent());
    categoryRepository.save(category2);

    Category category3 = new Category();
    category3.setName("International Films");
    category3.setPrice(8.0);
    category3.setAvailableContent(5);
    category3.setRemainingContent(category3.getAvailableContent());
    categoryRepository.save(category3);
  }
}
