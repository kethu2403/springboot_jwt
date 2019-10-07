
  
  

# Spring Boot Application - Jumbo

  

## Stack Used

  

![](https://img.shields.io/badge/java_8-✓-blue.svg) ![](https://img.shields.io/badge/spring_boot-✓-blue.svg) ![](https://img.shields.io/badge/H2Db-✓-blue.svg) ![](https://img.shields.io/badge/jwt-✓-blue.svg) ![](https://img.shields.io/badge/swagger_2-✓-blue.svg) ![](https://img.shields.io/badge/liquibase-✓-blue.svg)

  

## Running the application locally

  
  

For building and running the application you need:

  

-  [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

-  [Maven 3](https://maven.apache.org)

-  [GitBash](https://git-scm.com/downloads)

-  [Liquibase](https://www.liquibase.org/quickstart.html) (In Dependencies)

  
  

1. Make sure you have [Java 8](https://www.java.com/download/) and [Maven](https://maven.apache.org) installed

  

2. Clone the project repository from the below url:

  

`git@github.com:HexaInnovLab/Jumbo2.0-.git`

  

or

  

Run the below command in git bash to clone the repository:

```shell

git clone git@github.com:HexaInnovLab/Jumbo2.0-.git

```

  
  
  

3. Package the application using maven command below for running the test cases with test coverage check:

  

```shell

mvn package

```

  

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.hexaware.jumbo.Application` class from your IDE.


You can start the springboot application once packaged with below command:

```shell

java -jar 'jarname' --data.source.url=jdbc:h2:file:<File Path>;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE

```
or

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

  

```shell

mvn spring-boot:run -Dspring-boot.run.arguments="jdbc:h2:file:<File Path>;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"

```

4. After running the application navigate to `http://localhost:8080/swagger-ui.html`in your browser to check the existing Api's in the App