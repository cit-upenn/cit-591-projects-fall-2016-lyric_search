import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import lucene_index.FileReader;
import lucene_index.SongData;

public class SongDataTest {

	private FileReader file;
	
	
	@Before 
	public void setup() {
		file = new FileReader("lyrics.txt");
	}
	
	
	@Test
	public void testMapsGetCorrectValue() {
		SongData song = new SongData(file);
		int size = file.getFile().size();
		assertEquals("The first artist should be Cam'ron","Cam'ron", song.getArtist(0));
		assertEquals("The first song song title should be Killa Cam", "Killa Cam", song.getTitle(0));
		assertEquals("The first album should be Purple Haze", "Purple Haze", song.getAlbum(0));
		assertNotNull("The first lyrics should not be null", song.getLyrics(0));
		
		assertEquals("The last artist should be Lil Wayne","Lil Wayne", song.getArtist(size - 2));
		assertEquals("The last song song title should be Hustler Musik", "Hustler Musik", song.getTitle(size - 2));
		assertEquals("The last album should be Tha Carter II", "Tha Carter II", song.getAlbum(size - 2));
		assertNotNull("The last lyrics should not be null", song.getLyrics(size - 2));
	}
	
	@Test
	public void testMapsAreNotEmpty() {
		SongData song = new SongData(file);
		assertNotNull("The artist map should not be null", song.getArtistMap());
		assertNotNull("The album map should not be null", song.getAlbumMap());
		assertNotNull("The song title map should not be null", song.getTitleMap());
		assertNotNull("The lyrics map should not be null", song.getLyricsMap());
	}
}
