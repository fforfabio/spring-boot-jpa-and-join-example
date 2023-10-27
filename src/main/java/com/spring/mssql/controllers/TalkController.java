package com.spring.mssql.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.mssql.models.Room;
import com.spring.mssql.models.Talk;
import com.spring.mssql.repositories.RoomRepository;
import com.spring.mssql.repositories.SpeakerRepository;
import com.spring.mssql.repositories.TalkRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


/**
 * This is the controller for the 
 * {@link com.spring.mssql.models.Talk Talk} class, 
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
@RequestMapping("/TalksApi")
public class TalkController {
	
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
	 * To perform a call to a query declared inside 
	 * the {@link com.spring.mssql.repositories.RoomRepository RoomRepository} 
	 * we must have a variable that will link us to the 
	 * repository itself.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	RoomRepository roomRepository;
	
	/**
	 * With this constructor we will avoid the use of the
	 * {@link org.springframework.beans.factory.annotation.Autowired @Autowired} 
	 * annotation.
	 * @param talkRepository an instance of {@link com.spring.mssql.repositories.TalkRepository TalkRepository}
	 * @param speakerRepository an instance of {@link com.spring.mssql.repositories.SpeakerRepository SpeakerRepository}
	 * @param roomRepository an instance of {@link com.spring.mssql.repositories.RoomRepository RoomRepository}
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	public TalkController(TalkRepository talkRepository, SpeakerRepository speakerRepository, RoomRepository roomRepository) {
		this.talkRepository = talkRepository;
		this.speakerRepository = speakerRepository;
		this.roomRepository = roomRepository;
	}	
	

	/**
	 * Method that will retrieve all the talks.
	 * <br>
	 * If the title parameter is not null it will
	 * search for all the talks with a specific
	 * title (also a substring is allowed).
	 * <br>
	 * If the title parameter is null it will return
	 * all the talks inside the database.
	 * @param title string (or substring) representing the title of the talk.
	 * It is non required.
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/talks")
	public ResponseEntity<List<Talk>> getAllTalks(@RequestParam(required = false) String title) {
		try {
			List<Talk> talks = new ArrayList<Talk>();

			if (title == null)
				talkRepository.findAll().forEach(talks::add);
			else
				talkRepository.findByTitleContaining(title).forEach(talks::add);

			if (talks.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(talks, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
	 * Mehod that will search a specific talk.
	 * @param id of the talk to retrieve as 
	 * {@link org.springframework.web.bind.annotation.PathVariable @PathVariable}
	 * @return {@link org.springframework.lang.Nullable.HttpStatus#CREATED 201}
	 * and the talk if its identifier is present into the database. Otherwise
	 * {@link org.springframework.lang.Nullable.HttpStatus#NO_CONTENT 204}
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/talks/{id}")
	// @PathVarible will retrieve the parameter from the url
	public ResponseEntity<Talk> getTalkById(@PathVariable("id") long id) {
		
		// Call the findById query
		Optional<Talk> talkData = talkRepository.findById(id);

		if (talkData.isPresent()) {
			// Return the talk
			return new ResponseEntity<>(talkData.get(), HttpStatus.OK);
		} else {
			// Return 204 if no talk is found
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	
	/**
	 * Method that will create a new 
	 * {@link com.spring.mssql.models.Talk Talk}.
	 * @param speakerId Id of the Speaker who held the talk.
	 * @param roomId Id of the room where the talk has been taken.
	 * @param talk to save into the database as a 
	 * {@link org.springframework.web.bind.annotation.RequestBody @RequestBody}
	 * @return {@link org.springframework.lang.Nullable.HttpStatus#CREATED 201}
	 * and the talk if the save operation is succeed. Otherwise
	 * {@link org.springframework.lang.Nullable.HttpStatus#INTERNAL_SERVER_ERROR 500}
	 * @throws ResourceNotFoundException if the Room or the Speaker are not found.
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@PostMapping("talk/{speakerId}/{roomId}")
	@ApiResponse(responseCode = "201", description = "Talk created")
	// @RequestBody will retrieve the parameter from the body of the request
	public ResponseEntity<Talk> createTalk(@PathVariable(value = "speakerId") Long speakerId,
			@PathVariable(value = "roomId") Long roomId, @RequestBody Talk talk) {
		try {
			if(speakerRepository.findById(speakerId).isPresent()) {
				if(roomRepository.findById(roomId).isPresent()) {
					Room r = roomRepository.findById(roomId).get();

					speakerRepository.findById(speakerId).get().getSpeakerTalks().add(talk);
					talk.setRoom(r);

					talkRepository.save(talk);

					return new ResponseEntity<>(talk, HttpStatus.CREATED);
				}
				else
					throw new ResourceNotFoundException("Not found Room with id = " + roomId);
			}
			else
				throw new ResourceNotFoundException("Not found Speaker with id = " + speakerId);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
	 * Method that will update a specific talk.
	 * @param talkId id of the talk to update.
	 * @param roomId optional id of the room where the talk has been taken.
	 * @param speakerId optional id of the speaker of the talk.
	 * @param talk an instance of {@link com.spring.mssql.models.Talk Talk}.
	 * @since 1.0.1
	 * @author fforfabio 
	 **/
	@PutMapping(value = {"/talksTSR/{talkId}/{speakerId}/{roomId}", "/talksTS/{talkId}/{speakerId}",
			"/talksTR/{talkId}/{roomId}", "/talks/{talkId}"})
	// @PathVarible will retrieve the parameter from the url
	// @RequestBody will retrieve the parameter from the body of the request
	public ResponseEntity<Talk> updateTalk(@PathVariable("talkId") long talkId, 
			@PathVariable(required = false, name = "roomId") Optional<Long> roomId,
			@PathVariable(required = false, name = "speakerId") Optional<Long> speakerId,
			@RequestBody Talk talk) {
		try {
			Optional<Talk> talkData = talkRepository.findById(talkId);
			
			if (talkData.isPresent()) {
				Talk _talk = talkData.get();
				
				// Update the Talk
				_talk.setTitle(talk.getTitle());
				_talk.setDescription(talk.getDescription());
				_talk.setPublished(talk.isPublished());
				
				/*
				 * To update the fk inside the Talk entity you can
				 * use two ways.
				 * The first one is the one used for the room_id, where
				 * an updateTalk(_talk) method is call.
				 * The updateTalk method will remove the talk from the list
				 * of talks taken in that room and will add it to the list
				 * of the new room.
				 * The second one is the one used for the speaker_id, where
				 * are used the .remove() and the .add() method on the list
				 * of talks retrieve via a getter method getSpeakerTalks()
				 */
				// Update the foreign key room_id
				if(roomId.isPresent())
					if(roomRepository.findById(roomId.get()).isPresent())
						roomRepository.findById(roomId.get()).get().updateTalk(_talk);
				
				// Update the foreign_key speaker_id
				if(speakerId.isPresent()) {
					if(speakerRepository.findById(speakerId.get()).isPresent()) {
						// Retrieve the old speaker_id value of the talk to update.
						long sId = talkRepository.retrieveSpeakerId(talkId);
						// Remove the talk from the list of the old speaker.
						speakerRepository.findById(sId).get().getSpeakerTalks().remove(_talk);
						// Add the talk to the list of the new speaker.
						speakerRepository.findById(speakerId.get()).get().getSpeakerTalks().add(_talk);
					}
				}
				return new ResponseEntity<>(talkRepository.save(_talk), HttpStatus.OK);
			} else {
				throw new ResourceNotFoundException("Not found Talk with id = " + talkId);
			}
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
	 * Method that will delete a specific talk.
	 * @param id of the talk to delete.
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@DeleteMapping("/talks/{id}")
	// @PathVarible will retrieve the parameter from the url
	public ResponseEntity<HttpStatus> deleteTalk(
			@Parameter(description = "The id of the talk to delete", required = true)@PathVariable("id") long id) {
		try {
			talkRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
	 * Method that will delete all the talks.
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@DeleteMapping("/talks")
	public ResponseEntity<HttpStatus> deleteAllTalks() {
		try {
			talkRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	/**
	 * Method that will return all the published Talks or
	 * the Talks that are not been published yet, based on
	 * the parameter of the
	 * {@link com.spring.mssql.repositories.TalkRepository#findByPublished(boolean) findByPublished}
	 * method.
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/published")
	public ResponseEntity<List<Talk>> findByPublished() {
		try {
			List<Talk> talks = talkRepository.findByPublished(true);

			if (talks.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(talks, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Method that will return all the Talks inside
	 * the database with a user defined function.
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/talkFunction")
	public ResponseEntity<List<Talk>> getAllTalks() {
		try {
			List<Talk> talks = talkRepository.getAllTalksWithFunction();

			if (talks.isEmpty()) {
				return new ResponseEntity<>(talks, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(talks, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
