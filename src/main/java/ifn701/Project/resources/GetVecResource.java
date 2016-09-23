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

@Path("/Get_Vec")
@Produces(MediaType.APPLICATION_JSON)
//lets Jersey’s content negotiation code know that this resource produces representations which are application/json
public class GetVecResource {
    private  final String template;
    private final String defaultName;
    private  final AtomicLong counter;
    Cluster cluster;

    public GetVecResource(String template, String defaultName,Cluster cluster) {
        this.template = template;
        // the template it uses to produce the saying and 
        this.defaultName = defaultName;
        //the defaultName used when the user declines to tell us their name.

        this.counter = new AtomicLong();//a cheap, thread-safe way of generating unique(ish) IDs.
        this.cluster=cluster;
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("fileName") String fileName,@QueryParam("queryWord") String queryWord) {
    	

    	String vecValue_str= " ";
    	ArrayList<Double> vecValue = new ArrayList<Double>();
    	
    	
    	//--------connect with cassandra----
   	 //open DB
   	 DBOperation client = new DBOperation();
	 	try
	 	{
	 		 client.connect(cluster);
	 		  vecValue =client.querySchema(fileName, queryWord);
	 		 
	 	}
	 	
	 	catch (Throwable t) {
           t.printStackTrace();
       }
       finally {
           client.close();
       }   
	 	
	 	for (Double d : vecValue)
	 	{
	 		vecValue_str+=d.toString()+',';
	 	}
	 	
        
        return new Saying(counter.incrementAndGet(), vecValue_str);
    }
    
}