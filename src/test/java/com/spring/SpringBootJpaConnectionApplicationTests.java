package com.spring;

import java.util.Optional;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.mssql.models.Room;
import com.spring.mssql.models.Speaker;
import com.spring.mssql.models.Talk;
import com.spring.mssql.repository.SpeakerRepository;
import com.spring.mssql.repository.TalkRepository;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringBootSqlServerApplicationTests {

	TalkRepository tutorialRepository;

	SpeakerRepository speakerRepository;
	
	// With this constructor we will avoid the use of the @Autowired annotation
	public SpringBootSqlServerApplicationTests(TalkRepository tutorialRepository, SpeakerRepository speakerRepository) {
			this.tutorialRepository = tutorialRepository;
			this.speakerRepository = speakerRepository;
	}
	
	@Test
	@Order(1)
	public void saveSpeaker() {
		Speaker speaker = new Speaker("fforfabio", "TestJunit1", 27);
		Speaker s = speakerRepository.save(speaker);
		assertNotNull(speakerRepository.findById(s.getId()));
        speakerRepository.delete(speaker);
	}
	
	@Test
	@Order(2)
	public void checkSpeakerName() {
		Speaker speaker = new Speaker("fforfabio", "TestJunit2", 27);
		Speaker save = speakerRepository.save(speaker);
		Optional<Speaker> speakerById = speakerRepository.findById(save.getId());
		assertEquals("Fabio", speakerById.get().getFirstName(), "The first name of the speaker must be 'fforfabio'");
		assertEquals("TestJunitNum4", speakerById.get().getLastName(), "The last name of the speaker must be 'TestJunit2'");
		assertEquals("Speaker: fforfabio TestJunit2 id: " + String.valueOf(save.getId()), speakerById.get().toString(), "Incorrect toString() conversion.");
		speakerRepository.delete(speaker);
	}
	
	@Test
	@Order(3)
	public void saveTutorial() {
		Talk tutorial = new Talk("Test in JUnit", "How to run a JUnit test", true, 1, new Room());
		Talk save = tutorialRepository.save(tutorial);
		Optional<Talk> tutorialById = tutorialRepository.findById(save.getId());
		assert(tutorialById.isPresent());
		tutorialRepository.delete(tutorial);
	}
	
	@Test
	@Order(4)
	public void checkTutorialEntity() {
		Talk tutorial = new Talk("Test in JUnit part 2", "How to run a JUnit test part 2", false, 2, new Room());
		Talk save = tutorialRepository.save(tutorial);
		Optional<Talk> tutorialById = tutorialRepository.findById(save.getId());
		assertEquals("Test in JUnit part 2", tutorialById.get().getTitle(), "The title of the tutorial must be 'Test in JUnit part 2'");
		assertEquals("How to run a JUnit test part 2", tutorialById.get().getDescription(), "The description of the tutorial must be 'How to run a JUnit test part 2'");
		assertEquals(false, tutorialById.get().isPublished(), "The tutorial must NOT be published, but it is.");
		assertEquals(2, tutorialById.get().getSpeaker_id(), "The speaker_id of the tutorial must be '2'");
		tutorialRepository.delete(tutorial);
	}

}
