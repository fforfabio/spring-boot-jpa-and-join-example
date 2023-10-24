package com.spring.mssql.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.mssql.dto.SpeakerTalksDTO;
import com.spring.mssql.models.Speaker;


/**
 * This is the repository for the 
 * {@link com.spring.mssql.models.Speaker Speaker} entity.
 * <br>
 * It extends {@link org.springframework.data.jpa.repository.JpaRepository JpaRepository}.
 * <br><br>
 * Inside this class the user can define custom methods
 * that will execute custom queries.
 * <br>
 * To execute this queries inside a controller endpoint the
 * user must write something like this, where <i>executeCustomQuery</i>
 * is a custom method defined inside this repository:
 * <pre>
 * 	SpeakerRepository speakerRepository;
 * 	
 * 	public SpeakerController(SpeakerRepository speakerRepository){
 * 		this.speakerRepository = speakerRepository;
 * 	}
 * 
 * 	@GetMapping("/customQuery")
 *	public ResponseEntity<List<Speaker>> getFromCustomQuery() {
 *		Object s = speakerRepository.executeCustomQuery();
 *	}
 * </pre>
 * <br>
 * For further examples see 
 * {@link com.spring.mssql.controllers.SpeakerController SpeakerController}.
 * @since 1.0.0
 * @author fforfabio
 **/
@Repository
public interface SpeakerRepository extends JpaRepository<Speaker, Long>{
	
//	Query examples
	
//	@Query(value = "SELECT * FROM speakers s WHERE s.first_name = :firstName AND s.last_name LIKE 'M%'", nativeQuery = true)
//	public List<Speaker> getSpeakersByFirstName(@Param("firstName") String firstName);
	
//	@Query(value = "SELECT * FROM speakers s WHERE s.first_name = ?1", nativeQuery = true)
//	public List<Speaker> getSpeakersByFirstName(String firstName);
	
//	@Query("FROM Speaker WHERE firstName = ?1 AND lastName LIKE 'M%'")
//	public List<Speaker> getSpeakersByFirstName(String firstName);
	

	@Query("SELECT new com.spring.mssql.models.Speaker(s.lastName, s.id)"
			+ "FROM Speaker s "
			+ "WHERE s.firstName = ?1")
	public List<Speaker> getSpeakersByFirstName(String firstName);
	
	
	/**
	 * JOIN on DTO with nativeQuery.
	 * The definition of the query is available on the
	 * head of the {@link com.suzuki.spring.mssql.models.Speaker Speaker}
	 * entity. Just search for this method signature inside
	 * the name attribute of the @NamedNativeQuery annotation.
	 * @param firstName of the speaker
	 * @return The query will return all the infos of the speakers with first name as firstName. 
	 * If some fields are null they will be show.
	 * @since 1.0.0
	 * @author fforfabio
//	 **/ 
//	@Query(nativeQuery = true)
//	public List<Speaker> getSpeakersByFirstName(String firstName);
	
	
	/**
	 * JOIN on DTO with JPQL.
	 * The definition of the query is available on the
	 * head of the {@link com.suzuki.spring.mssql.models.Speaker Speaker}
	 * entity. Just search for this method signature inside
	 * the name attribute of the @NamedNativeQuery annotation.
	 * @param id of the speaker
	 * @return The query will return all the talks for the requested speaker.
	 * @since 1.0.0
	 * @author fforfabio
	 **/ 
	@Query("SELECT new com.spring.mssql.dto.speakerTalksDTO(s.lastName, t.title, t.description, s.id, t.id) "
			+ "FROM Speaker s JOIN s.speakerTalks t "
			+ "WHERE t.speaker_id = ?1")
	public List<SpeakerTalksDTO> getSpeakerTalksWithJoinDTOJPQL(long id);
	
	
	/**
	 * JOIN on DTO with nativeQuery.
	 * The definition of the query is available on the
	 * head of the {@link com.suzuki.spring.mssql.models.Speaker Speaker}
	 * entity. Just search for this method signature inside
	 * the name attribute of the @NamedNativeQuery annotation.
	 * @param id of the speaker
	 * @return The query will return all the talks for the requested speaker.
	 * @since 1.0.0
	 * @author fforfabio
	 **/ 
	@Query(nativeQuery = true)
	public List<SpeakerTalksDTO> getSpeakerTalksWithJoinDTONativeQuery(long id);
	
	
	/**
	 * JOIN on DTO with nativeQuery.
	 * The definition of the query is available on the
	 * head of the {@link com.suzuki.spring.mssql.models.Speaker Speaker}
	 * entity. Just search for this method signature inside
	 * the name attribute of the @NamedNativeQuery annotation.
	 * @return The query will return all the speakers with all their talks.
	 * @since 1.0.0
	 * @author fforfabio
	 **/ 
	@Query(nativeQuery = true)
	public List<SpeakerTalksDTO> getAllJoinDTONativeQuery();
	
	
	/**
	 * Complex query with nativeQuery on DTO.
	 * The definition of the query is available on the
	 * head of the {@link com.suzuki.spring.mssql.models.Speaker Speaker}
	 * entity. Just search for this method signature inside
	 * the name attribute of the @NamedNativeQuery annotation.
	 * @param titleLike initial part of the title of the talk
	 * @return The query will return all the speakers with a certain number of talks whose
	 * title is like 'titleLike%'
	 * @since 1.0.0
	 * @author fforfabio
	 **/ 
	@Query(nativeQuery = true)
	public List<SpeakerTalksDTO> getTalksCount(String titleLike);
	
}