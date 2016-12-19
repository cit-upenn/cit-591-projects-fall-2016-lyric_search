import static org.junit.Assert.*;

import org.junit.Test;

import lucene_index.FileReader;

public class FileReaderTest {

	@Test
	public void testImportFile() {
		FileReader file = new FileReader("lyrics.txt");
		assertEquals("imported file should have 2000 lines", 2000, file.getFile().size());
	}

}
