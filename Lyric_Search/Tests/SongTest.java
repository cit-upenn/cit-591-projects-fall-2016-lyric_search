import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import lucene_index.FileReader;
import lucene_index.SongData;

public class SongTest {

	private FileReader file;
	private SongData songData; 
	
	@Before
	public void setup() {
		file = new FileReader("lyrics.txt");
		songData = new SongData(file);
		
	}
	
	@Test
	public void testConstructor() {
		Song song = new Song(songData.getTitle(0), songData.getArtist(0), songData.getAlbum(0), songData.getLyrics(0));
		assertNotNull("Title should not be null", song.getTitle());
		assertNotNull("artist should not be null", song.getArtist());
		assertNotNull("Album should not be null", song.getAlbum());
		assertNotNull("Lyrics should not be null", song.getLyrics());
	}

}
