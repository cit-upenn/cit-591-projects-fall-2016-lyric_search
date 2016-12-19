import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSoupTester {
	
	//Create atrist.txt file containing name of the artist and URL 
	static void createArtistURLFile() {
		Document doc;
		ArrayList<String> letterURLArray = new ArrayList<String>();
		PrintWriter pw = null;
		String artistName = "";
		String artistNameUrl = "";
		try {
			
			pw = new PrintWriter(new FileWriter("artist.txt"));
			// connect to website, store in doc object
			doc = Jsoup.connect("http://www.azlyrics.com/").get();
			// System.out.println(doc.toString());

			// store "http://www.azlyrics.com" in String baseUri
			String baseUri = doc.baseUri();
			// System.out.println(baseUri);

			// select element for a-z links
			Elements letterURLs = doc.select(".btn-group a");

			for (Element letterURL : letterURLs) {
				// System.out.println(letterURL.toString());
				String url = letterURL.attr("href");

				// add url to urls ArrayList
				letterURLArray.add(url);
			}

			for (String letterURLString : letterURLArray) {

				doc = Jsoup.connect(letterURLString).get();
				// System.out.println(doc.toString());
				Elements artistURLs = doc.select(".col-sm-6 a");
				// System.out.println(aURLs.toString());

				for (Element artistURL : artistURLs) {
					// System.out.println(aURL.toString());
					artistName = artistURL.html();
					String a = artistURL.attr("href");
					a = baseUri + "/" + a;
					artistNameUrl = artistName + "|" + a;
					pw.println(artistNameUrl);
				}
				TimeUnit.MILLISECONDS.sleep(200);
			}
			pw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	//For each URL in artist.txt file, get song and lyrics and create lyrics file
	static void createAlbumAndSongURLFile() {
		PrintWriter out = null;
		String artistNamrUrl="";
		String[] stringArray = null;
		String artistName="";
		String artistURL="";
		String albumName = "";
		String songName = "";
		String songURL = "";
		String outputString;
		Document doc;
		String baseURI = "http://www.azlyrics.com";
		try {
			File inputFile = new File("artist.txt");
			Scanner in = new Scanner(inputFile);
			out = new PrintWriter("album.txt");
			
			while(in.hasNext()){
				artistNamrUrl = in.nextLine();
				System.out.println(artistNamrUrl);
				stringArray = artistNamrUrl.split("\\|");
				
				artistName = stringArray[0];
				artistURL = stringArray[1];
				
				System.out.println(artistName);
				
				doc = Jsoup.connect(artistURL).get();
				//System.out.println(doc.toString());
				
				// to select using id: use # (use . to select using style)
				Element divElement = doc.select("#listAlbum").get(0);

				Elements albums = divElement.select("div, a"); // get all <div> and
				// <a> tags
				// for each album, get album name in <b> tag and href in <a> tag
				for (Element element : albums) {
					// if div element then get album name in <b> tag
					System.out.println(element.toString());
					if (element.tagName() == "div") {
						// if div html has other songs then album name = "other
						// songs"
						if (element.html().contains("other")) {
							albumName = "other songs";
						} else {
							Elements elements = element.getElementsByTag("b");
							Element bTag = elements.get(0); // this is the b tag.
							albumName = bTag.html(); // to get string inside bTag
						}
					} else { // this is <a> tag, get href and song name
						songURL = element.attr("href");
						if (songURL.contains("/lyrics/") && songURL.length() > 0) {
							songName = element.html();
							//url has two dots in the front. need to remove them and add base url
							//../lyrics/aaliyah/intro.html 
							songURL = baseURI + songURL.substring(2, songURL.length());
							
							outputString = artistName + "|" + albumName + "|"  + songName + "|" + songURL ;
							out.println(outputString);
						}
					}
				}	
				break;  //getting only for one artist. To get all, remove break -- CAREFUL!!! blocks network
			}
			out.close();
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//get lyrics for each song URL in album.txt file
	static void createLyricsFile() {
		PrintWriter out = null;
		String inputString;
		String[] stringArray;
		String songName = "";
		String songURL = "";
		String artistName, albumName, lyrics;
		String outputString;
		Document doc;
		try {
			File inputFile = new File("album.txt");
			Scanner in = new Scanner(inputFile);
			out = new PrintWriter("lyrics.txt");
			
			while(in.hasNext()){
				inputString = in.nextLine();  //artistName + "|" + albumName + "|"  + songName + "|" + songURL 
				stringArray = inputString.split("\\|");   // split string
				artistName = stringArray[0];
				albumName =  stringArray[1];
				songName =  stringArray[2];
				songURL =  stringArray[3];
				
				System.out.println(songURL);
				
				doc = Jsoup.connect(songURL).get();

				//System.out.println(doc.toString());
				Elements allDivElements = doc.select("div.col-xs-12.col-lg-8.text-center");
				
				Element parentDivElement = allDivElements.get(0); // get first <div>
				//how to use multiple classes to select element - see answer in link below
				//http://stackoverflow.com/questions/21967413/jsoup-get-element-with-multiple-classes
				//System.out.println(parentDivElement.toString());

				//get all children div element of parentDivElement
				Elements childDivElements = parentDivElement.getElementsByTag("div");
				//System.out.println(childDivElements.size());
				Element divElement = childDivElements.get(8);  //get eight div element that has lyrics
				
				lyrics = divElement.text();
				
				outputString = artistName + "|" + albumName + "|"  + songName + "|" + lyrics ;
				out.println(outputString);
				break; // remove break to get all
			}	
			out.close();
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	public static void main(String[] args) {
		
		//createArtistURLFile();   //artist.txt - Don't have to create this - file already created
		//createAlbumAndSongURLFile();  //album.txt - file for one artist attached
		//createLyricsFile();   //lyrics.txt  - file for one song attached
	}
}
		
