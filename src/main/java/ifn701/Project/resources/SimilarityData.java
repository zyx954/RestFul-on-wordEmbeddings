package ifn701.Project.resources;

public class SimilarityData {

	 public String word;  
	 public int value;  
	 double i;
	    
	    public SimilarityData(String word, int value){  
	        this.word = word;  
	        this.value = value;  
	         i =(value+0.0)/1000;
	    }      
	    
	    
	    public String toString() {  
	        return word + "-->" + i;  
	    }  
}
