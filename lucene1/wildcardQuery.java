package lucene;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.uhighlight.UnifiedHighlighter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
 
public class wildcardQuery
{
  //This contains the lucene indexed documents
  private static final String INDEX_DIR = "Index";
 
  public static void main(String[] args) throws Exception 
  {
    //Get directory reference
    Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
     
    //Index reader - an interface for accessing a point-in-time view of a lucene index
    IndexReader reader = DirectoryReader.open(dir);
     
    //Create lucene searcher. It search over a single IndexReader.
    IndexSearcher searcher = new IndexSearcher(reader);
     
    //analyzer with the default stop words
    Analyzer analyzer = new StandardAnalyzer();
     
     
    /**
     * Wildcard "*" Example
     * */
     
    //Create wildcard query
    Query query = new WildcardQuery(new Term("contents", "hell*"));
     
    //Search the lucene documents
    TopDocs hits = searcher.search(query, 10, Sort.INDEXORDER);
     
    System.out.println("Search terms found in :: " + hits.totalHits + " files");
     /*
    UnifiedHighlighter highlighter = new UnifiedHighlighter(searcher, analyzer);
        String[] fragments = highlighter.highlight("contents", query, hits);
         
        for(String f : fragments)
        {
          System.out.println(f);
        }
       */
    
    
    for (ScoreDoc sd : hits.scoreDocs)
    {
        Document d = searcher.doc(sd.doc);
       
        System.out.println("Path : "+ d.get("path") +" , Content: "+ d.get("contents"));
    }
    
    
    
    
        /**
     * Wildcard "?" Example
     * */
         
        //Create wildcard query
      query = new WildcardQuery(new Term("contents", "mobi??"));
       
      //Search the lucene documents
      hits = searcher.search(query, 10, Sort.INDEXORDER);
       
      System.out.println("Search terms found in :: " + hits.totalHits + " files");
     /*  
      highlighter = new UnifiedHighlighter(searcher, analyzer);
      fragments = highlighter.highlight("contents", query, hits);
         
      for(String f : fragments)
      {
        System.out.println(f);
      }
      */
      for (ScoreDoc sd : hits.scoreDocs)
      {
          Document d = searcher.doc(sd.doc);
         
          System.out.println("Path : "+ d.get("path") +" , Content: "+ d.get("contents"));
      }
     
        dir.close();
  }
}