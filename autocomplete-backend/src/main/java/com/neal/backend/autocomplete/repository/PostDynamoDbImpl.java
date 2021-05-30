package com.neal.backend.autocomplete.repository;

import com.neal.backend.autocomplete.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ExecuteStatementRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

@Repository
public class PostDynamoDbImpl implements PostRepository{

  @Autowired
  private DynamoDbClient dbClient;

  @Override
  public void save(Post post) {
    HashMap<String, AttributeValue> itemValues = new HashMap<>();
    itemValues.put("Id", AttributeValue.builder().s(post.getId()).build());
    itemValues.put("Title", AttributeValue.builder().s(post.getTitle()).build());
    itemValues.put("Content", AttributeValue.builder().s(post.getTitle()).build());
    itemValues.put("LostModify", AttributeValue.builder().s(post.getTitle()).build());
    itemValues.put("ViewNumber", AttributeValue.builder().s(post.getTitle()).build());
    PutItemRequest putItemRequest=PutItemRequest.builder()
        .tableName("Post")
        .item(itemValues).build();
    dbClient.putItem(putItemRequest);
  }

  @Override
  public void remove(String id) {

  }

  @Override
  public Iterable<Post> findAll() {
    String sql="select * from Post";
    ExecuteStatementRequest executeStatementRequest= ExecuteStatementRequest.builder().statement(sql).build();
    List<Map<String,AttributeValue>> results=dbClient.executeStatement(executeStatementRequest).items();
    List<Post> posts=new ArrayList<>();
    for(Map<String,AttributeValue> map: results){
      posts.add(bind(map));
    }
    return posts;
  }

  @Override
  public Iterable<Post> findByTitlePrefix(String prefix) {

    List<AttributeValue> values=new ArrayList<>(1);
    values.add(AttributeValue.builder().s(prefix).build());
    String sql="select * from Post where begins_with(Title,?)";
    ExecuteStatementRequest executeStatementRequest=ExecuteStatementRequest.builder()
        .statement(sql)
        .parameters(values).build();
    List<Map<String,AttributeValue>> results=dbClient.executeStatement(executeStatementRequest).items();
    List<Post> posts=new ArrayList<>();
    for(Map<String,AttributeValue> map: results){
      posts.add(bind(map));
    }
    return posts;
  }

  private Post bind(Map<String,AttributeValue> map){
    Post post=new Post();
    if(map==null) return null;
    if(map.containsKey("Id")) post.setId(map.get("Id").s());
    if(map.containsKey("Title")) post.setTitle(map.get("Title").s());
    if(map.containsKey("Content")) post.setContent(map.get("Content").s());
    if(map.containsKey("LostModify")) post.setLastModified(map.get("LostModify").s());
    Long viewNumber=map.get("ViewNumber").n()==null?0L:Long.parseLong(map.get("ViewNumber").n());
    if(map.containsKey("ViewNumber")) post.setSearchCount(viewNumber);
    return post;
  }
}
