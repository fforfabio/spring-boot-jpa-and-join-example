package com.spring.mssql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.mssql.models.Room;


/**
 * This is the repository for the 
 * {@link com.spring.mssql.models.Room Room} entity.
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
 * 	RoomRepository roomRepository;
 * 	
 * 	public Controller(RoomRepository roomRepository){
 * 		this.roomRepository = roomRepository;
 * 	}
 * 
 * 	@GetMapping("/customQuery")
 *	public ResponseEntity<List<Room>> getFromCustomQuery() {
 *		Object r = roomRepository.executeCustomQuery();
 *	}
 * </pre>
 * <br>
 * For further examples see 
 * {@link com.spring.mssql.controllers.RoomController RoomController}.
 * @since 1.0.0
 * @author Marchetti Fabio
 **/
@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{

}
