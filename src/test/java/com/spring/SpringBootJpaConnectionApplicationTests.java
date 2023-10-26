package com.spring;

import java.util.Optional;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.mssql.models.Room;
import com.spring.mssql.models.Speaker;
import com.spring.mssql.models.Talk;
import com.spring.mssql.repositories.SpeakerRepository;
import com.spring.mssql.repositories.TalkRepository;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringBootSqlServerApplicationTests {

	TalkRepository talkRepository;

	SpeakerRepository speakerRepository;
	
	// With this constructor we will avoid the use of the @Autowired annotation
	public SpringBootSqlServerApplicationTests(TalkRepository talkRepository, SpeakerRepository speakerRepository) {
			this.talkRepository = talkRepository;
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
	public void saveTalk() {
		Talk talk = new Talk("Test in JUnit", "How to run a JUnit test", true, new Room());
		Talk save = talkRepository.save(talk);
		Optional<Talk> talkById = talkRepository.findById(save.getId());
		assert(talkById.isPresent());
		talkRepository.delete(talk);
	}
	
	@Test
	@Order(4)
	public void checkTalkEntity() {
		Talk talk = new Talk("Test in JUnit part 2", "How to run a JUnit test part 2", false, new Room());
		Talk save = talkRepository.save(talk);
		Optional<Talk> talkById = talkRepository.findById(save.getId());
		assertEquals("Test in JUnit part 2", talkById.get().getTitle(), "The title of the talk must be 'Test in JUnit part 2'");
		assertEquals("How to run a JUnit test part 2", talkById.get().getDescription(), "The description of the talk must be 'How to run a JUnit test part 2'");
		assertEquals(false, talkById.get().isPublished(), "The talk must NOT be published, but it is.");
		talkRepository.delete(talk);
	}

}
