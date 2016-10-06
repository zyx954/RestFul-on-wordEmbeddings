package ifn701.Project;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import ifn701.Project.resources.VecFile;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;


public class ReadFileToDB {
	public static void main( String[] args )
    {
		 Cluster cluster;
    Session session;
		//read file and store file data into cassandra
	 	DBOperation client = new DBOperation();
	 	try
	 	{
	 		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
	 		 client.connect(cluster);
	 		
	 		
	 		//read file from folder and reorginaze file data in to a variable with type HashMap<String,ArrayList<Double>>
	     	//& insert this variable to Cassandra
	     	File folder = new File("/Users/zyx954/Documents/wordEmbeddingsData");
	     	
	     	File[] listOfFiles = folder.listFiles();
	     	
	     	//get filename and create table(Schema) based on filename
	     	for (File file : listOfFiles) {
	     		//exclude  one hidden file <.DS_Store> if exist
	     		if (!file.getName().equals(".DS_Store") )
	     		{
	     		HashMap<String,ArrayList<Double>> vecValue=new HashMap<String,ArrayList<Double>>();
	     		 String fileNameWithTXT=file.getName();
	     		 //exculed the redundant character<the last 18 characters> in each file name 
	     		 String fileName = fileNameWithTXT.substring(0,fileNameWithTXT.length()-18);
	     	    if (file.isFile()) {
	     	       VecFile vecFile = new VecFile();
	     	       //put all the data in one file into a variable
	     	       vecValue = vecFile.getVec(fileNameWithTXT);
	     	    }
		      //create table(Schema) based on filename
	     	   ArrayList<String> tables = client.createSchema(fileName);
	     	    //record time
	     	   long a = System.currentTimeMillis();
	     	    //put the data from file into cassandra  
	     	   
	     			  if (!tables.contains(fileName))
	     		   {
	     			  for(String key:vecValue.keySet())
	   	           {
	   	        	   //get each vactor value 
	   	           	ArrayList<Double> vecValueDouble = vecValue.get(key);
	   	           	//lode each line one to DB one by one 
	   	           	client.loadData(fileName,key,vecValueDouble);
	   	           	//System.out.println(key);
//	   	           }
	     		   }
	     	   }
	           
	           long b =System.currentTimeMillis();
	           System.out.println(b-a);
	     	}
	     	}
	     	
	 	}
	 	
	        catch (Throwable t) {
	            t.printStackTrace();
	        }
	        finally {
	            client.closeAll();
	            
	            System.out.println("close DB");
	        }   
		 
	 	System.out.println("finish loading");
    }
	
	
	
}
