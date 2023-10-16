# Spring Boot, SQL Server and Queries example
- [Description of the project](#description-of-the-project)
- [Preconditions](#preconditions)
- [Project structure](#project-structure)
- [Project functioning](#project-functioning)
  - [Models](#models)
  - [Repositories](#repositories)
  - [Controllers](#controllers)
  - [application.properties](#application.properties)
  - [Execution](#execution)

***

## Description of the project
This project was created to demonstrate various examples of SQL queries and how to join two models.  
The technologies used in this project are:
- JPA
- Hibernate
- SpringBoot
- SQL Server
- SQL
- JPQL


## Preconditions  
- Having SQL Server installed on your pc;
- It is necessary to create a user that can access the various databases, ensuring the necessary permissions;
- Create the tables, specifying any foreign keys in the tables, if you choose the manual strategy and not to leave everything to JPA.
- Having Postman, or similar softwar, installed on your pc.


## Project structure
There are two entities or `models` in this project:  
- Speaker.java
- Tutorial.java
  
Each model has its own `repository`, which extends `JpaRepository`:
- SpeakerRepository.java
- TutorialRepository.java

There is `Controller.java` to map the endpoints.  

Finally there is an `application.properties` for the various project properties, such as: database addresses, username and password, ...

## Project functioning
### Models
Models are represented by tables that exist on SQL Server. Within each model the necessary attributes must be present, which will then be the columns of the tables, the constructors and the getter and setter methods.  
Each attribute can have annotations to specify constraints on how JPA should create table columns.  
To join two models it is necessary to specify in at least one of the two an object representing the second, annotating it with the type of relationship that must be present between them (1:1, 1:many, ...) and specifying the constraint between the attributes of the two models, i.e. the foreign key. (ex: In Speaker.java there is the list of all the tutorials held by the same, annotated with `@OneToMany`)  

### Repositories
Each model will have its own repository which will have to extend `JpaRepository<T, ID>`.  
Methods can be defined in the repository and annotated with `@Query` to define queries to be executed on the table every time that particular method will be called. These queries can be defined in the repository itself or in the model.  
Queries can be written in SQL, working on the tables, or in JPQL, working on the model itself.  
It is possible to define methods without declaring the query explicitly. JPA, based on the name of the method itself, will automatically create the query. For example, the Tutorial template has a title attribute. In its repository you can simply declare the `List<Tutorial> findByTitleContaining(String title);` method and JPA will return all Tutorial objects whose title contains the title parameter.  
By default each repository will already have methods that perform standard queries on the tables, such as: `findById()`, `findAll()`, `deleteById()`, ...  

### Controllers
The `controller.java` file inside has all the mappings of the various endpoints needed for the project. Within each method, the various actions to be performed when that particular endpoint is called are explained.
Using the repositories it is possible to perform actions on the various tables.

### application.properties
This file contains the various information to establish the connection to the database and other additional information.
For connections you must specify:
- jdbcUrl (ex: jdbc:sqlserver://ip;databaseName=examples;encrypt=true;trustServerCertificate=true;
- username
- password
- driverClassName (ex: com.microsoft.sqlserver.jdbc.SQLServerDriver)

Through the property spring.jpa.hibernate.ddl-auto it is possible to define how the tables are created and updated. It is possible to leave everything to JPA, which will rely on the various annotations on the parameters, using update; otherwise with none you will have to personally take care of the creation and maintenance of the tables.

### Execution
To run the project just do a `Run As -> Spring Boot App`. If the connection is not established, the project will not be running.  
Once the project has started, open Postman and recall the various endpoints, passing all the parameters necessary for its execution.  
Check on the database tables, if the data received as a parameter, and possibly processed, are present.
