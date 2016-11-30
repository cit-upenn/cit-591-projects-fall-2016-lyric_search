import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSoupTester {

	public static void main(String[] args) {
		
		ArrayList <String> urls = new ArrayList <String>();
		
		
		Document doc;
		try {
			doc = Jsoup.connect("http://www.azlyrics.com/").get();
			
			//System.out.println(doc.baseUri());
			
			String baseUri = doc.baseUri();
			
			//System.out.println(doc.toString());
			
			Elements letterURLs = doc.select(".btn-group a");
			
			for (Element letterURL : letterURLs) {
				//System.out.println(letterURL.toString());
				String url = letterURL.attr("href");
				
				urls.add(url);
				
			}
			
			//for (String url : urls) {
				//System.out.println(url);
			//}
			
			doc = Jsoup.connect(urls.get(0)).get();
			//System.out.println(doc.toString());
			Elements aURLs = doc.select(".col-sm-6 a");
			System.out.println(aURLs.toString());
			
			// adding test git comment
			
			
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
