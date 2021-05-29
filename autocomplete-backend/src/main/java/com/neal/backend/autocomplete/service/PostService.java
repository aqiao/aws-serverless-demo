package com.neal.backend.autocomplete.service;

import com.neal.backend.autocomplete.model.Post;
import com.neal.backend.autocomplete.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  @Autowired
  private PostRepository postRepository;

  public Iterable<Post> findAll(){
    return postRepository.findAll();
  }
}