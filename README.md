# Spring Boot, SQL Server and Queries example
- [Description of the project](#description-of-the-project)
- [Preconditions](#preconditions)
- [Project structure](#project-structure)
- [Project functioning](#project-functioning)
  - [Models](#models)
  - [Repositories](#repositories)
  - [Controllers](#controllers)
  - [application.properties](#application-properties)
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
There are three entities or `models` in this project:  
- Speaker.java
- Talk.java
- Room.java
  
Each model has its own `repository`, which extends `JpaRepository`:
- SpeakerRepository.java
- TalkRepository.java
- RoomRepository.java

Each model has its own `controller` to map its endpoints:
- SpeakerController.java
- TalkController.java
- RoomController.java
 

Finally there is an `application.properties` for the various project properties, such as: database addresses, username and password, ...

## Project functioning
### Models
Models are represented by tables that exist on SQL Server. Within each model the necessary attributes must be present, which will then be the columns of the tables, the constructors, the getter and setter methods and any service methods.  
Each attribute can have annotations to specify constraints on how JPA should create table columns.  
To join two models it is necessary to understand which type of relationship there is between them (1:1, 1:many, ...), if it is unidirectional or bidirectional, and then specifying the constraint between the attributes of the two models, i.e. the foreign key. (**ex:** In **Speaker.java** there is the list of all the talks held by the same, annotated with `@OneToMany`).  

For the `@OneToMany` annotation and a unidirectional relationship it is necessary to:
- Annotate with `@OneToMany` the attribute inside the proper class (in this example is the attribute speakerTalks inside Speaker); 
- Annotate with `@JoinColumn` the attribute inside the proper class (in this example is the attribute speakerTalks inside Speaker); 
- Inside the `@JoinColumn` set the **name** attribute, that is the foreign key inside the other entity (in this example the column that will be generated on the _talks_ table will have the name _speaker\_id_). 

For the `@OneToMany` annotation and a bidirectional relationship it is necessary to:  
- Annotate with `@ManyToOne` the owning side of a bidirectional relationship, that is where the foreign key is;
- Annotate with `@JoinColumn` the attribute inside the owning side of a bidirectional relationship;
- Annotate with `@OneToMany` the inverse side of a bidirectional association;
- Inside the `@OneToMany` annotation set the **mappedBy** attribute, that is the name of the column that represent the foreign key on the owning side.  
An **example** is the one present inside the **Talk** and **Room** entities.

### Repositories
Each model will have its own repository which will have to extend `JpaRepository<T, ID>`.  
Methods can be defined in the repository and annotated with `@Query` to define queries to be executed on the table every time that particular method will be called. These queries can be defined in the repository itself or in the model.  
Queries can be written in SQL, working on the tables, or in JPQL, working on the model itself.  
It is possible to define methods without declaring the query explicitly. JPA, based on the name of the method itself, will automatically create the query. For example, the Talk template has a title attribute. In its repository you can simply declare the `List<Talks> findByTitleContaining(String title);` method and JPA will return all Talks objects whose title contains the title parameter.  
By default each repository will already have methods that perform standard queries on the tables, such as: `findById()`, `findAll()`, `deleteById()`, ...  

### Controllers
All the `controllers` classes have all the mappings of the various endpoints needed for the project. Within each method, the various actions to be performed when that particular endpoint is called are explained.
By using the repositories it is possible to perform actions on the various tables.

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
