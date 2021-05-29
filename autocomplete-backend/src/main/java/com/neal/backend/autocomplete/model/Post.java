package com.neal.backend.autocomplete.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import java.sql.Date;

@Data
@DynamoDBTable(tableName = "Post")
public class Post {
  @DynamoDBHashKey(attributeName = "Id")
  @DynamoDBAutoGeneratedKey
  private String id;

  @DynamoDBAttribute(attributeName = "Title")
  private String title;

  @DynamoDBAttribute(attributeName = "Content")
  private String content;

  @DynamoDBAttribute(attributeName = "LostModify")
  private String lastModified;

  @DynamoDBAttribute(attributeName = "ViewNumber")
  private Long searchCount;
}
