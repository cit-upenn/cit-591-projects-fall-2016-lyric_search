package lucene_index;
import java.io.File;
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
 * @author Jonathan Huang, Sameer Kale, Saurav Sharma 
 *
 */
public class Indexer {
	public static void main(String[] args) throws IOException, ParseException {
//		FileReader file = new FileReader("song_list.txt");
//		FileReader file = new FileReader("demo_lyrics_10k.txt");
		FileReader file = new FileReader("lyrics.txt");
//		FileReader file = new FileReader("lyrics10000new.txt");
//		FileReader file = new FileReader("lyrics462.txt");		
		
		
		SongData songData = new SongData(file);
//		StandardAnalyzer analyzer = new StandardAnalyzer();
/*
 * choose to make index in ram or store in a directory
 */
		Path docDir = Paths.get("Song_Index"); 
		Directory directory = FSDirectory.open(docDir);
		File dir = new File("Song_Index");
		// deletes all files in the directory so each indexing is a fresh start
		for(File files: dir .listFiles()) { 
		    if (!files.isDirectory()) 
		        files.delete();
		}
//		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig();
		IndexWriter indexWriter = new IndexWriter(directory, config);
		// makes sure the index has no docs before creating a new index
		// prevents duplicates in index
		indexWriter.deleteAll();
		indexWriter.commit();
		
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
			doc.add(new TextField("lyrics", lyrics, Field.Store.NO));
			// write the document to the index
			indexWriter.addDocument(doc);
		}
		System.out.println(indexWriter.numDocs());
		
		indexWriter.close();
		System.out.println("Indexing Complete!");
	}
}
