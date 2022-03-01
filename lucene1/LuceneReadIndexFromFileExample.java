package lucene;

import java.io.File;
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











public class LuceneReadIndexFromFileExample
{
    //directory contains the lucene indexes
    private static final String INDEX_DIR = "Index";
 
    public static void main(String[] args) throws Exception
    {
        //Create lucene searcher. It search over a single IndexReader.
        IndexSearcher searcher = createSearcher();
         
        //Search indexed contents using search term
        String searchTerm="9028568911";
        TopDocs foundDocs = searchInContent(searchTerm, searcher);
         
        //Total found documents
        System.out.println("Total Results :: " + foundDocs.totalHits);
         
        //Let's print out the path of files which have searched term
        for (ScoreDoc sd : foundDocs.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            String s=d.get("contents");
            int p=s.indexOf(searchTerm);
           
            System.out.println("Path : "+ d.get("path") +" , Content: "+s+" ,StartIndexOfTerm: "+p+", SizeOfTerm: "+searchTerm.length()+ " , Score : " + sd.score);
        }
    }
     
    private static TopDocs searchInContent(String textToFind, IndexSearcher searcher) throws Exception
    {
        //Create search query
        QueryParser qp = new QueryParser("contents", new StandardAnalyzer());
        Query query = qp.parse(textToFind);
         
        //search the index
        TopDocs hits = searcher.search(query, 10);
        return hits;
    }
 
    private static IndexSearcher createSearcher() throws IOException
    {
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
         
        //It is an interface for accessing a point-in-time view of a lucene index
        IndexReader reader = DirectoryReader.open(dir);
         
        //Index searcher
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }
}

/*
public class LuceneReadIndexFromFileExample {
    public static void main(String[] args) throws IOException, ParseException {
    	LuceneReadIndexFromFileExample s = new LuceneReadIndexFromFileExample();  
        s.doSearch("Index","prn");  
    }  

    LuceneReadIndexFromFileExample() {
    }  

    public void doSearch(String db, String querystr) throws IOException, ParseException {
        // 1. Specify the analyzer for tokenizing text.  
        //    The same analyzer should be used as was used for indexing  
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);  

        Directory index = FSDirectory.open(new File(db));  

        // 2. query  
        Query q = new QueryParser(Version.LUCENE_CURRENT, "contents", analyzer).parse(querystr);  

        // 3. search  
        int hitsPerPage = 10;  
        IndexSearcher searcher = new IndexSearcher(index, true);  
        IndexReader reader = IndexReader.open(index, true);  
        searcher.setDefaultFieldSortScoring(true, false);  
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);  
        searcher.search(q, collector);  
        ScoreDoc[] hits = collector.topDocs().scoreDocs;  

        // 4. display term positions, and term indexes   
        System.out.println("Found " + hits.length + " hits.");  
        for(int i=0;i<hits.length;++i) {  

            int docId = hits[i].doc;  
            TermFreqVector tfvector = reader.getTermFreqVector(docId, "contents");  
            TermPositionVector tpvector = (TermPositionVector)tfvector;  
            // this part works only if there is one term in the query string,  
            // otherwise you will have to iterate this section over the query terms.  
            int termidx = tfvector.indexOf(querystr);  
            int[] termposx = tpvector.getTermPositions(termidx);  
            TermVectorOffsetInfo[] tvoffsetinfo = tpvector.getOffsets(termidx);  

            for (int j=0;j<termposx.length;j++) {  
                System.out.println("termpos : "+termposx[j]);  
            }  
            for (int j=0;j<tvoffsetinfo.length;j++) {  
                int offsetStart = tvoffsetinfo[j].getStartOffset();  
                int offsetEnd = tvoffsetinfo[j].getEndOffset();  
                System.out.println("offsets : "+offsetStart+" "+offsetEnd);  
            }  

            // print some info about where the hit was found...  
            Document d = searcher.doc(docId);  
            System.out.println((i + 1) + ". " + d.get("path"));  
        }  

        // searcher can only be closed when there  
        // is no need to access the documents any more.   
        searcher.close();  
    }      
}
*/