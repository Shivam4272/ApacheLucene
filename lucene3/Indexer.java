package lucene;

import java.io.File;
import java.io.IOException;
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
//import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author Niraj
 */
public class Indexer {
    StandardAnalyzer analyzer;
    Directory index ;
    IndexWriterConfig config;
    IndexWriter writoo;
    String querystr;
    IndexSearcher searchoo;
    Query q;
    ScoreDoc[] hits;
    public void indexer() throws IOException, ParseException{
        analyzer = new StandardAnalyzer(Version.LUCENE_42);
        //index = new RAMDirectory();
        index = FSDirectory.open(new File("index-dir"));
        config = new IndexWriterConfig(Version.LUCENE_42, analyzer);
        writoo = new IndexWriter(index, config); 
        writoo.deleteAll();
    } 
    public void query(String str) throws IOException, ParseException{
       // index.close();
        //index = FSDirectory.open(new File("index-dir"));
        writoo.close();
        querystr = str;
        q = new QueryParser(Version.LUCENE_42, "Address", analyzer).parse(querystr);
    }
     public void writerx(Agent ag)throws IOException, ParseException{
         addDoc(writoo, ag);
         
         
    }
    public void searcher()throws IOException, ParseException{
        int hitsPerPage = 10;
        IndexReader reader = DirectoryReader.open(index);
        searchoo = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
        searchoo.search(q, collector);
        hits = collector.topDocs().scoreDocs;
    }
    public void display(Agent ag)throws IOException, ParseException{
        System.out.println("Found " + hits.length + " hits.");
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searchoo.doc(docId);
            System.out.println((i + 1) + ". " + d.get("Id") + "\t" + d.get("Name") + "\t" + d.get("Address") + "\t" + d.get("Phone"));        }     
    }
    private static void addDoc(IndexWriter w, Agent ag) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("Address", ag.getAddress(), Field.Store.YES));
        doc.add(new StringField("Name", ag.getName(), Field.Store.YES));
        doc.add(new StringField("Phone", ag.getPhone(), Field.Store.YES));
        doc.add(new StringField("Id", ag.getId(), Field.Store.YES));        
        w.addDocument(doc);
    }
       
    
}
