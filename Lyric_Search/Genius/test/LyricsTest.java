import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

public class LyricsTest {

	private Lyrics lyrics;
	
	@Before
	public void setup() {
		lyrics = new Lyrics();
	}
	
	@Test
	public void testGetSong() {
		try {
			String song = lyrics.getSong(1);
			assertEquals("Song string length should be 2283", 2383, song.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetLyrics() {
		try {
			String testURL = "http://genius.com/Camron-killa-cam-lyrics";
			String songLyrics = lyrics.getLyrics(testURL);
			assertEquals("Song lyrics string length should be 2348", 2348, songLyrics.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}