package lucene_index.index_tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

public class FileReaderTest {

	@Test
	public void testImportFile() {
		FileReader file = null;
		try {
			file = new FileReader("lyrics.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		assertEquals("lines will match the text file", 18, file.get);  
		
	}

}
