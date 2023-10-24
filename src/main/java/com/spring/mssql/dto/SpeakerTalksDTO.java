package com.spring.mssql.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * DTO for join between Speaker and Talk.
 * The {@link com.fasterxml.jackson.annotation.JsonIgnoreProperties @JsonIgnoreProperties}
 * annotation, as write on this class, ignore 
 * the specified properties.
 * The {@link com.fasterxml.jackson.annotation.JsonInclude @JsonInclude}
 * annotation, as write on this class, avoid the 
 * insertion of some properties in the returned JSON.
 * @since 1.0.0
 * @author fforfabio 
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SpeakerTalksDTO {
	
	private long speakerId;
	private String speakerLastName;
	private long talkId;
	private String talkTitle;
	private String talkDescription;
	private int numTalks;
	private int publishedTalks;
	
	
	/**
	 * Constructor for queries getSpeakerTalksWithJoinDTOJPQL, getSpeakerTalksWithJoinDTONativeQuery
	 * and getAllJoinDTONativeQuery
	 * @param speakerLastName last name of the speaker
	 * @param talkTitle title of the tals
	 * @param talkDescription description of the talk
	 * @param speakerId id of the speaker
	 * @param talkId id of the talk
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	public SpeakerTalksDTO(String speakerLastName, String talkTitle, String talkDescription, long speakerId, long talkId) {
		this.speakerLastName = speakerLastName;
		this.talkTitle = talkTitle;
		this.talkDescription = talkDescription;
		this.speakerId = speakerId;
		this.talkId = talkId;
	}
	
	
	/**
	 * Constructor for query getTalksCount
	 * @param speakerLastName last name of the speaker
	 * @param speakerId id of the speaker
	 * @param numTalks number of talks for the speaker
	 * @param tutPublished number of published talks for the speaker
	 * @since 1.0.0
	 * @author fforfabio
	 **/
	public SpeakerTalksDTO(String speakerLastName, long speakerId, int numTalks, int publishedTalks) {
		this.speakerLastName = speakerLastName;
		this.speakerId = speakerId;
		this.numTalks = numTalks;
		this.publishedTalks = publishedTalks;
	}
	
	// Getter and setter
	public String getSpeakerLastName() {
		return speakerLastName;
	}

	public void setSpeakerLastName(String speakerLastName) {
		this.speakerLastName = speakerLastName;
	}

	public String getTalkDescription() {
		return talkDescription;
	}

	public void setTalkDescription(String talkDescription) {
		this.talkDescription = talkDescription;
	}

	public String getTalkTitle() {
		return talkTitle;
	}

	public void setTalkTitle(String talkTitle) {
		this.talkTitle = talkTitle;
	}

	public long getSpeakerId() {
		return speakerId;
	}

	public void setSpeakerId(long speakerId) {
		this.speakerId = speakerId;
	}

	public long getTalkId() {
		return talkId;
	}

	public void setTalkId(long talkId) {
		this.talkId = talkId;
	}

	public int getNumTalks() {
		return numTalks;
	}

	public void setNumTalks(int numTalks) {
		this.numTalks = numTalks;
	}

	public int getPublishedTalks() {
		return publishedTalks;
	}

	public void setPublishedTalks(int publishedTalks) {
		this.publishedTalks = publishedTalks;
	}
	
	@Override
	public String toString() {
		return "Speaker " + this.speakerLastName + " with id: " + String.valueOf(this.speakerId) + 
				"\nTalk " + this.talkId + ": '" + this.talkTitle + 
				"' with description '" + this.talkDescription + "'\n";
	}
}
