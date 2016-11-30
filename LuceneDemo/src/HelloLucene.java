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

import java.io.IOException;

public class HelloLucene {
	public static void main(String[] args) throws IOException, ParseException {
    // 0. Specify the analyzer for tokenizing text.
    //    The same analyzer should be used for indexing and searching
    StandardAnalyzer analyzer = new StandardAnalyzer();

    // 1. create the index
    // to store index on disk use: 
//    Directory directory = FSDirectory.open("~Development/EclipseWorkSpace/HomeWorkSix/cit-591-projects-fall-2016-lyric_search/LuceneDemo");
    Directory directory = new RAMDirectory();
    IndexWriterConfig config = new IndexWriterConfig();
    IndexWriter iWriter = new IndexWriter(directory, config);
   
//  need some while loop here to go through the file and create each document
    Document doc = new Document();
    String title = "We Will Rock You";
    String artist = "Queen";
    String lyrics = "Buddy you're a boy make a big noise"
    		+ " Playin' in the street gonna be a big man some day"
    		+ "You got mud on yo' face"
    		+ "You big disgrace"
    		+ "Kickin' your can all over the place Singin'";
//    String lyrics = "happy birthday is the lyrics to this song boy! ";
    
    // adds each field to the document
    doc.add(new TextField("title", title, Field.Store.YES));
    doc.add(new TextField("artist", artist, Field.Store.YES));
    doc.add(new TextField("lyrics", lyrics, Field.Store.NO));
    //write the document to the index
    iWriter.addDocument(doc);
    
    iWriter.close();

    // 2. query
    String querystr = args.length > 0 ? args[0] : "kick~ all oer the plac~";	// ~ at end of each word for fuzzy search

    // the "title" arg specifies the default field to use
    // when no field is explicitly specified in the query.
    Query q = new QueryParser("lyrics", analyzer).parse(querystr);

    // 3. search
    int hitsPerPage = 10;
    IndexReader reader = DirectoryReader.open(directory);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
    searcher.search(q, collector);
    ScoreDoc[] hits = collector.topDocs().scoreDocs;
    
    // 4. display results
    System.out.println("Found " + hits.length + " hits.");
    for(int i=0;i<hits.length;++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      System.out.println((i + 1) + ". " + d.get("artist") + "\t" + d.get("title"));
    }

    // reader can only be closed when there
    // is no need to access the documents any more.
    reader.close();
    directory.close();
  }

//	private static void addDoc(IndexWriter w, String title, String isbn) throws IOException {
//		Document doc = new Document();
//		doc.add(new TextField("title", title, Field.Store.YES));
//
//		// use a string field for isbn because we don't want it tokenized
//		doc.add(new StringField("isbn", isbn, Field.Store.YES));
//		w.addDocument(doc);
//	}
}