package ifn701.Project;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import ifn701.Project.resources.SimilarityData;

public class DBOperation {

	private Cluster cluster;
    private Session session;
 
 
    public Session getSession() {
        return this.session;
    }
 
 
    /**
     * 连接集群，创建执行cql的session对象。
     * 
     * @param node
     */
    public void connect(Cluster clu) {
    	cluster=clu;
    	Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
        for (Host host : metadata.getAllHosts()) {
            System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(),
                host.getAddress(), host.getRack());
        }
        Duration timeout=Duration.ofSeconds(2);
        session = cluster.connect();
        
        System.out.println("###");
    }
 
 

    //create keyspaec / schema/database  
    //creat table if not exist
    //here Only excute once. keyspace(once)  . table will based the parameter.
    public void createSchema(String fileName) {
        session.execute("CREATE KEYSPACE IF NOT EXISTS WordEmbeddings WITH replication "
                + "= {'class':'SimpleStrategy', 'replication_factor':1};");
        session.execute("CREATE TABLE IF NOT EXISTS WordEmbeddings."+fileName +"(" + "word varchar PRIMARY KEY," +  "vectors list<double>" + ");");

    }
 
    //insert data
    //here the data will based on para
    public void loadData(String name,String word, ArrayList<Double> vec) {
        session.execute("INSERT INTO WordEmbeddings."+name +"(word,vectors) " + "VALUES ("
                +"'" +word+"'"+","+vec+");");
        
    }
 
 
 //select data
    public  ArrayList<Double> querySchema(String tableName,String word) {
    	String queryStr= "SELECT vectors "+" FROM WordEmbeddings."+tableName  + " where word = '"+word+"';";
        ResultSet results =
                session.execute(queryStr);
       Row row= results.one();
       //the data returned from DB is List type
       List<Double> row_list=row.getList(0, Double.class);
       //convert list to ArrayList
       ArrayList<Double> vecValue=new ArrayList<Double>(row_list);
       
        return vecValue;
    }
    
    public  String getWordList(String tableName,String word) {
    	
    	//
    	Comparator order =  new Comparator<SimilarityData>() 
    	{  
            public int compare(SimilarityData p1, SimilarityData p2) {  
                return p2.value - p1.value;  
              }  
            };
        PriorityQueue<SimilarityData> queue = new PriorityQueue<SimilarityData>(order);   
        String finalValue="";
        //get query vec
        ArrayList<Double> queryVec = new ArrayList<Double>();
        queryVec = querySchema(tableName,word);
        
        //get all the vec from DB to memory
    	String queryStr= "SELECT * "+" FROM WordEmbeddings."+tableName  + ";";
        ResultSet results =
                session.execute(queryStr);
       Iterator<Row> rows= results.iterator();
//       List<Row> rows = results.all();//solution 1 this solution is slow
       
       //the data returned from DB is List type
       ArrayList<String> allRows = new ArrayList<String>();
       long a =System.currentTimeMillis();
       //solution2 : 
       while (rows.hasNext())
       {
    	   Row row = rows.next();
    	  String oneWord= row.getString(0);
    	  List<Double> row_list=row.getList(1, Double.class);
    	 // words.add(word);
    	  //String i = "0";
    	  ArrayList<Double> vecValue=new ArrayList<Double>(row_list);
    	  double value = calculateCos(vecValue,queryVec);
    	  int i = (int) (value * 1000);
    	  queue.add(new SimilarityData(oneWord,i));
       }
       long b =System.currentTimeMillis();
       System.out.println("GET ALL TEH WRODS TIME2 ");
       System.out.println(b-a);
       //solition 1
//       for(Row row : rows)
//       {
//    	   String row_list=row.getString(0);
//           //convert list to ArrayList
////    	   String word = "";
//    	   //convvert List<String> to string 
//    	   String word = row_list;
//    	   words.add(word);
//       }
//       while (!queue.isEmpty())
//       {
    	   for(int i = 0; i<41;i++)
    	   {
    		   if(i ==0)
    		   {
    			   queue.poll().toString();
    			   continue;
    		   }
    		   else
    		   {
    			   //System.out.println(queue.poll().toString());
    			   finalValue += (i)+". " +queue.poll().toString()+ ";  ";
    			   
    		   }
    	   }
       
       
        return finalValue;
    }
    
    //calculate Cos
    double calculateCos(ArrayList<Double>v1,ArrayList<Double>v2)
    {
    	double[] vecValue = convertArrayListToArray(v1);
    	double[] queryVec = convertArrayListToArray(v2);
    	return cosineSimilarity(vecValue,queryVec);
    }
      double cosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }   
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
    
      
    //conver ArrayList to array --> for cosineSimilarity function
    public  double[] convertArrayListToArray(ArrayList<Double> doubles)
	{
    	 double[] target = new double[doubles.size()];
    	 for (int i = 0; i < target.length; i++) {
    	    target[i] = doubles.get(i);                
    	 }
    	return target;

	}
    
    //close connection.
       public void close() {
           session.close();
//           cluster.close();
       }
       
       
 
 
    //------updata (test purpose)
    public void updateSchema() {
        session.execute("UPDATE simplex.songs set title = 'La Petite Tonkinoise Updated'"
                + " WHERE id = 756716f7-2e54-4715-9f00-91dcbea6cf50;");
    }
 

//-----delete  (test purpose)
    public void deleteSchema() {
        session.execute("DELETE FROM simplex.songs " + " WHERE id = 756716f7-2e54-4715-9f00-91dcbea6cf50;");
    }
 
   
     //delete keyspace，in other DB system might called schema
     
    public void dropSchema(String keySpaceName) {
        session.execute("DROP KEYSPACE "+keySpaceName+";");
    }

}
