# Spring Boot, SQL Server and Queries example
- [Descrizione del progetto](#descrizione-del-progetto)
- [Precondizioni](#precondizioni)
- [Struttura del progetto](#struttura-del-progetto)
- [Funzionamento del progetto](#funzionamento-del-progetto)

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
- Avere installato SQL Server.
- E' necessario creare un'utenza che possa accedere ai vari database, assicurandogli i permessi necessari.  
- Creare le tabelle, specificando le eventuali chiavi esterne nelle tabelle, se si sceglie la strategia manuale e di non demandare tutto a JPA.
- Installare Postman.

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
**Modelli**  
I modelli sono rappresentati dalle tabelle che sono presenti su SQL Server. All'interno di ogni modello devono essere presenti gli attributi necessari, che saranno poi le colonne delle tabelle, i costruttori e i metodi getter e setter.  
Ogni attributo può presentare delle annotazioni per specificare dei vincoli su come dovranno essere create le colonne delle tabella da parte di JPA.  
Per effettuare il join tra due modelli è necessario specificare almeno in uno dei due un oggetto rappresentante il secondo, annotandolo con il tipo di relazione che deve essere presente tra di essi (1:1, 1:molti, ...) e specificando il vincolo tra gli attributi dei due modelli, ovvero la chiave esterna. (es: In Speaker.java è presente la lista di tutti i tutorial tenuti dallo stesso, annotato con `@OneToMany`)

**Repository**  
Ogni modello avrà il suo repository che dovrà andare ad estendere `JpaRepository<T, ID>`.  
Nel repository possono essere definiti dei metodi ed annotarli con `@Query` per definire delle query da eseguire sulla tabella ogni volta che quel determinato metodo verrà richiamato. Queste quey possono essere definite nel repository stesso o nel modello.  
Le query possono essere scritte in SQL, lavorando sulle tabelle, o in JPQL, lavorando sul modello stesso.  
E' possibile definire dei metodi senza dichiarare la query esplicitamente. JPA, in base al nome del metodo stesso, andrà a creare automaticamente la query. Per esempio, il modello Tutorial presenta un attributo title. Nel suo repository si può semplicemente dichiarare il metodo `List<Tutorial> findByTitleContaining(String title);` e JPA restituirà tutti gli oggetti Tutorial il cui titolo contiene il parametro title.  
Di default ogni repository avrà già dei metodi che eseguono delle query standard sulle tabelle, come: `findById()`, `findAll()`, `deleteById()`, ...  

**Controller**  
Il file `controller.java` al suo interno presenta tutte le mappature dei vari endpoint necessari per il progetto. All'interno di ogni metodo sono espicitate le varie azioni da eseguire quando viene richiamato quel determinato endpoint.  
Utilizzando i repository è possibile eseguire delle azioni sulle varie tabelle.    

**application.properties**  
In questo file sono presenti le varie informazioni per instaurare la connessione al database e altre iniformazioni aggiuntive.  
Per le connessioni è necessario specificare:
- jdbcUrl (es: jdbc:sqlserver://ip;databaseName=examples;encrypt=true;trustServerCertificate=true;  
- username
- password
- driverClassName (es: com.microsoft.sqlserver.jdbc.SQLServerDriver)  

Attraverso la proprietà spring.jpa.hibernate.ddl-auto è possibile definire come vengono create e aggiornate le tabelle. E' possibile demandare tutto a JPA, che si affiderà alle varie annotazioni sui parametri, usando update; altrimenti con none ci si dovrà occupare personalmente della creazione e della manutenzione delle tabelle.  
   
**Esecuzione**  
Per eseguire il progetto basta eseguire un `Run As -> Spring Boot App`. Se la connessione non viene instaurata, il progetto non sarà in esecuzione.  
Avviato il progetto aprire Postman e richiamare i vari endpoint, passando gli eventuali parametri necessari all'esecuzione dello stesso.  
Verificare sulle varie tabelle se siano presenti i dati ricevuti come parametro ed eventualmente elaborati.  
