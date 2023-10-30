package com.spring.mssql.models;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.spring.mssql.dto.SpeakerTalksDTO;


/**
 * This class represent the speakers table.
 * <br><br>
 * Each Speaker is associated with a 
 * {@link com.spring.mssql.models.Talk Talk}
 * in a unidirectional one-to-many relationship.
 * Each Speaker could have more Talks, while each 
 * Talk could be taken by a Speaker.
 * To perform this operation we have a 
 * {@link Speaker#speakerTalks speakerTalks} attribute inside 
 * this class, which will store all the
 * talks held by a Speaker.
 * @since 1.0.0
 * @author fforfabio
 **/
@NamedNativeQueries(value = {
	@NamedNativeQuery(name = "Speaker.getSpeakerTalksWithJoinDTONativeQuery",
		query = "SELECT s.lastName AS last, t.title AS title, t.description AS descr, s.id AS sID, t.id AS tID "
				+ "FROM speakers s JOIN talks t ON s.id = t.speaker_id "
				+ "WHERE t.speaker_id = ?1",
		resultSetMapping = "speakerTalksDTO"),
	
	@NamedNativeQuery(name = "Speaker.getAllJoinDTONativeQuery",
	query = "SELECT s.lastName AS last, t.title AS title, t.description AS descr, s.id AS sID, t.id AS tID "
			+ "FROM speakers s, talks t "
			+ "WHERE s.id = t.speaker_id",
	resultSetMapping = "speakerTalksDTO"),

	@NamedNativeQuery(name = "Speaker.getSpeakersByFirstName",
	query = "SELECT s.lastName AS last, s.id AS sID "
			+ "FROM speakers s "
			+ "WHERE s.first_name = ?1",
	resultSetMapping = "speaker"),
	
	/*@NamedNativeQuery(name = "Speaker.getSpeakersByFirstName",
	query = "SELECT s.first_name AS first, s.last_name AS last, s.id AS sID "
			+ "FROM speakers s "
			+ "WHERE s.first_name = ?1",
	resultSetMapping = "speaker"),*/
	
	@NamedNativeQuery(name = "Speaker.getTalksCount",
	query = "SELECT s.lastName AS last, s.id AS sID, COUNT(t.id) AS numTalks, COUNT(NULLIF(t.published, 0)) AS publishedTalks "
			+ "FROM speakers s, talks t "
			+ "WHERE s.id = t.speaker_id AND t.id IN( "
				+ "SELECT t1.id "
				+ "FROM talks t1 "
				+ "WHERE t1.title LIKE CONCAT(?1, '%')) "
				//+ "WHERE t1.title LIKE '[:titleLike]%') "
			+ "GROUP BY s.id, s.lastName "
			+ "HAVING COUNT(t.id) >= 1",
	resultSetMapping = "speakerTalksDTOMapping")})

@SqlResultSetMappings(value = {
	@SqlResultSetMapping(name = "speakerTalksDTO",
	   classes = @ConstructorResult(targetClass = SpeakerTalksDTO.class,
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
	
	@SqlResultSetMapping(name = "speakerTalksDTOMapping",
	        		classes = @ConstructorResult(targetClass = SpeakerTalksDTO.class,
                    columns = {@ColumnResult(name = "last"),
                               @ColumnResult(name = "sID", type = long.class),
                               @ColumnResult(name = "numTalks", type = int.class),
                               @ColumnResult(name = "publishedTalks", type = int.class)}))})
@Entity
@Table(name = "speakers")
public class Speaker {
	
	// Columns of the model Speaker
	/**
	 * Identifier of the speaker.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/**
	 * First name of the speaker.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	@NotNull
	private String firstName;

	/**
	 * Last name of the speaker.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	@NotNull
	private String lastName;
	
	/**
	 * Age of the speaker.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	@Column(nullable = true)
	private int age;

	/**
	 * Unidirectional relationship between Speaker entity
	 * and Talk entity. Inside this last one there is
	 * a speaker_id attribute that represent the foreign key.
	 * If the name is not set, it assume the default value.
	 * <br>
	 * Each speaker has the list of talks taken by him.
	 * FetchType.LAZY means that the list of talks will be 
	 * retrieve only on request.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "speaker_id", referencedColumnName = "id")
	private List<Talk> speakerTalk;

	
	/* With standard JPA, static fields are not persisted, and final fields are not persisted too. */
	private static final Logger logger = LogManager.getLogger(Speaker.class.getName());
	
	
	public Speaker() {
	}

	
	/**
	 * Constructor
	 * @param firstName of the speaker
	 * @param lastName of the speaker
	 * @param age of the speaker
	 * @since 1.0.0
 	 * @author fforfabio
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
	 * @since 1.0.0
 	 * @author fforfabio
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
	
	public List<Talk> getSpeakerTalks() {
		return speakerTalk;
	}

	public void setSpeakerTalks(List<Talk> speakerTalks) {
		this.speakerTalk = speakerTalks;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "Speaker " + String.valueOf(this.id) + " :" 
				+ this.firstName + " " + this.lastName;
	}
}