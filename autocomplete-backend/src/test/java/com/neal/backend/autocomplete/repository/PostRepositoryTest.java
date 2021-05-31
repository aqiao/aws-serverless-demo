package com.neal.backend.autocomplete.repository;

import com.neal.backend.autocomplete.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {
  @Autowired
  private PostRepository repository;

  @Test
  public void testSave(){
    Post post=new Post();
    post.setId(String.valueOf(System.currentTimeMillis()));
    post.setTitle("Mysql 从删库到跑路");
    post.setContent("Mysql 从删库到跑路");
    post.setLastModified("2021-05-30");
    post.setSearchCount(10L);
    repository.save(post);
  }

  @Test
  public void testTitlePrefix(){
    repository.findByTitlePrefix("mysql");
  }
}
