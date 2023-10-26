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

	// Columns of the model Room
	/**
	 * Identifier of the room.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	/**
	 * Name of the room.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	private String roomName;
	
	
	/**
	 * Capacity of the room.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	private long roomCapacity;
	
	
	/**
	 * Floor of the room.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
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
	
	/**
	 * Constructor
	 * @param roomName the name of the room
	 * @param roomCapacity the capacity of the room
	 * @param roomFloor the floor of the room
	 * @since 1.0.0
 	 * @author fforfabio
	 **/
	public Room(String roomName, long roomCapacity, int roomFloor) {
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
	
	
	// Service methods
	/**
	 * This method is used to update the reference 
	 * between {@link Room} and {@link Talk}.
	 * It will call in order the 
	 * {@link Room#removeTalk(Talk)} and the
	 * {@link Room#addTalk(Talk)} methods.
	 * @since 1.0.1
	 * @author fforfabio
	 **/
	public void updateTalk(Talk talk) {
		this.removeTalk(talk);
		this.addTalk(talk);
	}
	 
	/**
	 * This method perform two operations:
	 * <ol>
	 * <li>
	 * Remove the talk from the {@link Room#talks talks}
	 * list of this room;
	 * </li>
	 * <li>
	 * Set the room for the talk to null.
	 * </li>
	 * </ol>
	 * @param talk to update
	 * @since 1.0.1
	 * @author fforfabio
	 **/
	private void removeTalk(Talk talk) {
		talks.remove(talk);
		talk.setRoom(null);
	}
	
	/**
	 * This method perform two operations:
	 * <ol>
	 * <li>
	 * Add the talk into the {@link Room#talks talks}
	 * list of this room;
	 * </li>
	 * <li>
	 * Set this room as the room where the talk is taken.
	 * </li>
	 * </ol>
	 * @param talk to update
	 * @since 1.0.1
	 * @author fforfabio
	 **/
	private void addTalk(Talk talk) {
        talks.add(talk);
        talk.setRoom(this);
    }
	

	@Override
	public String toString() {
		return "Room [id=" + id + ", roomName=" + roomName + ", roomCapacity=" + roomCapacity + ", roomFloor="
				+ roomFloor + ", talks=" + talks + "]";
	}
}
