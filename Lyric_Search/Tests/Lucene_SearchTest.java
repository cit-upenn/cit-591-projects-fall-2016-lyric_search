import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class Lucene_SearchTest {

	@Test
	public void test() {
		Lucene_Search search = null;
		try {
			search = new Lucene_Search();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		search.search(userQuery)
	}

}
