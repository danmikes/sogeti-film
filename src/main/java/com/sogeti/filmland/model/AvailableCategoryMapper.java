package com.sogeti.filmland.model;

import com.sogeti.filmland.entity.Category;

public interface AvailableCategoryMapper {

  default AvailableCategory toAvailableCategory(Category category) {
    AvailableCategory availableCategory = new AvailableCategory();
    availableCategory.setName(category.getName());
    availableCategory.setAvailableContent(category.getAvailableContent());
    availableCategory.setPrice(category.getPrice());

    return availableCategory;
  }
}
