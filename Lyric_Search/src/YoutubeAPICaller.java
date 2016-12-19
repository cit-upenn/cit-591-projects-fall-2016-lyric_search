import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class handles the Youtube API. The API was used to find the youtube search results of
 * a given artist and song search and creates the youtube link of the video most closely
 * matching the top search result.
 * @author Saurav, Sameer, Jon
 *
 */
public class YoutubeAPICaller {
	private final static String API_BASE = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=";
	private final static String MY_KEY = "AIzaSyDvolSDCsRWRnKmxPU3WujTXhAJHjoP0lE";
	private final static String YoutubeURL = "https://www.youtube.com/embed/";
	private String videoLink;

	/**
	 * This method takes in a song title and song artist and returns a youtube link of the 
	 * given song by the given artist.
	 * @param songTitle Name of song
	 * @param songArtist Name of song's artist
	 * @return videoLink String containing youtube URL of song
	 * @throws Exception
	 */
	public String getYoutubeID (String songTitle, String songArtist) throws Exception{
		String artistAndSong = songArtist + " " + songTitle;
		String searchUrl = buildUrl(artistAndSong);
		String searchResponse = doApiCall(searchUrl);
		HashMap <String, String> ID = extractIdsFromResponse(searchResponse);
		String videoId = "";
		for(String element : ID.keySet()){
			if(element.toLowerCase().contains(songTitle.toLowerCase())){
				videoId = ID.get(element);
				break;
			}
		}
		videoLink = YoutubeURL + videoId;
		return videoLink;
	}

	/**
	 * This method takes in a song artist and title and creates the URL needed to make the API call
	 * @param artistAndTitle String containing song artist and title
	 * @return
	 */
	public String buildUrl(String artistAndTitle) {
		String searchTermNoSpaces = artistAndTitle.replaceAll(" ", "\\+");
		StringBuilder url = new StringBuilder(API_BASE + searchTermNoSpaces + "&type=video&key=" + MY_KEY);
		return url.toString();
	}

	// HTTP GET request
	/**
	 * This method conducts the youtube API call and returns the results of the call as a string
	 * @param url URl of API call made by buildUrl method
	 * @return String containing search results from API call
	 * @throws Exception
	 */
	public static String doApiCall(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString().length());
		return response.toString();
	}

	/**
	 * This method goes through the results of the API call and creates a hashmap containing the search
	 * results' titles as keys and videoIDs as values.
	 * @param response String containing the search results from API call
	 * @return results Hashmap containing titles and videoIDs of search results
	 */
	public HashMap <String, String> extractIdsFromResponse(String response) {
		HashMap <String, String> results = new HashMap <String, String>();
		JSONObject obj = new JSONObject(response);
		JSONArray theResults = obj.getJSONArray("items");

		for (int i = 0; i < theResults.length(); i++)
		{
			JSONObject single = theResults.getJSONObject(i);
			results.put(single.getJSONObject("snippet").getString("title"), single.getJSONObject("id").getString("videoId"));
		}

		return results;
	}


}
