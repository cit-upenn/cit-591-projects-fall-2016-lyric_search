import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class YoutubeAPICallerTest {

	private YoutubeAPICaller yt;
	
	@Before
	public void setup(){
		yt = new YoutubeAPICaller();	
	}
	
	@Test
	public void buildUrltest() {
		String artistAndSong = "Eminem Lose Yourself";
		String URL = yt.buildUrl(artistAndSong);		
		assertEquals("URL length should be 135", 135, URL.length());
	}
	
	@Test
	public void doApiCalltest() {
		String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=Eminem+Lose+Yourself&type=video&key=AIzaSyDvolSDCsRWRnKmxPU3WujTXhAJHjoP0lE";
		String response = "";
		try {
			response = yt.doApiCall(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("Response length should be 4994", 4994, response.length());
		
	}
	
	@Test
	public void extractIdsFromResponsetest() {
		String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=Eminem+Lose+Yourself&type=video&key=AIzaSyDvolSDCsRWRnKmxPU3WujTXhAJHjoP0lE";
		String response = "";
		try {
			response = yt.doApiCall(url);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		HashMap <String, String> searchResults = yt.extractIdsFromResponse(response);
		assertNotNull("SearchResults should not be empty", searchResults);
		
	}
	
	@Test
	public void getYoutubeIDtest() {
		String artist = "Eminem";
		String song = "Lose Yourself";
		String videoLink = "";
		try {
			videoLink = yt.getYoutubeID(song, artist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("VideoLink length should be 41", 41, videoLink.length());
		
	}
	
	

}
