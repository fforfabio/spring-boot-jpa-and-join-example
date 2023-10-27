package com.spring.mssql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
 * @author fforfabio
 **/
@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{

	
	/**
	 * This query is called by the 
	 * {@link com.spring.mssql.controllers.RoomController#updateRoom(long, Room) updateRoom}
	 * method inside the {@link com.spring.mssql.controllers.RoomController RoomController}.
	 * <br>
	 * It's purpose is to update a room.
	 * @param roomId id of the room to update
	 * @param roomName the new name of the room
	 * @param roomCapacity the new capacity of the room
	 * @param roomFloor the new floor of the room
	 * @since 1.0.1
	 * @author fforfabio
	 **/
	@Modifying
	@Transactional
	@Query(value = "UPDATE rooms "
			+ "SET roomName = ?2, roomCapacity = ?3, roomFloor = ?4 "
			+ "WHERE id = ?1", nativeQuery = true)
	public void updateRoom(long roomId, String roomName, long roomCapacity,int roomFloor);
	
}
