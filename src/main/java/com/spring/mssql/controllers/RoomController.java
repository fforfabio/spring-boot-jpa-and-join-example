package com.spring.mssql.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.mssql.models.Room;
import com.spring.mssql.repositories.RoomRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


/**
 * This is the controller for the 
 * {@link com.spring.mssql.models.Room Room} class, 
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
@RequestMapping("/RoomsApi")
public class RoomController {

	
	
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
	 * @param roomRepository an instance of {@link com.spring.mssql.repositories.RoomRepository RoomRepository}
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	public RoomController(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}
	
	
	/**
	 * Method that will return all the Rooms inside the
	 * database.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	@GetMapping("/rooms")
	public ResponseEntity<List<Room>> getAllRooms() {
		try {
			List<Room> rooms = new ArrayList<Room>();
			
			roomRepository.findAll().forEach(rooms::add);

			if (rooms.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(rooms, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	/**
	 * Method that will return a Room with a specific ID.
	 * @param id of the room to search for
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@GetMapping("/room/{id}")
	// @PathVarible will retrieve the parameter from the url
	public ResponseEntity<Room> getRoomById(@PathVariable("id") long id) {
		Optional<Room> roomData = roomRepository.findById(id);

		if (roomData.isPresent()) {
			return new ResponseEntity<>(roomData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	/**
	 * Method that will create a new Room inside the database.
	 * @param room to save
	 * @since 1.0.0
	 * @author fforfabio 
	 **/
	@PostMapping("/createRoom")
	@Operation(summary = "Create a new room.",
    	description = "Create a new room with its location and its capacity.",
    	tags = {"Create room"},
    	responses = {
    			@ApiResponse(responseCode = "201", description = "Room created."),
                @ApiResponse(responseCode = "400", description = "Cannot create room.")})
	// @RequestBody will retrieve the parameter from the body of the request
	public ResponseEntity<Room> createRoom(@RequestBody Room room) {
		try {
			Room _room = roomRepository.save(new Room(room.getRoomName(), room.getRoomCapacity(), room.getRoomFloor()));
			return new ResponseEntity<>(_room, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * This method will update a room that is in 
	 * the rooms table.
	 * <br>
	 * It will check all the attributes of the 
	 * {@link com.spring.mssql.models.Room Room}
	 * object, and if some of them are null or empty
	 * they will be set to a default value.
	 * <br>
	 * If the room is not inside the database a new
	 * {@link org.springframework.data.rest.webmvc.ResourceNotFoundException ResourceNotFoundException}
	 * is throw.
	 * @param roomId id of the Room to update
	 * @param room the Room object with it's new attribute's values
	 * @throws ResourceNotFoundException if the room is not found
	 * @since 1.0.1
	 * @author fforfabio 
	 **/
	@PutMapping("/updateRoom/{roomId}")
	@Operation(summary = "Update a room.",
    	description = "Update a room.",
    	responses = {
    			@ApiResponse(responseCode = "201", description = "Room updated."),
                @ApiResponse(responseCode = "400", description = "Cannot update room.")})
	public ResponseEntity<Room> updateRoom(@PathVariable("roomId") long roomId, @RequestBody Room room) {
		try {
			if(roomRepository.findById(roomId).isPresent()) {
				roomRepository.updateRoom(roomId, room.getRoomName(), room.getRoomCapacity(), room.getRoomFloor());
				return new ResponseEntity<>(HttpStatus.OK);
			}
			throw new ResourceNotFoundException("Room " + roomId + " not found.");
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
