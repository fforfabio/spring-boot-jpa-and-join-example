package com.spring.mssql.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "tutorials")
public class Tutorial {

	// Columns of the model tutorial
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

	
	public Tutorial() {	
	}

	/**
	 * Constructor
	 * @param title of the tutorial
	 * @param description of the tutorial
	 * @param published if the tutorial is published
	 * @param speakerId the id of the speaker of the tutorial
	 **/
	public Tutorial(String title, String description, boolean published, long speakerId) {
	    this.title = title;
	    this.description = description;
	    this.published = published;
	    this.speaker_id = speakerId;
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
	
	@Override
	public String toString() {
		 return "Tutorial [num: " + id + ", title: " + title + ", description: " + description + ", "
		 		+ "published: " + published + "] taken by speaker " + this.speaker_id;
	}

}
