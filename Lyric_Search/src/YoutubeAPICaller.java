import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class YoutubeAPICaller {
	private final static String API_BASE = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=";
	private final static String MY_KEY = "AIzaSyDvolSDCsRWRnKmxPU3WujTXhAJHjoP0lE";
	private final static String YoutubeURL = "https://www.youtube.com/embed/";
	private String videoLink;

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

	public String buildUrl(String title) {
		String searchTermNoSpaces = title.replaceAll(" ", "\\+");
		StringBuilder url = new StringBuilder(API_BASE + searchTermNoSpaces + "&type=video&key=" + MY_KEY);
		System.out.println(url.toString().length());
		return url.toString();
	}

	// HTTP GET request
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
