package com.spring.mssql.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.mssql.models.Talk;


/**
 * This is the repository for the 
 * {@link com.spring.mssql.models.Talk Talk} entity.
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
 * 	TalkRepository talkRepository;
 * 	
 * 	public TalkController(TalkRepository talkRepository){
 * 		this.talkRepository = talkRepository;
 * 	}
 * 
 * 	@GetMapping("/customQuery")
 *	public ResponseEntity<List<Talk>> getFromCustomQuery() {
 *		Object t = talkRepository.executeCustomQuery();
 *	}
 * </pre>
 * <br>
 * For further examples see 
 * {@link com.spring.mssql.controllers.TalkController TalkController}.
 * @since 1.0.0
 * @author fforfabio
 **/
@Repository
public interface TalkRepository extends JpaRepository<Talk, Long> {
	
	/**
  	 * Method that perform a query which search for 
  	 * all the Talks, in base if they are been published
  	 * or not.
  	 * @param published a boolean value use to search for Talk published or not.
  	 * @return all the Talks, published or not, depending on the parameter.
	 * @since 1.0.0
	 * @author fforfabio
  	 **/
	List<Talk> findByPublished(boolean published);
	
	
	/**
  	 * Method that perform a query which search for 
  	 * Talks which title contain the parameter
  	 * title in the database.
  	 * @param title substring to search in the Talk title.
  	 * @return all the Talks that contain title inside their title.
	 * @since 1.0.0
	 * @author fforfabio
  	 **/
  	List<Talk> findByTitleContaining(String title);
  	
  	
  	/**
  	 * Method that perform a query that call a 
  	 * user defined function in the database.
  	 * @return all the talks inside the talks table.
	 * @since 1.0.0
	 * @author fforfabio
  	 **/
 	@Query(value = "SELECT * from dbo.getTalksWithFunction()", nativeQuery = true)
 	public List<Talk> getAllTalksWithFunction();
 	
 	
 	/**
  	 * Method that perform a query to retrieve
  	 * the foreign key speaker_id inside the
  	 * talks table for a specific talk.
  	 * <br>
  	 * It is call by the 
  	 * {@link com.spring.mssql.controllers.TalkController#updateTalk updateTalk}
  	 * method inside the {@link com.spring.mssql.controllers.TalkController TalkController}.
  	 * @param talkId the id of the talk to update.
  	 * @return the id of the speaker who held the talk.
	 * @since 1.0.1
	 * @author fforfabio
  	 **/
 	@Query(value = "SELECT t.speaker_id from talks t where t.id = ?1", nativeQuery = true)
 	public long retrieveSpeakerId(long talkId);
}
