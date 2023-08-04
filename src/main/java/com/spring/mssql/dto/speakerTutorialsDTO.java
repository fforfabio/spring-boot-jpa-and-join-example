package com.spring.mssql.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


// This annotation will ignore the specified properties
@JsonIgnoreProperties(ignoreUnknown = true)

// This annotation avoid the insertion of some properties in the JSON
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class speakerTutorialsDTO {
	// DTO for join between Speaker and Tutorial
	
	private long speakerId;
	private String speakerLastName;
	private long tutorialId;
	private String tutorialTitle;
	private String tutorialDescription;
	private int numTutorials;
	private int publishedTutorials;
	
	
	/**
	 * Constructor for queries getSpeakerTutorialsWithJoinDTOJPQL, getSpeakerTutorialsWithJoinDTONativeQuery
	 * and getAllJoinDTONativeQuery
	 * @param speakerLastName last name of the speaker
	 * @param tutorialTitle title of the tutorial
	 * @param tutorialDescription description of the tutorial
	 * @param speakerId id of the speaker
	 * @param tutorialId id of the tutorial
	 **/
	public speakerTutorialsDTO(String speakerLastName, String tutorialTitle, String tutorialDescription, long speakerId, long tutorialId) {
		this.speakerLastName = speakerLastName;
		this.tutorialTitle = tutorialTitle;
		this.tutorialDescription = tutorialDescription;
		this.speakerId = speakerId;
		this.tutorialId = tutorialId;
	}
	
	
	/**
	 * Constructor for query getTutorialsCount
	 * @param speakerLastName last name of the speaker
	 * @param speakerId id of the speaker
	 * @param numTutorials number of tutorials for the speaker
	 * @param tutPublished number of published tutorials for the speaker
	 **/
	public speakerTutorialsDTO(String speakerLastName, long speakerId, int numTutorials, int publishedTutorials) {
		this.speakerLastName = speakerLastName;
		this.speakerId = speakerId;
		this.numTutorials = numTutorials;
		this.publishedTutorials = publishedTutorials;
	}
	
	// Getter and setter
	public String getSpeakerLastName() {
		return speakerLastName;
	}

	public void setSpeakerLastName(String speakerLastName) {
		this.speakerLastName = speakerLastName;
	}

	public String getTutorialDescription() {
		return tutorialDescription;
	}

	public void setTutorialDescription(String tutorialDescription) {
		this.tutorialDescription = tutorialDescription;
	}

	public String getTutorialTitle() {
		return tutorialTitle;
	}

	public void setTutorialTitle(String tutorialTitle) {
		this.tutorialTitle = tutorialTitle;
	}

	public long getSpeakerId() {
		return speakerId;
	}

	public void setSpeakerId(long speakerId) {
		this.speakerId = speakerId;
	}

	public long getTutorialId() {
		return tutorialId;
	}

	public void setTutorialId(long tutorialId) {
		this.tutorialId = tutorialId;
	}

	public int getNumTutorials() {
		return numTutorials;
	}

	public void setNumTutorials(int numTutorials) {
		this.numTutorials = numTutorials;
	}

	public int getPublishedTutorials() {
		return publishedTutorials;
	}

	public void setPublishedTutorials(int publishedTutorials) {
		this.publishedTutorials = publishedTutorials;
	}
	
	@Override
	public String toString() {
		return "Speaker " + this.speakerLastName + " with id: " + String.valueOf(this.speakerId) + 
				"\nTutorial " + this.tutorialId + ": '" + this.tutorialTitle + 
				"' with description '" + this.tutorialDescription + "'\n";
	}
}
