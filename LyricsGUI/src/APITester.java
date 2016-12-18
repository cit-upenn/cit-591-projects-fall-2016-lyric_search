import java.util.ArrayList;
import java.util.HashMap;

public class APITester {

	private final static String YoutubeURL = "https://www.youtube.com/watch?v=";
//	private static String videoLink;
//	
//	public void setVideoLink(String videoLink) {
//		this.videoLink = videoLink;
//	}

	public static void main(String[] args) throws Exception {
		
		YoutubeAPICaller yt = new YoutubeAPICaller();
		
		String videoLink = "";
		String searchInput = "where+it's+at";
		String searchInputNoSpace = searchInput.replaceAll("\\+", " ");
		System.out.println(searchInputNoSpace);
//		System.out.println(searchInputNoSpace);
		String searchUrl = yt.buildUrl(searchInput);
		String searchResponse = yt.doApiCall(searchUrl);
		System.out.println(searchResponse);
		
		HashMap <String, String> ID = yt.extractIdsFromResponse(searchResponse);
		String videoId = "";
		for(String element : ID.keySet()){
			if(element.toLowerCase().contains(searchInputNoSpace)){
				videoId = ID.get(element);
				System.out.println(videoId);
				break;
			}
		}
		videoLink = YoutubeURL + videoId;
		System.out.println(videoLink);
	}
	
}