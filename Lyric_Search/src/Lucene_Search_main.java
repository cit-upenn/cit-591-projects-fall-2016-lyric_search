import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
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

public class Lucene_Search_main {

	public static void main(String[] args) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		Scanner in = new Scanner(System.in);
		boolean loop = true;
		Path docDir = Paths.get("Song_Index");
		Directory directory = FSDirectory.open(docDir);
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);

		while (loop) {
			System.out.println("search some lyrics or (q)uit: ");
			String input = in.nextLine();
			if (input.equalsIgnoreCase("q")) {
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println("See you next time!");
				in.close();
				System.exit(1);
			}
			// create query
			// can use~ after each term for fuzzy search
			String querystr = input;
			
			System.out.println(querystr);
			
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
				System.out.println((i + 1) + ". " + d.get("artist") + "\t" + d.get("title") + "\n");
			}
			System.out.println();
		}
		// reader can only be closed when there
		// is no need to access the documents any more.
		in.close();
		reader.close();
		directory.close();
	}
}
