package com.neal.backend.autocomplete.repository;

import com.neal.backend.autocomplete.model.Post;

public interface PostRepository {
  void save(Post post);
  void remove(String id);
  Iterable<Post> findAll();
  Iterable<Post> findByTitlePrefix(String prefix);
}