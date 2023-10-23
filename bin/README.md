# Spring Boot, SQL Server and Queries example
- [Description of the project](#description-of-the-project)
- [Preconditions](#preconditions)
- [Project structure](#project-structure)
- [Project functioning](#project-functioning)
  - [Models](#models)
  - [Repository](#repository)
  - [Controller](#controller)
  - [application.properties](#application.properties)
  - [Execution](#execution)
- [Italian version](#esempio-di-spring-boot-sql-server-e-queries)

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


***

# Esempio di Spring Boot, SQL Server e queries
- [Descrizione del progetto](#descrizione-del-progetto)
- [Precondizioni](#precondizioni)
- [Struttura del progetto](#struttura-del-progetto)
- [Funzionamento del progetto](#funzionamento-del-progetto)
  - [Modelli](#modelli)
  - [Repository](#repository)
  - [Controller](#controller)
  - [application.properties](#application.properties)
  - [Esecuzione](#esecuzione)
- [Versione inglese](#spring-boot-sql-server-and-queries-example)

***

## Descrizione del progetto
Questo progetto è stato realizzato per dimostrare vari esempi di query su SQL e su come realizzare il join tra due modelli.  
Le tecnologie utilizzate in questo progetto sono:
- JPA
- Hibernate
- SpringBoot
- SQL Server
- SQL
- JPQL

## Precondizioni  
- Avere installato SQL Server sul proprio pc;
- E' necessario creare un'utenza che possa accedere ai vari database, assicurandogli i permessi necessari;
- Creare le tabelle, specificando le eventuali chiavi esterne nelle tabelle, se si sceglie la strategia manuale e di non demandare tutto a JPA.
- Avere installato Postman, o un software simile, sul proprio pc.

## Struttura del progetto  
In questo progetto sono presenti due entità o `modelli`:
- Speaker.java
- Tutorial.java 
  
Ogni modello ha la sua `repository`, che estende `JpaRepository`:
- SpeakerRepository.java
- TutorialRepository.java

E' presente `Controller.java` per mappare gli endpoint.   

Infine è presente un `application.properties` per le varie proprietà del progetto, come: indirizzi dei database, username e password, ...   

## Funzionamento del progetto
### Modelli  
I modelli sono rappresentati dalle tabelle che sono presenti su SQL Server. All'interno di ogni modello devono essere presenti gli attributi necessari, che saranno poi le colonne delle tabelle, i costruttori e i metodi getter e setter.  
Ogni attributo può presentare delle annotazioni per specificare dei vincoli su come dovranno essere create le colonne delle tabella da parte di JPA.  
Per effettuare il join tra due modelli è necessario specificare almeno in uno dei due un oggetto rappresentante il secondo, annotandolo con il tipo di relazione che deve essere presente tra di essi (1:1, 1:molti, ...) e specificando il vincolo tra gli attributi dei due modelli, ovvero la chiave esterna. (es: In Speaker.java è presente la lista di tutti i tutorial tenuti dallo stesso, annotato con `@OneToMany`)

### Repository
Ogni modello avrà il suo repository che dovrà andare ad estendere `JpaRepository<T, ID>`.  
Nel repository possono essere definiti dei metodi ed annotarli con `@Query` per definire delle query da eseguire sulla tabella ogni volta che quel determinato metodo verrà richiamato. Queste quey possono essere definite nel repository stesso o nel modello.  
Le query possono essere scritte in SQL, lavorando sulle tabelle, o in JPQL, lavorando sul modello stesso.  
E' possibile definire dei metodi senza dichiarare la query esplicitamente. JPA, in base al nome del metodo stesso, andrà a creare automaticamente la query. Per esempio, il modello Tutorial presenta un attributo title. Nel suo repository si può semplicemente dichiarare il metodo `List<Tutorial> findByTitleContaining(String title);` e JPA restituirà tutti gli oggetti Tutorial il cui titolo contiene il parametro title.  
Di default ogni repository avrà già dei metodi che eseguono delle query standard sulle tabelle, come: `findById()`, `findAll()`, `deleteById()`, ...  

### Controller
Il file `controller.java` al suo interno presenta tutte le mappature dei vari endpoint necessari per il progetto. All'interno di ogni metodo sono espicitate le varie azioni da eseguire quando viene richiamato quel determinato endpoint.  
Utilizzando i repository è possibile eseguire delle azioni sulle varie tabelle.    

### application.properties
In questo file sono presenti le varie informazioni per instaurare la connessione al database e altre iniformazioni aggiuntive.  
Per le connessioni è necessario specificare:
- jdbcUrl (es: jdbc:sqlserver://ip;databaseName=examples;encrypt=true;trustServerCertificate=true;  
- username
- password
- driverClassName (es: com.microsoft.sqlserver.jdbc.SQLServerDriver)  

Attraverso la proprietà spring.jpa.hibernate.ddl-auto è possibile definire come vengono create e aggiornate le tabelle. E' possibile demandare tutto a JPA, che si affiderà alle varie annotazioni sui parametri, usando update; altrimenti con none ci si dovrà occupare personalmente della creazione e della manutenzione delle tabelle.  
   
### Esecuzione 
Per eseguire il progetto basta eseguire un `Run As -> Spring Boot App`. Se la connessione non viene instaurata, il progetto non sarà in esecuzione.  
Avviato il progetto aprire Postman e richiamare i vari endpoint, passando gli eventuali parametri necessari all'esecuzione dello stesso.  
Verificare sulle varie tabelle se siano presenti i dati ricevuti come parametro ed eventualmente elaborati.  
