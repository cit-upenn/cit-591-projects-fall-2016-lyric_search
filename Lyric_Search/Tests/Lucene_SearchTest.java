import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Before;
import org.junit.Test;

public class Lucene_SearchTest {
	
	private Lucene_Search search;
	HashMap<Integer, Song> results;
	
	@Before
	public void setup() throws IOException {
		search = new Lucene_Search();
		results = new HashMap<>();
	}
	
	@Test
	public void testSearchIsNotNull() throws ParseException, IOException {
		Lucene_Search search = null;
		try {
			search = new Lucene_Search();
		} catch (IOException e) {
			e.printStackTrace();
		}
		results = search.search("invite me");
		assertNotNull("results is not empty", results);
	}
	
	@Test
	public void testSearch() throws ParseException, IOException {
		HashMap<Integer, Song> results = search.search("invite me");
		assertEquals("The artist should be Jay Z", "Jay Z", results.get(0).getArtist());
		assertEquals("The song title should be Can I Live", "Can I Live", results.get(0).getTitle());
	}
	
//	@Test 
//	public void testClose() throws IOException, ParseException {
//		search.close();
//		results = search.search("invite me");
////		assertExpected()
//		assertEquals("results should be null", results);
//	}
}
