package lucene_index;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * This class creates a searchable index
 * 
 * @author jon
 *
 */
public class Indexer {
	public static void main(String[] args) throws IOException, ParseException {
		FileReader file = new FileReader("song_list.txt");
		SongData songData = new SongData(file);
//		StandardAnalyzer analyzer = new StandardAnalyzer();
/*
 * choose to make index in ram or store in a directory
 */
		Path docDir = Paths.get("Song_Index");
		Directory directory = FSDirectory.open(docDir);
//		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig();
		IndexWriter indexWriter = new IndexWriter(directory, config);
		// makes sure the index is empty before creating a new index
		// prevents duplicates in index
		indexWriter.deleteAll();
		// loop through song data to create the docs
		System.out.println("indexing...");
		for (Integer key : songData.getKeySet()) {
			Document doc = new Document();
			String title = songData.getTitle(key);
			String album = songData.getAlbum(key);
			String artist = songData.getArtist(key);
			String lyrics = songData.getLyrics(key);
			// adds each field to the document
			doc.add(new TextField("title", title, Field.Store.YES));
			doc.add(new TextField("album", album, Field.Store.YES));
			doc.add(new TextField("artist", artist, Field.Store.YES));
			doc.add(new TextField("lyrics", lyrics, Field.Store.YES));
			// write the document to the index
			indexWriter.addDocument(doc);
		}
		
		indexWriter.close();
		System.out.println("Indexing Complete!");
	}
}
