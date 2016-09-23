package ifn701.Project.resources;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class VecFile {
	
	HashMap<String,ArrayList<Double>> vec = new HashMap<String,ArrayList<Double> >();

	//Funciton used for pre-Read file from oen file 
    public HashMap<String,ArrayList<Double>> getVec (String file_name)
    {
    	VecFile Dataclass = new VecFile();  
    	//get the path based on different file_name
        String fileName = "/Users/zyx954/Documents/wordEmbeddingsData/"+file_name;
        // This will reference one line at a time
        String line = null;
        Boolean firstTime = true;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            //read each line in the file as string
            while((line = bufferedReader.readLine()) != null) {
            	//exclude the first line 
               if(firstTime==true)
               {
            	   firstTime=false;
               }
               else{
            	   //get all the word:vector and store this data to the global variable
            	   Dataclass.getData(line);
               }
            	
            }

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '"  + fileName + "'");                  
        }
        //System.out.println( "Hello World!" );
        return Dataclass.vec;
    }
    
    //a method spit string based on " " get  word and vector --> store all data in file to a dictionary  
    public void getData(String line)
    {
    	ArrayList<Double> vecValue=new ArrayList<Double>();
    	String[] singleValue=line.split(" ");
    	
    	for(int i=0; i<singleValue.length-1;i++)
    	{
    		vecValue.add(Double.parseDouble(singleValue[i+1]));
    	}
    	
    	vec.put(singleValue[0],vecValue);
    	
    	
    }
}
