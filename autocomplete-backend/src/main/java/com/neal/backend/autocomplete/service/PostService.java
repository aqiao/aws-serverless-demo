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

  public Iterable<Post> findByTitlePrefix(String prefix){
    if(prefix==null||prefix.length()==0)
      return null;
    return postRepository.findByTitlePrefix(prefix);
  }
}