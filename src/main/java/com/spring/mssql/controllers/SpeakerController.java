package com.spring.mssql.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.mssql.dto.speakerTutorialsDTO;
import com.spring.mssql.models.Speaker;
import com.spring.mssql.models.Talk;
import com.spring.mssql.repository.SpeakerRepository;
import com.spring.mssql.repository.TalkRepository;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;


/**
 * This is the controller for the Speaker class, 
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
	 * the {@link com.spring.mssql.repository.TalkRepository TutorialRepository} 
	 * we must have a variable that will link us to the 
	 * repository itself.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	TalkRepository talkRepository;
	
	
	/**
	 * To perform a call to a query declared inside 
	 * the {@link com.spring.mssql.repository.SpeakerRepository SpeakerRepository} 
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
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	public SpeakerController(TalkRepository talkRepository, SpeakerRepository speakerRepository) {
		this.talkRepository = talkRepository;
		this.speakerRepository = speakerRepository;
	}
	
	
	/**
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
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
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
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@PutMapping("/speakers/{id}")
	// @PathVarible will retrieve the parameter from the url
	// @RequestBody will retrieve the parameter from the body of the request
	public ResponseEntity<Speaker> updateSpeaker(@PathVariable("id") long id, @RequestBody Speaker speaker) {
		Optional<Speaker> speakerData = speakerRepository.findById(id);
		if (speakerData.isPresent()) {
			Speaker _speaker = speakerData.get();
			_speaker.setFirstName(speaker.getFirstName());
			_speaker.setLastName(speaker.getLastName());
			_speaker.setAge(speaker.getAge());
			return new ResponseEntity<>(speakerRepository.save(_speaker), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	/**
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
						tut.setSpeaker_id(speakerRepository.findAll().get(0).getId());
						talkRepository.save(tut);
					}
					speakerRepository.deleteById(id);
				}
				return new ResponseEntity<>(HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
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
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
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
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/speakerTutorialsJoinExampleDTOJPQL")
	// @RequestBody will retrieve the parameter from the body of the request
	public ResponseEntity<List<speakerTutorialsDTO>> getSpeakerTutorialsWithJoinDTOJPQL(@RequestBody String speakerId) {
		try {
			List<speakerTutorialsDTO> dto = speakerRepository.getSpeakerTutorialsWithJoinDTOJPQL(Long.parseLong(speakerId));
			if (dto.isEmpty()) {
				return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/speakerTutorialsJoinExampleDTONativeQuery")
	// @RequestBody will retrieve the parameter from the body of the request
	public ResponseEntity<Map<Long, speakerTutorialsDTO>> getSpeakerTutorialsWithJoinDTONativeQuery(@RequestBody String speakerId) {
		try {
			List<speakerTutorialsDTO> dto = speakerRepository.getSpeakerTutorialsWithJoinDTONativeQuery(Long.parseLong(speakerId));
			if (dto.isEmpty()) {
				return new ResponseEntity<>(new HashMap<Long, speakerTutorialsDTO>(), HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(dto.stream().collect(Collectors.toMap(speakerTutorialsDTO::getTutorialId,  
					Function.identity(), (o1, o2) -> o1, TreeMap::new)),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/getAllJoinDTONativeQuery")
	public ResponseEntity<List<speakerTutorialsDTO>> getAllJoinDTONativeQuery() {
		try {
			List<speakerTutorialsDTO> dto = speakerRepository.getAllJoinDTONativeQuery();
			if (dto.isEmpty()) {
				return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/getTutorialsCount")
	public ResponseEntity<List<speakerTutorialsDTO>> getTutorialsCount(@RequestBody(required=false) String titleLike) {
		try {
			if(titleLike == null)
				titleLike = "";
			List<speakerTutorialsDTO> dto = speakerRepository.getTutorialsCount(titleLike);
			if (dto.isEmpty()) {
				return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
