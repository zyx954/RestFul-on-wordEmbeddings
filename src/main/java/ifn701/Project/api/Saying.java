package ifn701.Project.api;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class Saying {
	//this is a representation class
	//a simpe pojo 
	//this class is immutable
	
	/*Second, it uses the JavaBeans standard for the id and content properties. This allows
	 *  Jackson to serialize it to the JSON we need*/
	
	//The Jackson object mapping code will populate the id field of the JSON object with the
	//return value of #getId(), likewise with content and #getContent().
	
	
    private long id;

    @Length(max = 3)// Lastly, the bean leverages validation to ensure the content size is no greater than 3.
    private String content;

    public Saying() {
        // Jackson deserialization
    }

    public Saying(long id, String content) {
        this.id = id;
        //here take the contect as the key in the variable vec1=<string,int[]> 
        //I need vec1[content] --return int[]
        this.content = content;
        System.out.println(content+"@@@@@");
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }
}