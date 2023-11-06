package com.spring.mssql.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.mssql.dto.SpeakerTalksDTO;
import com.spring.mssql.models.Speaker;
import com.spring.mssql.models.Talk;
import com.spring.mssql.repositories.SpeakerRepository;
import com.spring.mssql.repositories.TalkRepository;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;


/**
 * This is the controller for the 
 * {@link com.spring.mssql.models.Speaker Speaker} class, 
 * where all the endpoints of this entity are implemented.
 * To make it a controller the class must be annotated with
 * {@link org.springframework.web.bind.annotation.ReastController @RestController}.
 * <br>
 * It is also specified the origins allowed for the CORS
 * with {@link org.springframework.web.bind.annotation.CrossOrigin @CrossOrigin},
 * and the first part of the path to call to retrieve the
 * information from an endpoint with
 * {@link org.springframework.web.bind.annotation.RequestMapping @RequestMapping}.
 * @since 1.0.0
 * @author fforfabio
 * @see
 * <ul>
 * <li><a href="https://spring.io/guides/gs/rest-service-cors/">Spring doc for CORS</a></li>
 * <li><a href="https://www.baeldung.com/spring-cors">CORS pt.2</a></li>
 * </ul>
 **/
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/SpeakersApi")
public class SpeakerController {

	/**
	 * To perform a call to a query declared inside 
	 * the {@link com.spring.mssql.repositories.TalkRepository TalkRepository} 
	 * we must have a variable that will link us to the 
	 * repository itself.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	TalkRepository talkRepository;
	
	
	/**
	 * To perform a call to a query declared inside 
	 * the {@link com.spring.mssql.repositories.SpeakerRepository SpeakerRepository} 
	 * we must have a variable that will link us to the 
	 * repository itself.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	SpeakerRepository speakerRepository;

	
	/**
	 * With this constructor we will avoid the use of the
	 * {@link org.springframework.beans.factory.annotation.Autowired @Autowired} 
	 * annotation.
	 * @param talkRepository an instance of {@link com.spring.mssql.repositories.TalkRepository TalkRepository}
	 * @param speakerRepository an instance of {@link com.spring.mssql.repositories.SpeakerRepository SpeakerRepository}
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	public SpeakerController(TalkRepository talkRepository, SpeakerRepository speakerRepository) {
		this.talkRepository = talkRepository;
		this.speakerRepository = speakerRepository;
	}
	
	
	/**
	 * Retrieve all the speakers inside the speakers table.
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/speakers")
	public ResponseEntity<List<Speaker>> getAllSpeakers() {
		try {
			List<Speaker> speakers = new ArrayList<Speaker>();

			speakerRepository.findAll().forEach(speakers::add);

			if (speakers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(speakers, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Method that will retrieve a specific speaker.
	 * @param id of the speaker to search for
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/speakers/{id}")
	// @PathVarible will retrieve the parameter from the url
	public ResponseEntity<Speaker> getSpeakerById(@PathVariable("id") long id) {
		Optional<Speaker> speakerData = speakerRepository.findById(id);

		if (speakerData.isPresent()) {
			return new ResponseEntity<>(speakerData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	/**
	 * Method that will create a new speaker.
	 * @param speaker an instance of {@link com.spring.mssql.models.Speaker Speaker}
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@PostMapping("/createSpeaker")
	@Operation(summary = "Create a new speaker.",
    	description = "Create a new speaker with first name and last name as mandatory parameters.",
    	tags = {"Create speaker"},
    	responses = {
    			@ApiResponse(responseCode = "201", description = "Speaker created."),
                @ApiResponse(responseCode = "400", description = "Cannot create speaker.")})
	// @RequestBody will retrieve the parameter from the body of the request
	public ResponseEntity<Speaker> createSpeaker(@RequestBody Speaker speaker) {
		try {
			Speaker _speaker = speakerRepository.save(
					new Speaker(speaker.getFirstName(), speaker.getLastName(), speaker.getAge()));
			return new ResponseEntity<>(_speaker, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Method that will update a speaker.
	 * @param id of the speaker to update
	 * @param speaker an instance of {@link com.spring.mssql.models.Speaker Speaker}
	 * @since 1.0.2
	 * @author fforfabio 
	 **/
	@PutMapping("/speakers/{id}")
	// @PathVarible will retrieve the parameter from the url
	// @RequestBody will retrieve the parameter from the body of the request
	public ResponseEntity<Speaker> updateSpeaker(@PathVariable("id") long id, @RequestBody Speaker speaker) {
		
		ResponseEntity<Speaker> update = speakerRepository.findById(id)
				.map(s -> {
					s.setFirstName(speaker.getFirstName());
					s.setLastName(speaker.getLastName());
					s.setAge(speaker.getAge());
					return new ResponseEntity<Speaker>(speakerRepository.save(s), HttpStatus.OK);
				})
				.orElseGet(() -> {
					return new ResponseEntity<Speaker>(speakerRepository.save(speaker), HttpStatus.OK);
				});
		return update;
		
	}


