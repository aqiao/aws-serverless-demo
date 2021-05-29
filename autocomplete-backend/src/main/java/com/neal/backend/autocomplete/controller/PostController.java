package com.neal.backend.autocomplete.controller;

import com.neal.backend.autocomplete.model.ApiResult;
import com.neal.backend.autocomplete.model.Post;
import com.neal.backend.autocomplete.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/post")
public class PostController {
  @Autowired
  private PostService postService;

  @RequestMapping
  public ApiResult findAll(){
    try{
      Iterable<Post> posts=postService.findAll();
      return new ApiResult(posts);
    }catch (Exception e){
      return new ApiResult("404",e.getMessage());
    }
  }
}
