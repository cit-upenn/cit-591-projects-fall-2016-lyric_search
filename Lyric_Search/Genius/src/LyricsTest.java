import static org.junit.Assert.*;

import org.junit.Test;

public class LyricsTest {

	@Test
	public void test() {
		Lyrics lyrics = new Lyrics();
		try {
			String song = lyrics.getSong(1);
			System.out.println(song);
			assertEquals(2383, song.length());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
