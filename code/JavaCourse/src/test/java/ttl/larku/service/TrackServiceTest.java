package ttl.larku.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ttl.larku.domain.Track;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TrackServiceTest {

	private TrackService trackService;
	
	@BeforeEach
	public void setup() {
		trackService = new TrackService();
	}
	
	@Test
	public void testCreateTrack() {
		Track newTrack = new Track("The Shadow Of Your Smile", Arrays.asList("Big John Patton"),
				"Let 'em Roll", "06:15", "1965-10-20");

		newTrack  = trackService.createTrack(newTrack);

		Track result = trackService.getTrack(newTrack.getId());
		
		assertTrue(result.getTitle().contains(newTrack.getTitle()));
		assertEquals(1, trackService.getAllTracks().size());
	}
	
	@Test
	public void testDeleteTrack() {
		Track track1 = new Track("The Shadow Of Your Smile", Arrays.asList("Big John Patton"),
				"Let 'em Roll", "06:15", "1965-10-20");
		track1 = trackService.createTrack(track1);
		Track track2 = new Track("I'll Remember April", Arrays.asList("Jim Hall", "Ron Carter"),
				"Alone Together", "05:54", "1972-08-19");
		track2 = trackService.createTrack(track2);
		
		assertEquals(2, trackService.getAllTracks().size());
		
		boolean done = trackService.deleteTrack(track1.getId());
		assertTrue(done);
		
		assertEquals(1, trackService.getAllTracks().size());
		assertTrue(trackService.getAllTracks().get(0).getTitle().contains("April"));
	}

	@Test
	public void testDeleteNonExistentTrack() {
		Track track1 = new Track("The Shadow Of Your Smile", Arrays.asList("Big John Patton"),
				"Let 'em Roll", "06:15", "1965-10-20");
		track1 = trackService.createTrack(track1);
		Track track2 = new Track("I'll Remember April", Arrays.asList("Jim Hall", "Ron Carter"),
				"Alone Together", "05:54", "1972-08-19");
		track2 = trackService.createTrack(track2);
		
		assertEquals(2, trackService.getAllTracks().size());
		
		boolean done = trackService.deleteTrack(9999);
		assertFalse(done);
		
		assertEquals(2, trackService.getAllTracks().size());
	}
	
	@Test
	public void testUpdateTrack() {
		Track track1 = new Track("The Shadow Of Your Smile", Arrays.asList("Big John Patton"),
				"Let 'em Roll", "06:15", "1965-10-20");
		track1 = trackService.createTrack(track1);

		assertEquals(1, trackService.getAllTracks().size());
		
		track1.setTitle("A Shadowy Smile");
		boolean done = trackService.updateTrack(track1);
		assertTrue(done);
		
		assertEquals(1, trackService.getAllTracks().size());
		assertTrue( trackService.getAllTracks().get(0).getTitle().contains("Shadowy"));
	}
}
