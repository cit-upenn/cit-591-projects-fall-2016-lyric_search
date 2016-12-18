import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * This is a search class that has all of the searching logic
 * 
 * @author Jonathan Huang, Sameer Kale, Saurav Sharma
 *
 */
public class Lucene_Search {
	private StandardAnalyzer analyzer;
	private Path docDir;
	private Directory directory;
	private IndexReader reader;
	private IndexSearcher searcher;

	/**
	 * constructor
	 * 
	 * @throws IOException
	 */
	public Lucene_Search() throws IOException {
		analyzer = new StandardAnalyzer();
		docDir = Paths.get("Song_Index");
		directory = FSDirectory.open(docDir);
		reader = DirectoryReader.open(directory);
		searcher = new IndexSearcher(reader);
	}

	public HashMap<Integer, Song> search(String userQuery) throws ParseException, IOException {
		// create query
		// can use~ after each term for fuzzy search
		String querystr = userQuery;
		// first field specifies the default field to use
		// when no field is explicitly specified in the query.
		Query q = new QueryParser("lyrics", analyzer).parse(querystr); // throws ParseException

		// search
		int hitsPerPage = 5;
		HashMap<Integer,Song> searchResults = new HashMap<Integer, Song>();
		
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
		searcher.search(q, collector); // throws IOException
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			Song song = new Song(d.get("title"), d.get("artist"), d.get("album"), d.get("lyrics"));
			searchResults.put(i, song);
		}
		return searchResults;
	}
	
	/**
	 * closes the reader and directory after we are done using them.
	 * Only done once we do not need the documents anymore.  
	 * @throws IOException 
	 */
	public void close() throws IOException {
		reader.close(); 
		directory.close();
	}
}
