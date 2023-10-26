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
 * @author fforfabio
 **/
@Entity
@Table(name = "talks")
public class Talk {

	// Columns of the model Talk
	/**
	 * Identifier of the talk.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * Title of the talk.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	@NotNull
	private String title;

	/**
	 * Description of the talk.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	private String description;

	/**
	 * Flag to see if the talk has been published.
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	private boolean published;
	
	
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
	 * @since 1.0.0
 	 * @author fforfabio
	 **/
	public Talk(String title, String description, boolean published, Room room) {
	    this.title = title;
	    this.description = description;
	    this.published = published;
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
				+ "hold in room " + room + textPublished;
	}

}
