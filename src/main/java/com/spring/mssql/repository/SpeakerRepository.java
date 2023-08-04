package com.spring.mssql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.mssql.dto.speakerTutorialsDTO;
import com.spring.mssql.models.Speaker;


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
	 * JOIN on DTO with nativeQuery
	 * @param firstName of the speaker
	 * @return The query will return all the infos of the speakers with first name as firstName. 
	 * If some fields are null they will be show.
//	 **/ 
//	@Query(nativeQuery = true)
//	public List<Speaker> getSpeakersByFirstName(String firstName);
	
	
	/**
	 * JOIN on DTO with JPQL
	 * @param id of the speaker
	 * @return The query will return all the tutorials for the requested speaker.
	 **/ 
	@Query("SELECT new com.spring.mssql.dto.speakerTutorialsDTO(s.lastName, t.title, t.description, s.id, t.id) "
			+ "FROM Speaker s JOIN s.speakerTutorials t "
			+ "WHERE t.speaker_id = ?1")
	public List<speakerTutorialsDTO> getSpeakerTutorialsWithJoinDTOJPQL(long id);
	
	
	/**
	 * JOIN on DTO with nativeQuery
	 * @param id of the speaker
	 * @return The query will return all the tutorials for the requested speaker.
	 **/ 
	@Query(nativeQuery = true)
	public List<speakerTutorialsDTO> getSpeakerTutorialsWithJoinDTONativeQuery(long id);
	
	
	/**
	 * JOIN on DTO with nativeQuery
	 * @return The query will return all the speakers with all their tutorials.
	 **/ 
	@Query(nativeQuery = true)
	public List<speakerTutorialsDTO> getAllJoinDTONativeQuery();
	
	
	/**
	 * Complex query with nativeQuery
	 * @param titleLike initial part of the title of the tutorial
	 * @return The query will return all the speakers with a certain number of tutorials whose
	 * title is like 'titleLike%'
	 **/ 
	@Query(nativeQuery = true)
	public List<speakerTutorialsDTO> getTutorialsCount(String titleLike);
	
}