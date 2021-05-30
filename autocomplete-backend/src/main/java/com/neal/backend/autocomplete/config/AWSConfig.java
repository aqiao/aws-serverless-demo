package com.neal.backend.autocomplete.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
//@EnableDynamoDBRepositories(basePackages = "com.neal.backend.autocomplete.repository")
public class AWSConfig {
  //  @Value("${amazon.dynamodb.endpoint}")
//  private String amazonDynamoDbEndpoint;
//  @Value("AKIAZ2I4VD2GC4TRGKXD")
//  private String amazonAWSAccessKey;
//  @Value("eTOSfT7f+7HcZcmLpriZKAGvgn5bxPxOccG1j1Bb")
//  private String amazonAWSSecretKey;
//  @Value("${amazon.aws.region}")
//  private String amazonAWSRegion;

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
//  public AWSCredentialsProvider amazonAWSCredentialsProvider() {
//    return new AWSStaticCredentialsProvider(amazonAWSCredentials());
//  }
//
//  @Bean
//  public AWSCredentials amazonAWSCredentials() {
//    return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
//  }
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
//  @Bean
//  public AmazonDynamoDB amazonDynamoDB() {
//    return AmazonDynamoDBClientBuilder.standard().withCredentials(amazonAWSCredentialsProvider())
//        .withRegion(Regions.US_EAST_1).build();
//  }
//  @Bean
//  public DynamoDbEnhancedClient dynamoDbEnhancedClient(){
//    DynamoDbClient dbClient = DynamoDbClient.builder()
//        .region(Region.US_EAST_1)
//        .build();
//    return DynamoDbEnhancedClient.builder().dynamoDbClient(dbClient).build();
//  }

  @Bean
  public DynamoDbClient dynamoDbClient(){
    return DynamoDbClient.builder()
        .region(Region.US_EAST_1)
        .build();
  }
}

