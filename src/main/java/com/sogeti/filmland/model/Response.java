package com.sogeti.filmland.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Response {

  private String status;
  private String message;
}
