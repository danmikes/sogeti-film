package com.sogeti.filmland.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table
public class User {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = IDENTITY)
  private long id;

  @Column(nullable = false)
  @Pattern(regexp = "^(.+)@(.+)$")
  private String email;

  @Column(nullable = false)
  private String password;

  @ManyToMany
  @JoinTable(
    name="user_category",
    joinColumns = {
      @JoinColumn(name = "user_id")
    },
    inverseJoinColumns = {
      @JoinColumn(name = "category_id")
    }
  )
  private Set<Category> categories = new HashSet<>();

  public void addCategory(Category category) {
    if (!this.categories.contains(category)) {
      category.getUsers().add(this);
    }
  }

  public void removeCategory(Long categoryId) {
    Category category = this.categories.stream().filter(c -> c.getId() == categoryId).findFirst().orElse(null);
    if (category != null) this.categories.remove(category);
    category.getUsers().remove(this);
  }
}
