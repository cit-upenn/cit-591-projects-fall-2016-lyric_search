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
		String artistAndSong = "Jay Z Can I Live";
		String URL = yt.buildUrl(artistAndSong);		
		assertEquals("URL Length should be 131", 131, URL.length());
	}
	
	@Test
	public void doApiCalltest() {
		String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=Jay+Z+Can+I+Live&type=video&key=AIzaSyDvolSDCsRWRnKmxPU3WujTXhAJHjoP0lE";
		String response = "";
		try {
			response = yt.doApiCall(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("Response Length should be 4790", 4790, response.length());
		
	}
	
	@Test
	public void extractIdsFromResponsetest() {
		String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=Jay+Z+Can+I+Live&type=video&key=AIzaSyDvolSDCsRWRnKmxPU3WujTXhAJHjoP0lE";
		String response = "";
		try {
			response = yt.doApiCall(url);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		HashMap <String, String> searchResults = yt.extractIdsFromResponse(response);
		assertNotNull("SearchResults should not be empty", searchResults);
		
	}

}
