package com.sogeti.filmland.repository;

import com.sogeti.filmland.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  // TODO : add query
  Optional<User> findByEmail(String email);
  List<User> findUsersByCategoriesId(Long categoryId);
}
