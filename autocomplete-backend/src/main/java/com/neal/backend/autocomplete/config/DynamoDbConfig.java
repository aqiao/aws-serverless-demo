package com.neal.backend.autocomplete.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.neal.backend.autocomplete.repository")
public class DynamoDbConfig {
  //  @Value("${amazon.dynamodb.endpoint}")
//  private String amazonDynamoDbEndpoint;
  @Value("${amazon.aws.accesskey}")
  private String amazonAWSAccessKey;
  @Value("${amazon.aws.secretkey}")
  private String amazonAWSSecretKey;
  @Value("${amazon.aws.region}")
  private String amazonAWSRegion;

  //  @Bean
//  public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider awsCredentialsProvider){
//    AmazonDynamoDB amazonDynamoDB= AmazonDynamoDBClientBuilder.standard()
//        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDbEndpoint,amazonAWSRegion))
//        .withCredentials(awsCredentialsProvider).build();
//    return amazonDynamoDB;
//  }
//
//  @Bean
//  public AWSCredentialsProvider awsCredentialsProvider(){
//    return new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey,amazonAWSSecretKey));
//  }
  public AWSCredentialsProvider amazonAWSCredentialsProvider() {
    return new AWSStaticCredentialsProvider(amazonAWSCredentials());
  }

  @Bean
  public AWSCredentials amazonAWSCredentials() {
    return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
  }
  //
//  @Bean
//  public DynamoDBMapperConfig dynamoDBMapperConfig() {
//    return DynamoDBMapperConfig.DEFAULT;
//  }
//
//  @Bean
//  public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig config) {
//    return new DynamoDBMapper(amazonDynamoDB, config);
//  }
//
  @Bean
  public AmazonDynamoDB amazonDynamoDB() {
    return AmazonDynamoDBClientBuilder.standard().withCredentials(amazonAWSCredentialsProvider())
        .withRegion(Regions.US_EAST_1).build();
  }
}
