package com.sogeti.filmland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table
public class Category {

  @Id
  @Column(name = "category_id")
  @GeneratedValue(strategy = IDENTITY)
  private long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private double price;

  @Column
  @JsonInclude(Include.NON_NULL)
  private int availableContent;

  @Column
  @JsonInclude(Include.NON_NULL)
  private int remainingContent;

  @Column
  @JsonInclude(Include.NON_NULL)
  private LocalDate startDate;

  @ManyToMany
  @JsonInclude(Include.NON_NULL)
  private Set<User> users = new HashSet<>();
}
