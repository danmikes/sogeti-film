package com.sogeti.filmland.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Subscription {
  private String email;
  private String availableCategory;
}
