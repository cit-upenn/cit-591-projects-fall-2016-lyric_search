import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import lucene_index.FileReader;
import lucene_index.SongData;

public class SongDataTest {

	private FileReader file;
	private SongData song; 
	
	@Before 
	public void setup() {
		file = new FileReader("lyrics.txt");
	}
	
	@Test
	public void testConstructor() {
		SongData testSong = new SongData(file);
		
	}
	
	@Test
	public void testGenerateData() {
		song = new SongData(file);
		assertEquals("The first artist should be Cam'ron","Cam'ron", song.getArtist(0));
		assertEquals("The first song song title should be Killa Cam", "Killa Cam", song.getTitle(0));
		assertEquals("The first album should be Purple Haze", "Purple Haze", song.getAlbum(0));
		assertNotNull("The first lyrics should not be null", song.getLyrics(0));
		
		assertEquals("The last artist should be Cam'ron","Cam'ron", song.getArtist(0));
		assertEquals("The last song song title should be Rockin' and Rollin'", "Rockin' and Rollin'", song.getTitle(0));
		assertEquals("The last album should be Confessions of Fire", "Confessions of Fire", song.getAlbum(0));
		assertNotNull("The last lyrics should not be null", song.getLyrics(18));
	}

}
