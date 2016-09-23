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
import java.util.HashMap;
import java.util.Optional;

@Path("/Cal_Similarity")
@Produces(MediaType.APPLICATION_JSON)
public class SimilarityResource {
    private  final String template;
    private final String defaultName;
    private  final AtomicLong counter;
    Cluster cluster;

    public SimilarityResource(String template, String defaultName,Cluster cluster) {
        this.template = template;
        // the template it uses to produce the saying and 
                this.defaultName = defaultName;
              //the defaultName used when the user declines to tell us their name.

        this.counter = new AtomicLong();//a cheap, thread-safe way of generating unique(ish) IDs.
        this.cluster=cluster;
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("fileName") String fileName,@QueryParam("queryWord1") String queryWord1,@QueryParam("queryWord2") String queryWord2) {
    	

    	String vecValue_str= " ";
    	ArrayList<Double> vecValue1 = new ArrayList<Double>();
    	ArrayList<Double> vecValue2 = new ArrayList<Double>();

    	
    	
    	//--------connect with cassandra----
   	 //open DB
   	 DBOperation client = new DBOperation();
	 	try
	 	{
	 		 client.connect(cluster);
	 		  vecValue1 =client.querySchema(fileName, queryWord1);
	 		 vecValue2 =client.querySchema(fileName, queryWord2);
	 	}
	 	
	 	catch (Throwable t) {
           t.printStackTrace();
       }
       finally {
           client.close();
       }   
	 	
	 	double[] vecValue1_double=convertArrayListToArray(vecValue1);
	 	double[] vecValue2_double=convertArrayListToArray(vecValue2);
	 	
//        
	 	//calculate similarity
        double similarity = cosineSimilarity(vecValue1_double,vecValue2_double);
        String result2 = Double.toString(similarity);
        
        
        return new Saying(counter.incrementAndGet(), result2);
    }
    
    // calculate cosine similarity
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