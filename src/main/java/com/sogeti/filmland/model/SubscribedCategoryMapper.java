package com.sogeti.filmland.model;

import com.sogeti.filmland.entity.Category;

public interface SubscribedCategoryMapper {

  default SubscribedCategory toSubscribedCategory(Category category) {
    SubscribedCategory subscribedCategory = new SubscribedCategory();
    subscribedCategory.setName(category.getName());
    subscribedCategory.setRemainingContent(category.getAvailableContent());
    subscribedCategory.setPrice(category.getPrice());

    return subscribedCategory;
  }
}
