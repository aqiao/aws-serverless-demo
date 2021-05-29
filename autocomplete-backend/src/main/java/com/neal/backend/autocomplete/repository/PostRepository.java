package com.neal.backend.autocomplete.repository;

import com.neal.backend.autocomplete.model.Post;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface PostRepository extends CrudRepository<Post,String> {
}