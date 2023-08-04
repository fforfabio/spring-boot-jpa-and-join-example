package com.spring.mssql.models;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.spring.mssql.dto.speakerTutorialsDTO;


@NamedNativeQueries(value = {
	@NamedNativeQuery(name = "Speaker.getSpeakerTutorialsWithJoinDTONativeQuery",
		query = "SELECT s.last_name AS last, t.title AS title, t.description AS descr, s.id AS sID, t.id AS tID "
				+ "FROM speakers s JOIN tutorials t ON s.id = t.speaker_id "
				+ "WHERE t.speaker_id = ?1",
		resultSetMapping = "speakerTutorialsDTO"),
	
	@NamedNativeQuery(name = "Speaker.getAllJoinDTONativeQuery",
	query = "SELECT s.last_name AS last, t.title AS title, t.description AS descr, s.id AS sID, t.id AS tID "
			+ "FROM speakers s, tutorials t "
			+ "WHERE s.id = t.speaker_id",
	resultSetMapping = "speakerTutorialsDTO"),

	@NamedNativeQuery(name = "Speaker.getSpeakersByFirstName",
	query = "SELECT s.last_name AS last, s.id AS sID "
			+ "FROM speakers s "
			+ "WHERE s.first_name = ?1",
	resultSetMapping = "speaker"),
	
	/*@NamedNativeQuery(name = "Speaker.getSpeakersByFirstName",
	query = "SELECT s.first_name AS first, s.last_name AS last, s.id AS sID "
			+ "FROM speakers s "
			+ "WHERE s.first_name = ?1",
	resultSetMapping = "speaker"),*/
	
	@NamedNativeQuery(name = "Speaker.getTutorialsCount",
	query = "SELECT s.last_name AS last, s.id AS sID, COUNT(t.id) AS numTutorials, COUNT(NULLIF(t.published, 0)) AS publishedTutorials "
			+ "FROM speakers s, tutorials t "
			+ "WHERE s.id = t.speaker_id AND t.id IN( "
				+ "SELECT t1.id "
				+ "FROM tutorials t1 "
				+ "WHERE t1.title LIKE CONCAT(?1, '%')) "
				//+ "WHERE t1.title LIKE '[:titleLike]%') "
			+ "GROUP BY s.id, s.last_name "
			+ "HAVING COUNT(t.id) >= 1",
	resultSetMapping = "speakerTutorialsDTOMapping")})

@SqlResultSetMappings(value = {
	@SqlResultSetMapping(name = "speakerTutorialsDTO",
	   classes = @ConstructorResult(targetClass = speakerTutorialsDTO.class,
	                                columns = {@ColumnResult(name = "last"),
	                                           @ColumnResult(name = "title"),
	                                           @ColumnResult(name = "descr"),
	                                           @ColumnResult(name = "sID", type = long.class),
	                                           @ColumnResult(name = "tID", type = long.class)})),
	@SqlResultSetMapping(name = "speaker",
	   classes = @ConstructorResult(targetClass = Speaker.class,
	                                columns = {@ColumnResult(name = "last"),
	                                           @ColumnResult(name = "sId", type = long.class)})),
	/*@SqlResultSetMapping(name = "speaker",
	        entities = @EntityResult(
	                entityClass = Speaker.class,
	                fields = {
	                    @FieldResult(name = "firstName", column = "first"),
	                    @FieldResult(name = "lastName", column = "last"),
	                    @FieldResult(name = "id", column = "sId")})),*/
	
	@SqlResultSetMapping(name = "speakerTutorialsDTOMapping",
	        		classes = @ConstructorResult(targetClass = speakerTutorialsDTO.class,
                    columns = {@ColumnResult(name = "last"),
                               @ColumnResult(name = "sID", type = long.class),
                               @ColumnResult(name = "numTutorials", type = int.class),
                               @ColumnResult(name = "publishedTutorials", type = int.class)}))})
@Entity
@Table(name = "speakers")
public class Speaker {
	
	// Columns of the model speaker
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String firstName;

	@NotNull
	private String lastName;
	
	@Column(nullable = true)
	private int age;

	/* OneToMany relationship. 
	Each speaker has the list of tutorials taken by him.
	FetchType.LAZY -> the list of tutorials will be retrieve only on request.
	*/
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "speaker_id", referencedColumnName = "id")
	private List<Tutorial> speakerTutorials;

	
	/* With standard JPA, static fields are not persisted, and final fields are not persisted too. */
	private static final Logger logger = LogManager.getLogger(Speaker.class.getName());
	
	
	public Speaker() {
	}

	
	/**
	 * Constructor
	 * @param firstName of the speaker
	 * @param lastName of the speaker
	 * @param age of the speaker
	 **/
	public Speaker(String firstName, String lastName, int age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	
	
	/**
	 * Constructor for query getSpeakersByFirstName
	 * @param lastName of the speaker
	 * @param id of the speaker
	 **/
	public Speaker(String lastName, long id) {
		  this.lastName = lastName;
		  this.id = id;
		  this.firstName = null;
		  this.age = -1;
	}
	
	
	// Getter and setter
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		logger.info("Method getFirstName()\tFirstName " + this.firstName);
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public List<Tutorial> getSpeakerTutorials() {
		return speakerTutorials;
	}

	public void setSpeakerTutorials(List<Tutorial> speakerTutorials) {
		this.speakerTutorials = speakerTutorials;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "Speaker: " + this.firstName + " " + this.lastName +
				" id: " + String.valueOf(this.id);
	}
}