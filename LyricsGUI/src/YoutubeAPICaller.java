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
	private String songName;
	
	public YoutubeAPICaller(){
		
	}
	
//	public void setSongName(String song) {
//		this.songName = song;
//	}

	
//	public ArrayList<String> extractIdsFromResponse(String response) {
//		ArrayList<String> ids = new ArrayList<String>();
//		JSONObject obj = new JSONObject(response);
//		JSONArray theResults = obj.getJSONArray("results");
//		
//		for (int i = 0; i < theResults.length(); i++)
//		{
//		    JSONObject single = theResults.getJSONObject(i);
//		    ids.add("" + single.getInt("id"));
//		}
//		return ids;
//	}
	
	public HashMap <String, String> extractIdsFromResponse(String response) {
		HashMap <String, String> results = new HashMap <String, String>();
		JSONObject obj = new JSONObject(response);
		JSONArray theResults = obj.getJSONArray("totalResults");
		
		for (int i = 0; i < theResults.length(); i++)
		{
		    JSONObject single = theResults.getJSONObject(i);
		    results.put(single.getString("title"), single.getString("videoId"));
		}
		
		return results;
	}
	
	public String buildUrl(String title) {
		songName = title;
		StringBuilder url = new StringBuilder(API_BASE + songName + "&type=video&key=" + MY_KEY);
		return url.toString();
	}
	
	// HTTP GET request
	public static String doApiCall(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		return response.toString();
	}
	

}
