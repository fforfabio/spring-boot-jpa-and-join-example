package com.spring.mssql.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


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
	
	/*
	 * The value of mappedBy is the name of the association-mapping 
	 * attribute on the owning side, so in the Talk class because
	 * it own the foreign key. It is necessary for bidirectional 
	 * relationships.
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
	private List<Talk> talk;

	
	public Room() {}
	
	public Room(String roomName, long roomCapacity, int roomFloor) {
		super();
		this.roomName = roomName;
		this.roomCapacity = roomCapacity;
		this.roomFloor = roomFloor;
	}

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

	public List<Talk> getTalk() {
		return talk;
	}

	public void setTalk(List<Talk> talk) {
		this.talk = talk;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", roomName=" + roomName + ", roomCapacity=" + roomCapacity + ", roomFloor="
				+ roomFloor + ", talk=" + talk + "]";
	}
}
