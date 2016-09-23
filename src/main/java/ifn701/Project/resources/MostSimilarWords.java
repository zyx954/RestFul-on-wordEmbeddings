
package ifn701.Project.resources;


import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.core.Cluster;

import ifn701.Project.DBOperation;
import ifn701.Project.ProjectConfiguration;
import ifn701.Project.WordEmbeddingApplication;
import ifn701.Project.api.Saying;
import io.dropwizard.setup.Environment;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;
import java.util.PriorityQueue;

@Path("/Cal_MosrSimilarWords")
@Produces(MediaType.APPLICATION_JSON)
public class MostSimilarWords {
    private  final String template;
    private final String defaultName;
    private  final AtomicLong counter;
    Cluster cluster;

    public MostSimilarWords(String template, String defaultName,Cluster cluster) {
        this.template = template;
                this.defaultName = defaultName;

        this.counter = new AtomicLong();//a cheap, thread-safe way of generating unique(ish) IDs.
        this.cluster=cluster;
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("fileName") String fileName,@QueryParam("queryWord") String queryWord) {
       String finalValue="";
    	//--------connect with cassandra----
   	 //open DB
    	long a = System.currentTimeMillis();
   	 DBOperation client = new DBOperation();
	 	try
	 	{
	 		 client.connect(cluster);
	 		 //get word list (ToDo needs to  optimize)
	 		 finalValue= client.getWordList(fileName,queryWord);
	 	}
	 	catch (Throwable t) {
           t.printStackTrace();
       }
       finally {
           client.close();
       }   
	 	//print time
        long b =System.currentTimeMillis();
        System.out.println("GET ALL TEH WRODS TIME ");
        System.out.println(b-a);
        
        return new Saying(counter.incrementAndGet(), finalValue);
    }
    
    
    
    // calculate cosine
    public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
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
    public static double[] convertArrayListToArray(ArrayList<Double> doubles)
	{
    	 double[] target = new double[doubles.size()];
    	 for (int i = 0; i < target.length; i++) {
    	    target[i] = doubles.get(i);                
    	 }
    	return target;

	}
    
}