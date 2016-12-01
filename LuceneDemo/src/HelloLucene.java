import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class HelloLucene {
	public static void main(String[] args) throws IOException, ParseException {
		FileReader file = new FileReader("song_list.txt");
		/*
		 * =============================
		 * stop list with this code
		 * stop list code FileReader stoplist = new FileReader("stop_list.txt");
		 * 
		 * CharArraySet stopset = new CharArraySet(stoplist.getFile().size(),
		 * true); for (String words : stoplist.getFile()) { stopset.add(words);
		 * }
		 */

		SongData songData = new SongData(file);

		// 0. Specify the analyzer for tokenizing text.
		// The same analyzer should be used for indexing and searching
		/*
		 * StandardAnalyzer analyzer = new StandardAnalyzer(stopset);
		 */
		StandardAnalyzer analyzer = new StandardAnalyzer();
		// 1. create the index
		// to store index on disk use:
		/*======================================
		 * store index in path with this code
		 * 
		 * Path docDir = Paths.get("index"); Directory directory =
		 * FSDirectory.open(docDir);
		 */
		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig();
		IndexWriter iWriter = new IndexWriter(directory, config);

		// need some while loop here to go through the file and create docs
		for (int i = 0; i < songData.getDataSize(); i++) {
			Document doc = new Document();
			String title = songData.getTitle(i);
			String album = songData.getAlbum(i);
			String artist = songData.getArtist(i);
			String lyrics = songData.getLyrics(i);

			// adds each field to the document
			doc.add(new TextField("title", title, Field.Store.YES));
			doc.add(new TextField("album", album, Field.Store.YES));
			doc.add(new TextField("artist", artist, Field.Store.YES));
			doc.add(new TextField("lyrics", lyrics, Field.Store.NO));
			// write the document to the index
			iWriter.addDocument(doc);
		}

		iWriter.close();

		Scanner in = new Scanner(System.in);
		boolean loop = true;
		IndexReader reader = DirectoryReader.open(directory);
		while (loop) {
			System.out.println("search some lyrics or (q)uit: ");
			String input = in.nextLine();
			if (input.equalsIgnoreCase("q")) {
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println("See you next time!");
				System.exit(1);
			}

			// 2. query
			// ~ after each word for fuzzy search
			String querystr = args.length > 0 ? args[0] : input;
			// the "title" arg specifies the default field to use
			// when no field is explicitly specified in the query.
			Query q = new QueryParser("lyrics", analyzer).parse(querystr);

			// 3. search
			int hitsPerPage = 5;
			// copied the reader to outside the loop
			// IndexReader reader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
			searcher.search(q, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;

			// 4. display results
			System.out.println();
			System.out.println("=====================================================================================");
			System.out.println("searching... " + querystr);

			System.out.println("Found " + hits.length + " hits.");
			for (int i = 0; i < hits.length; ++i) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				System.out.println((i + 1) + ". " + d.get("artist") + "\t" + d.get("title"));
			}
			System.out.println();
		}
		// reader can only be closed when there
		// is no need to access the documents any more.
		reader.close();
		directory.close();
	}
}