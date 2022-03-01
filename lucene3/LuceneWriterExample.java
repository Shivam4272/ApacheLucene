package lucene;

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
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;

/**
 *
 * @author Niraj
 */
public class LuceneWriterExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
        /* Initiallially, we can create a StandardAnalyzer instance. 
         Note: You need to need to import “lucene-analyzers-common-4.2.1.jar” to use StandardAnalyzer. */
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
        //creates an StandardAnalyzer object
        // 1. Indexing
        /* You can create an index Directory and configure it with the analyzer instance.
         You can also give the file path to assign as index directory. (Must in case of larger data scenerio.) */
        Directory index = new RAMDirectory();
        //Directory index = FSDirectory.open(new File("index-dir"));
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42, analyzer);
        IndexWriter writer = new IndexWriter(index, config);
        /* Then you can create a writer object using the index directory and IndexWriterConfig objects.
         For good programming practices , never forget to close the writer upon completion of writer task.
         This completes the indexing process. */
        addDoc(writer, "Day first : Lucence Introduction test.", "3436NRX");
        addDoc(writer, "Day second , part one : Lucence Projects.", "3437RJ1");
        addDoc(writer, "Day second , part two: Lucence Uses testing rr.", "3437RJ2");
        addDoc(writer, "Day third : Lucence Demos.", "34338KRX");

        writer.close();
    // 2. quering
    /* Second task with the example is going with a query string for our seraching task. 
         For quering we use Query parser for our query string using the same analyzer.
         Nextly, we create indexreader and index searcher for our index directory using a index searcher object.
        Finally, we collect the search results using TopScoreDocCollector into the array of ScoreDoc.
        The same array can be used to display the results to user with a proper user interface as needed.
        
         */
        String querystr = "test*";
        Query q = new QueryParser(Version.LUCENE_42, "title", analyzer).parse(querystr);
        
        // 3. searching
        int hitsPerPage = 10;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        // 4. display results
        System.out.println("Query string: " + querystr );
        System.out.println("Found " + hits.length + " hits.");        
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("course_code") + "\t" + d.get("title"));
        }

        // Finally , close reader
    }

    private static void addDoc(IndexWriter w, String title, String courseCode) throws IOException {
        /* Instead of lengthly process of adding each new entry, we can create a generic fuction to add 
            the new entry doc . We can add needed fields with field variable and respective tag .*/
        
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));
        // use a string field for course_code because we don't want it tokenized
        doc.add(new StringField("course_code", courseCode, Field.Store.YES));
        w.addDocument(doc);
    }

}
