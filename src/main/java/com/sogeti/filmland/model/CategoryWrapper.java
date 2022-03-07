package com.sogeti.filmland.model;

import com.sogeti.filmland.entity.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryWrapper {

  private List<Category> availableCategories;
  private List<Category> subscribedCategories;
}
