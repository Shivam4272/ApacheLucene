package lucene2;

import java.io.IOException;
import java.nio.file.Paths;
 
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
 
public class ReadIndex
{
  private static final String INDEX_DIR = "Index2";
 
  public static void main(String[] args) throws Exception 
  {
    IndexSearcher searcher = createSearcher();
     
    //Search by ID
    TopDocs foundDocs = searchById(2, searcher);
     
    System.out.println("Total Results :: " + foundDocs.totalHits);
     
    for (ScoreDoc sd : foundDocs.scoreDocs) 
    {
      Document d = searcher.doc(sd.doc);
      System.out.println(String.format(d.get("firstName")));
    }
     
    //Search by firstName
    TopDocs foundDocs2 = searchByFirstName("lokesh", searcher);
     
    System.out.println("Total Results :: " + foundDocs2.totalHits);
     
    for (ScoreDoc sd : foundDocs2.scoreDocs) 
    {
      Document d = searcher.doc(sd.doc);
      System.out.println(String.format(d.get("lastName")));
    }
  }
   
  private static TopDocs searchByFirstName(String firstName, IndexSearcher searcher) throws Exception
  {
    QueryParser qp = new QueryParser("firstName", new StandardAnalyzer());
    Query firstNameQuery = qp.parse(firstName);
    TopDocs hits = searcher.search(firstNameQuery, 10);
    return hits;
  }
 
  private static TopDocs searchById(Integer id, IndexSearcher searcher) throws Exception
  {
    QueryParser qp = new QueryParser("id", new StandardAnalyzer());
    Query idQuery = qp.parse(id.toString());
    TopDocs hits = searcher.search(idQuery, 10);
    return hits;
  }
 
  private static IndexSearcher createSearcher() throws IOException {
    Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
    IndexReader reader = DirectoryReader.open(dir);
    IndexSearcher searcher = new IndexSearcher(reader);
    return searcher;
  }
}