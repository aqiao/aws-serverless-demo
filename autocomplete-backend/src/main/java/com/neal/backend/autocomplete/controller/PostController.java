package com.neal.backend.autocomplete.controller;

import com.neal.backend.autocomplete.model.ApiResult;
import com.neal.backend.autocomplete.model.Post;
import com.neal.backend.autocomplete.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/post")
public class PostController {
  @Autowired
  private PostService postService;

  @GetMapping
  public ApiResult findAll(){
    try{
      Iterable<Post> posts=postService.findAll();
      return new ApiResult(posts);
    }catch (Exception e){
      return new ApiResult("404",e.getMessage());
    }
  }

  @GetMapping(value = "title/{prefix}")
  public ApiResult findByTitlePrefix(@PathVariable String prefix){
    try{
      Iterable<Post> posts=postService.findByTitlePrefix(prefix);
      return new ApiResult(posts);
    }catch (Exception e){
      return new ApiResult("503",e.getMessage());
    }
  }
}