	/**
	 * Method that will delete a speaker.
	 * @param id of the speaker to delete.
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@DeleteMapping("/speakers/{id}")
	// @PathVarible will retrieve the parameter from the url
	public ResponseEntity<HttpStatus> deleteSpeaker(@PathVariable("id") long id) {
		try {
			if(speakerRepository.findById(id).isPresent()){
				if(speakerRepository.findById(id).get().getSpeakerTalks().isEmpty()) {
					speakerRepository.deleteById(id);
				}
				else {
					List<Talk> sTut = speakerRepository.findById(id).get().getSpeakerTalks();
					for(Talk tut : sTut) {
						talkRepository.save(tut);
					}
					speakerRepository.deleteById(id);
				}
				return new ResponseEntity<>(HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
	 * Method that will delete all the speakers.
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@DeleteMapping("/speakers")
	public ResponseEntity<HttpStatus> deleteAllSpeakers() {
		try {
			if(speakerRepository.findAll().isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			else {
				speakerRepository.deleteAll();
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Method that will search for all the speakers
	 * with a specific first name.
	 * @param firstName first name (or a substring) of the speakers to search for.
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/speakersByFirst")
	// @RequestBody will retrieve the parameter from the body of the request
	public ResponseEntity<Map<Long, Speaker>> getSpeakersByFirstName(@RequestBody String firstName) {
		try {
			List<Speaker> speaker = speakerRepository.getSpeakersByFirstName(firstName);

			if (speaker.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(speaker.stream().collect(Collectors.toMap(Speaker::getId, s -> s)), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Method that will perform a join between
	 * speakers and talks table.
	 * <br>
	 * It call the 
	 * {@link com.spring.mssql.repositories.SpeakerRepository#getSpeakerTalksWithJoinDTOJPQL 
	 * getSpeakerTalksWithJoinDTOJPQL}
	 * query inside the
	 * {@link com.spring.mssql.repositories.SpeakerRepository SpeakerRepository}
	 * @param speakerId id of the speaker to search for
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/speakerTalksJoinExampleDTOJPQL")
	// @RequestBody will retrieve the parameter from the body of the request
	public ResponseEntity<List<SpeakerTalksDTO>> getSpeakerTalksWithJoinDTOJPQL(@RequestBody String speakerId) {
		try {
			List<SpeakerTalksDTO> dto = speakerRepository.getSpeakerTalksWithJoinDTOJPQL(Long.parseLong(speakerId));
			if (dto.isEmpty()) {
				return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Method that will perform a join between
	 * speakers and talks table.
	 * <br>
	 * It call the 
	 * {@link com.spring.mssql.repositories.SpeakerRepository#getSpeakerTalksWithJoinDTONativeQuery 
	 * getSpeakerTalksWithJoinDTONativeQuery}
	 * query inside the
	 * {@link com.spring.mssql.repositories.SpeakerRepository SpeakerRepository}
	 * @param speakerId id of the speaker to search for
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/speakerTalksJoinExampleDTONativeQuery")
	// @RequestBody will retrieve the parameter from the body of the request
	public ResponseEntity<Map<Long, SpeakerTalksDTO>> getSpeakerTalksWithJoinDTONativeQuery(@RequestBody String speakerId) {
		try {
			List<SpeakerTalksDTO> dto = speakerRepository.getSpeakerTalksWithJoinDTONativeQuery(Long.parseLong(speakerId));
			if (dto.isEmpty()) {
				return new ResponseEntity<>(new HashMap<Long, SpeakerTalksDTO>(), HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(dto.stream().collect(Collectors.toMap(SpeakerTalksDTO::getTalkId,  
					Function.identity(), (o1, o2) -> o1, TreeMap::new)),
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Method that will perform a join between
	 * speakers and talks table.
	 * <br>
	 * It call the 
	 * {@link com.spring.mssql.repositories.SpeakerRepository#getAllJoinDTONativeQuery 
	 * getAllJoinDTONativeQuery}
	 * query inside the
	 * {@link com.spring.mssql.repositories.SpeakerRepository SpeakerRepository}
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/getAllJoinDTONativeQuery")
	public ResponseEntity<List<SpeakerTalksDTO>> getAllJoinDTONativeQuery() {
		try {
			List<SpeakerTalksDTO> dto = speakerRepository.getAllJoinDTONativeQuery();
			if (dto.isEmpty()) {
				return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Method that will perform a join between
	 * speakers and talks table.
	 * <br>
	 * It call the 
	 * {@link com.spring.mssql.repositories.SpeakerRepository#getTalksCount 
	 * getTalksCount}
	 * query inside the
	 * {@link com.spring.mssql.repositories.SpeakerRepository SpeakerRepository}
	 * @param titleLike substring of the title of the talk to search for
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/getTalksCount")
	public ResponseEntity<List<SpeakerTalksDTO>> getTalksCount(@RequestBody(required=false) String titleLike) {
		try {
			if(titleLike == null)
				titleLike = "";
			List<SpeakerTalksDTO> dto = speakerRepository.getTalksCount(titleLike);
			if (dto.isEmpty()) {
				return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Method that will return all the speakers
	 * inside the database with pagination.
	 * @param page indicate which page of the result show.
	 * @param size indicate the size of each page, so how much elements must be inside one page
	 * @return the page, specified in the page parameter,
	 * with the current speakers.
	 * @since 1.0.2
	 * @author fforfabio 
	 **/
	@GetMapping("/speakersPagination")
	public Page<Speaker> getAllSpeakersWithPagination(@RequestParam int page, @RequestParam int size) {
		// Create the new Pageable object
		PageRequest pr = PageRequest.of(page, size);
		/*
		 * Call the Page<T> findAll(Pageable pageable); method defined
		 * in the JpaRepository<T, ID>. 
		 * NOTE that JpaRepository extends PagingAndSortingRepository.
		 */
        Page<Speaker> p = speakerRepository.findAll(pr);        
        return p;
	}
	
	
	/**
	 * Method that will return all the speakers
	 * inside the database with pagination and 
	 * sorted by lastName.
	 * @param page indicate which page of the result show.
	 * @param size indicate the size of each page, so how much elements must be inside one page
	 * @return the page, specified in the page parameter,
	 * with the current speakers ordered by lastName.
	 * @since 1.0.2
	 * @author fforfabio 
	 **/
	@GetMapping("/speakersPaginationAndSorting")
	public Page<Speaker> getAllSpeakersWithPaginationAndSorting(@RequestParam int page, @RequestParam int size) {
		// Create the new Pageable object
		PageRequest pr = PageRequest.of(page, size, Sort.by("lastName"));
		/*
		 * Call the Page<T> findAll(Pageable pageable); method defined
		 * in the JpaRepository<T, ID>. 
		 * NOTE that JpaRepository extends PagingAndSortingRepository.
		 */ 
        Page<Speaker> p = speakerRepository.findAll(pr);        
        return p;
	}
	
	
	/**
	 * Method that will return all the speakers
	 * inside the database sorted by firstName and
	 * lastName in a descending order.
	 * @return a list of all the speakers ordered by
	 * firstName and lastName in a descending order.
	 * @since 1.0.2
	 * @author fforfabio 
	 **/
	@GetMapping("/speakersSorting")
	public List<Speaker> getAllSpeakersWithSorting() {
		/* Call the List<T> findAll(Sort sort); method defined in the
         * JpaRepository<T, ID>. 
         * It override the Iterable<T> findAll(Sort sort); method defined in the
         * PagingAndSortingRepository<T, ID> interface.
         */
        List<Speaker> p = speakerRepository.findAll(Sort.by("firstName").and(Sort.by("lastName")).descending());
        return p;
	}
}
