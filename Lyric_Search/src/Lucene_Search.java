import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
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
 * @author jon
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

	public void search(String userQuery) {
		// create query
		// can use~ after each term for fuzzy search
		String querystr = args.length > 0 ? args[0] : userQuery;
		// first field specifies the default field to use
		// when no field is explicitly specified in the query.
		Query q = new QueryParser("lyrics", analyzer).parse(querystr);

		// search
		int hitsPerPage = 5;

		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		// 4. display results
		System.out.println();
		System.out.println("=====================================================================================");
		System.out.println("searching... " + querystr);
		System.out.println();
		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get("artist") + "\t" + d.get("title"));
		}
	}
}
