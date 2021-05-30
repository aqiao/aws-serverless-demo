package com.neal.backend.autocomplete.model;

import lombok.Data;

@Data
public class Post {
  private String id;
  private String title;
  private String content;
  private String lastModified;
  private Long searchCount;
}
