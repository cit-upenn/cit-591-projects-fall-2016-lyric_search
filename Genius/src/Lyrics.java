
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.json.JSONObject;
/**
 * This class generates a text file with song lyrics for a specified number of songs.
 * It uses the Genius API to get the URL of a song and then uses the Jsoup API to parse the HTML document 
 * to get the lyrics for the URL.
 * Format: artistName|albumName|songTitle|lyrics\n** \n
 * @author Jonathan Way Huang, Sameer Kale, Saurav Sharma
 *
 */

public class Lyrics {
	// constant variables
	private final static String USER_AGENT = "something";
	private final static String MY_KEY = "Bearer FEelQry7txfJ1HkTdV8VYwmPEJB9aS11O2NeWEuAHoNcpR61WCYN27nMjVn7ZF6X"; 
	private final static String API_BASE = "https://api.genius.com/songs/";

	/**
	 * Given the URL for a specific song, this method gets the html document and parses it 
	 * to get the song's lyrics using the Jsoup api.
	 * @param string URL for song
	 * @return string lyrics
	 */
	private String getLyrics(String url) {
		Document doc;
		String lyrics = "";

		try {
			// get connection object using Jsoup connect method
			Connection con = Jsoup.connect(url); 

			// set User-Agent value to "something" in connection header
			con.header("User-Agent", USER_AGENT);

			// get html document from the server and store in doc
			doc = con.get();

			// select all html "p" elements that are children of the "div" element that has css class of 
			// "song_body-lyrics"
			// store in lyricsElement
			Elements lyricsElement = doc.select(".song_body-lyrics p");

			// get text from lyricsElement and store in lyrics
			lyrics = lyricsElement.text();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lyrics;
	}


	/**
	 * This method retrieves the song data for the specified song and returns a string object with the data
	 * @param num
	 * @return song data as artistName + "|" + albumName + "|" + songTitle + "|" + lyrics
	 * @throws Exception
	 */
	private String getSong(int num) throws Exception {
		URL url;
		String output = "";

		// append song number to create url string for the song
		String surl = API_BASE + num;

		// instantiate URL object with the given url string 
		url = new URL(surl);

		// open HTTP connection and store it in con object 
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		// set HTTP request method to "GET"
		con.setRequestMethod("GET");

		// add request header and authorization key 
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Authorization", MY_KEY);

		// create a BufferedReader object to read song data. BufferedReader is created from InputStreamReader 
		// which is created using InputStream from the con object.
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), 
				Charset.forName("UTF-8")));

		String inputLine;

		// create StringBuffer object to store server response
		StringBuffer serverResponse = new StringBuffer();

		// read data from BufferedReader and append it to serverResponse
		while ((inputLine = in.readLine()) != null) {
			serverResponse.append(inputLine);
		}

		// close the BufferedReader
		in.close();

		// construct JSONObject from serverResponse
		JSONObject obj = new JSONObject(serverResponse.toString());

		// use getJSONObject method to get song title, lyrics URL, album and artist name
		String songTitle = obj.getJSONObject("response").getJSONObject("song").getString("title");
		String lyricsURL = obj.getJSONObject("response").getJSONObject("song").getString("url");
		String albumName = obj.getJSONObject("response").getJSONObject("song").getJSONObject("album")
				.getString("name");
		String artistName = obj.getJSONObject("response").getJSONObject("song").getJSONObject("album")
				.getJSONObject("artist").getString("name");

		// call getLyrics to get lyrics for the song
		String lyrics = getLyrics(lyricsURL);

		// combine data into one string
		output = artistName + "|" + albumName + "|" + songTitle + "|" + lyrics + "\n** \n";

		return output;

	}

	/**
	 * This method creates a lyrics.txt file with the song data for the specified songs
	 * @param startNumber  
	 * @param endNumber
	 */
	private void getAllSongs(int startNumber, int endNumber) {

		String songData;

		PrintWriter out = null;		
		try {

			// instantiate the PrintWriter object and create lyrics.txt file
			out = new PrintWriter("lyrics.txt");

			// for loop to cycle through songs
			for (int num = startNumber; num <= endNumber; num++) {  
				System.out.println(num);
				try {
					// call getSong method to get song data, store in songData variable
					songData = getSong(num);

					// print data to lyrics.txt file
					out.write(songData);

					// print data to console
					System.out.println(songData);
				} catch (Exception e) {
					// catch exception thrown by getSong method 
					System.out.println("Song " + num + " not found. " + e.getMessage());
					System.out.println();
					System.out.println();
				} 
			}

			out.close();

			System.out.println("Scraping complete!");

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Main method that calls getAllSongs and outputs song data to the console
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			// create new lyrics object
			Lyrics lyrics = new Lyrics();
			
			// note: must specify which songs to get (range)
			lyrics.getAllSongs(1, 10000);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
