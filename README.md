## Introduction
### Background
This project aims to practice AWS serverless, a modern, mainstream and cloud native technology. It's based on N-tier architecture style:
* Frontend: HTML, Jquery autocomplete widget and Ajax
* Proxy layer: AWS Lambda, API Gateway
* Service layer: Springboot
* Persistency layer: Dynamodb

## Architecture
### Development view
**Development architecture diagram**
![development architecture diagram](https://github.com/dikers/serverless/blob/master/doc/picture/7.png?raw=true)

**Why serverless**

A brief lookback on cloud compute evolution

**Development architecture diagram**
<div align=center> 
<img src='https://github.com/aqiao/aws-serverless-demo/blob/main/resouces/aws-demo-serverless-evolution.png' alt='cloud compute evolution'/>
</div>
Reference: https://saasmetrics.co/iaas-paas-faas-saas-understanding-the-cloud-computing-pyramid/

**Strengths**

* Serverless management
* Scalable to extend
* Pay as you go
* Reduce maintenance effort
* Automatic high availability implementation

### Infrastructure view
From serverless architecture point of view, the infrastructure view is transparent for developers, it means dev can focus on business implementation more easily than ever before.
### DevOps view
Use AWS Amplify to setup a one-click release
### Security view
* SSL data transport
* Data encryption on S3 and dynamodb

## Implementation
### Create IAM user and git repo
The process is quite easy, only highlight some key steps here:

1 Install AWS Cli and set credential for local development
```
$ aws configure
AWS Access Key ID [None]: AKIAIOSFODNN7EXAMPLE
AWS Secret Access Key [None]: wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
Default region name [None]: us-west-2
Default output format [None]: json
```
2 Bind public key of git account to AWS IAM account
Upload public key to generate a SSH key ID
for this demo I created IAM account `developer` and `https://github.com/aqiao/aws-serverless-demo` repo. 
> Note: Please DO NOT expose access key and secret key on configuration file like `application.properties` or `application.yml`, or AWS will restrict your IAM account permission.

![credential](https://github.com/aqiao/aws-serverless-demo/blob/main/resouces/credentials.png)

After performing above two steps, I created `developer` as IAM account, and `https://github.com/aqiao/aws-serverless-demo` as git repo

### Frontend
AWS Amplify is a set of tools and services that can be used together or on their own, to help front-end web and mobile developers build scalable full stack applications, powered by AWS. With Amplify, you can configure app backends and connect your app in minutes, deploy static web apps in a few clicks, and easily manage app content outside the AWS console.

Amplify supports popular web frameworks including JavaScript, React, Angular, Vue, Next.js, and mobile platforms including Android, iOS, React Native, Ionic, Flutter. Get to market faster with AWS Amplify.

How it works
1. Create App
Open amplify console and create app, then select the web file source. In my case, i choose git repo

![amplify-1](https://github.com/aqiao/aws-serverless-demo/blob/main/resouces/amplify-1.png)

2. Connect git repo and branch
Specify git repo and branch

![amplify-2](https://github.com/aqiao/aws-serverless-demo/blob/main/resouces/amplify-2.png)

3. Specify the basepath to build (Optional)
Exclude the unnecessary files to accelerate build and deployment

![amplify-3](https://github.com/aqiao/aws-serverless-demo/blob/main/resouces/amplify-3.png)

4. Automatic deployment

Once you commit your code, Amplify will detect the changes and trigger deployment automatically
![amplify-4](https://github.com/aqiao/aws-serverless-demo/blob/main/resouces/amplify-4.png)

5. Case Show
Do some changes on index.html

### Service Layer
AWS Lambda
Write lambda function based on Springboot
1. Add dependency
```
    <dependency>
      <groupId>com.amazonaws.serverless</groupId>
      <artifactId>aws-serverless-java-container-springboot2</artifactId>
      <version>1.5.2</version>
    </dependency>
```
2. Implement `RequestStreamHandler` interface

```
public class StreamLambdaHandler implements RequestStreamHandler {

  private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
  static{
    try{
      handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(AutoCompleteApp.class);
    }catch (ContainerInitializationException e){
      // if we fail here. We re-throw the exception to force another cold start
      e.printStackTrace();
      throw new RuntimeException("Could not initialize Spring Boot application",e);
    }
  }
  @Override
  public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
    handler.proxyStream(input, output, context);
  }
}
```
3. Involve DynamoDb dependencies

```
  <dependencyManagement>
    <dependencies>
      <!-- https://mvnrepository.com/artifact/software.amazon.awssdk/bom -->
      <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>bom</artifactId>
        <version>2.16.74</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>software.amazon.awssdk</groupId>
      <artifactId>dynamodb</artifactId>
    </dependency>
    ...
  </dependencies>
```
4. Make sure the package includes all the dependencies
```
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>3.1.0</version>
        <configuration>
          <createDependencyReducedPom>false</createDependencyReducedPom>
        </configuration>
        <executions>
          <execution>
            <phase>package</phase>
            <goals>
              <goal>shade</goal>
            </goals>
          </execution>
        </executions>
      </plugin>

    </plugins>
  </build>
```
5. Register the DynamoDBClient as a bean
```
@Configuration
public class AWSConfig {

  @Bean
  public DynamoDbClient dynamoDbClient(){
    return DynamoDbClient.builder()
        .region(Region.US_EAST_1)
        .build();
  }
}
```
6. Add CURD method with `DynamoDbClient`
find all
```
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
```
find begin with
```
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
```
In my case ,the lambda function is `autocomplete`
### Proxy Layer
API gateway is running as proxy layer
During create API gateway for restapi, DO remember enable CORS, or the Ajax request will be blocked
![api gate 1](https://github.com/aqiao/aws-serverless-demo/blob/main/resouces/api-gw-1.png)

![api-gateway-2](https://github.com/aqiao/aws-serverless-demo/blob/main/resouces/api-gw-2.png)

![api gateway -3](https://github.com/aqiao/aws-serverless-demo/blob/main/resouces/api-gw-3.png)


## Final result
https://main.d2sujkt3dzw7ch.amplifyapp.com/

![final effect](https://github.com/aqiao/aws-serverless-demo/blob/main/resouces/final-effect.png)

