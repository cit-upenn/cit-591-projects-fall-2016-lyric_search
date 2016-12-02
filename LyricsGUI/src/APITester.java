import java.util.ArrayList;
import java.util.HashMap;

public class APITester {

	public static void main(String[] args) throws Exception {
		
		YoutubeAPICaller yt = new YoutubeAPICaller();
		
		String searchInput = "where+it's+at";
		String searchUrl = yt.buildUrl(searchInput);
		String searchResponse = yt.doApiCall(searchUrl);
		System.out.println(searchResponse);
		HashMap <String, String> ID = yt.extractIdsFromResponse(searchResponse);
		String videoId = "";
		for(String element : ID.keySet()){
			if(element.contains(searchInput.replaceAll("+", " "))){
				videoId = ID.get(element);
				System.out.println(videoId);
				break;
			}
		}

	}
	
}