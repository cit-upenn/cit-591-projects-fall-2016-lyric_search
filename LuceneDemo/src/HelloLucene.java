import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.Token;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.PhraseQuery;
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

/**
 * This class is for testing lucene functionality by creating a demo
 * @author jon
 *
 */
public class HelloLucene {
	public static void main(String[] args) throws IOException, ParseException {

		FileReader file = new FileReader("song_list.txt");
		/*
		 * ============================= stop list with this code stop list code
		 * FileReader stoplist = new FileReader("stop_list.txt");
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
		// EnglishAnalyzer analyzer = new EnglishAnalyzer();

		// 1. create the index
		// to store index on disk use:
		/*
		 * ====================================== store index in path with this
		 * code
		 * 
		 * Path docDir = Paths.get("index"); Directory directory =
		 * FSDirectory.open(docDir);
		 */
		// Path docDir = Paths.get("demo_index"); Directory directory =
		// FSDirectory.open(docDir);
		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig();
		IndexWriter indexWriter = new IndexWriter(directory, config);

		// need some while loop here to go through the file and create docs
		for (Integer key : songData.getKeySet()) {
			Document doc = new Document();
			String title = songData.getTitle(key);
			String album = songData.getAlbum(key);
			String artist = songData.getArtist(key);
			String lyrics = songData.getLyrics(key);
			// System.out.println(songData.getLyrics(key));
			// adds each field to the document
			doc.add(new TextField("title", title, Field.Store.YES));
			doc.add(new TextField("album", album, Field.Store.YES));
			doc.add(new TextField("artist", artist, Field.Store.YES));
			doc.add(new TextField("lyrics", lyrics, Field.Store.NO));
			// write the document to the index
			indexWriter.addDocument(doc);
		}

		indexWriter.close();

		Scanner in = new Scanner(System.in);
		boolean loop = true;
		/*
		 * to read from a pre-indexed file directly Path docDir =
		 * Paths.get("demo_index"); Directory directory = FSDirectory.open(docDir);
		 * StandardAnalyzer analyzer = new StandardAnalyzer();
		 */

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

			// 2. query
			// ~ after each word for fuzzy search
			String querystr = args.length > 0 ? args[0] : input;
			// the "title" arg specifies the default field to use
			// when no field is explicitly specified in the query.

			/*
			 * test code for stemmming using tokenstreams?
			 * 
			 * TokenStream tokenstream = analyzer.tokenStream("lyrics",
			 * querystr); tokenstream = new LowerCaseFilter(tokenstream);
			 * tokenstream = new StopFilter(tokenstream,
			 * StopAnalyzer.ENGLISH_STOP_WORDS_SET); tokenstream = new
			 * PorterStemFilter(tokenstream); OffsetAttribute offsetAttribute =
			 * tokenstream.addAttribute(OffsetAttribute.class);
			 * CharTermAttribute charTermAttribute =
			 * tokenstream.addAttribute(CharTermAttribute.class);
			 * 
			 * tokenstream.reset(); while (tokenstream.incrementToken()) {
			 * querystr = charTermAttribute.toString(); } tokenstream.end();
			 * tokenstream.close();
			 */

			Query q = new QueryParser("lyrics", analyzer).parse(querystr);

			// Query q = new MultiFieldQueryParser(fields,
			// analyzer).parse(querystr);

			// 3. search
			int hitsPerPage = 5;
			// copied the reader to outside the loop
			// IndexReader reader = DirectoryReader.open(directory);
			// copied searcher to outside of loop to not create it multiple
			// times
			// IndexSearcher searcher = new IndexSearcher(reader);
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
			System.out.println();
		}
		// reader can only be closed when there
		// is no need to access the documents any more.
		in.close();
		reader.close();
		directory.close();
	}
}
