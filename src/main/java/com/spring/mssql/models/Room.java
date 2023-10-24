package com.spring.mssql.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * This class represent the rooms table.
 * <br><br>
 * Each room is associated with a 
 * {@link com.spring.mssql.models.Talk Talk}
 * in a bidirectional one-to-many relationship.
 * Each Room could have host more Talk, while each 
 * Talk could be taken in only one Room.
 * To perform this operation we have a 
 * {@link Room#talks talks} attribute inside 
 * this class, which will store all the Talks held
 * in this Room.
 * @since 1.0.0
 * @author fforfabio
 **/
@Entity
@Table(name = "rooms")
public class Room {

	// Columns of the model Talk
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String roomName;
	
	private long roomCapacity;
	
	private int roomFloor;
	
	/**
	 * The value of mappedBy is the name of the association-mapping 
	 * attribute on the owning side, so in the Talk class because
	 * it own the foreign key. It is necessary for bidirectional 
	 * relationships.
	 * @since 1.0.0
 	 * @author fforfabio
	 **/
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
	private List<Talk> talks;

	
	public Room() {}
	
	public Room(String roomName, long roomCapacity, int roomFloor) {
		super();
		this.roomName = roomName;
		this.roomCapacity = roomCapacity;
		this.roomFloor = roomFloor;
	}

	
	// Getter and setter
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public long getRoomCapacity() {
		return roomCapacity;
	}

	public void setRoomCapacity(long roomCapacity) {
		this.roomCapacity = roomCapacity;
	}

	public int getRoomFloor() {
		return roomFloor;
	}

	public void setRoomFloor(int roomFloor) {
		this.roomFloor = roomFloor;
	}

	public List<Talk> getTalks() {
		return talks;
	}

	public void setTalks(List<Talk> talks) {
		this.talks = talks;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", roomName=" + roomName + ", roomCapacity=" + roomCapacity + ", roomFloor="
				+ roomFloor + ", talks=" + talks + "]";
	}
}
