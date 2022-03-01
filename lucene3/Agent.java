package lucene;

import java.io.IOException;
import org.apache.lucene.queryparser.classic.ParseException;



/**
 *
 * @author NRJ_AC
 */
public class Agent {
    private String name,address,phone,id,password;
   
    public Agent()
    {
        name="";
        address="";
        phone="";
        id="";
        password="";
    }
    public Agent(String a,String b,String c,String d,String e)
    {
        name=a;
        address=b;
        phone=c;
        id=d;
        password=e;
    }
    public String getName()
    {
        return name;
    }
    public String getAddress()
    {
        return address;
    }
    public String getPhone()
    {
        return phone;
    }
    public String getId()
    {
        return id;
    }
    public String getPassword()
    {
        return password;
    }
    public void setName(String a)
    {
        name=a;
    }
    
    public void setID(String a)
    {
        id = a;
    }
    
    public void setPassword(String a)
    {
        password = a;
    }

    public static void main(String[] args) throws IOException, ParseException {
           Agent ag = new Agent("Niraj Ac", "Lamachour -3 , Kaski " ,"+9779800000000", "322krx","Pass");
           Agent ag2 = new Agent("Nitesh Singh", " Dhapasi-6 , Kathmandu" ,"+9779800000000", "344rxn","Pass");
           Agent ag3 = new Agent("Niraj Pahari", "Armala -3 , Kaski" ,"+9779800000000", "304rnr","Pass");
           Agent ag4 = new Agent("Bir Bikram Shah", " Dhapasi-6 , Kathmandu" ,"+9779800000000", "420krx","Pass");
           Indexer in =new Indexer();
           in.indexer();
           in.writerx(ag);
           in.writerx(ag2);  
           in.writerx(ag3);
           in.writerx(ag4);
           in.query("Kathmandu");
           in.searcher();
           in.display(ag);  
       } 
}