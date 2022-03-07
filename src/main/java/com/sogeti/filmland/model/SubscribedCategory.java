package com.sogeti.filmland.model;

import lombok.Data;

@Data
public class SubscribedCategory {
  String name = null;
  int remainingContent = 0;
  double price = 0;
}
