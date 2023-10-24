package com.spring.mssql.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * This class represent the talks table.
 * <br><br>
 * Each Talk is associated with a 
 * {@link com.spring.mssql.models.Room Room}
 * in a bidirectional one-to-many relationship.
 * Each Room could have host more Talks, while each 
 * Talk could be taken in only one Room.
 * To perform this operation we have a 
 * {@link Talk#room room} attribute inside 
 * this class, which will store the Room where
 * the Talk has been taken.
 * @since 1.0.0
 * @author Marchetti Fabio
 **/
@Entity
@Table(name = "talks")
public class Talk {

	// Columns of the model Talk
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	private String title;

	private String description;

	private boolean published;
  
	@Column(nullable = false)
	@NotNull
	private long speaker_id;
	
	/**
	 * Our Talk entity will have a foreign key column 
	 * named room_id (specified inside the @JoinColumn)
	 * referring to the primary attribute id of our Room entity.
	 * @since 1.0.0
 	 * @author fforfabio
	 **/
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "room_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Room room;

	
	public Talk() {	
	}

	/**
	 * Constructor
	 * @param title of the talk
	 * @param description of the talk
	 * @param published if the talk is published
	 * @param speakerId the id of the speaker of the talk
	 * @param room the room where the talk will be taken
	 **/
	public Talk(String title, String description, boolean published, long speakerId, Room room) {
	    this.title = title;
	    this.description = description;
	    this.published = published;
	    this.speaker_id = speakerId;
	    this.room = room;
	}

	// Getter and setter
	public long getId() {
	  	return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
	  	return title;
	}

	public void setTitle(String title) {
	  	this.title = title;
	}

	public String getDescription() {
	  	return description;
	}

	public long getSpeaker_id() {
	  	return speaker_id;
	}

	public void setSpeaker_id(long speakerId) {
		 this.speaker_id = speakerId;
	}
	  
	public void setDescription(String description) {
		 this.description = description;
	}
	
	public boolean isPublished() {
		 return published;
	}
	
	public void setPublished(boolean isPublished) {
		 this.published = isPublished;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public String toString() {
		String textPublished = (published) ? " is already available." : " is not available.";
		return "Talk number " + id + ", with title \"" + title + "\" [description = \"" + description + "\"] "
				+ "hold by Speaker " + speaker_id + " in room " + room + textPublished;
	}

}
