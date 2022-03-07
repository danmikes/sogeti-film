package com.sogeti.filmland.repository;

import com.sogeti.filmland.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CategoryRepositoryTest {

  final long ID = 1L;
  final String NAME = "Dutch Films";
  final double PRICE = 4.0;

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  void testFindAll() {
    List<Category> categoryList = categoryRepository.findAll();

    assertThat(categoryList.get(0).getId()).isEqualTo(ID);
    assertThat(categoryList.get(0).getName()).isEqualTo(NAME);
    assertThat(categoryList.get(0).getPrice()).isEqualTo(PRICE);
  }

  @Test
  void testFindById() {
    Category category = categoryRepository.findById(ID).get();

    assertThat(category.getId()).isEqualTo(ID);
    assertThat(category.getName()).isEqualTo(NAME);
    assertThat(category.getPrice()).isEqualTo(PRICE);
  }

  @Test
  void testFindByName() {
    Category category = categoryRepository.findByName(NAME).get();

    assertThat(category.getId()).isEqualTo(ID);
    assertThat(category.getName()).isEqualTo(NAME);
    assertThat(category.getPrice()).isEqualTo(PRICE);
  }

  @Test
  void testSaveCategory() {
    Category category = new Category();
    category.setName("World Films");
    category.setPrice(5.0);

    Category data = categoryRepository.save(category);

    assertThat(data.getName()).isEqualTo(data.getName());
  }
}
