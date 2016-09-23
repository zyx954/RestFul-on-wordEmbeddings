package ifn701.Project;



import io.dropwizard.Configuration;
import systems.composable.dropwizard.cassandra.CassandraFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


public class ProjectConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = " ";//here it is doesnt matter that if 
    
    

    @JsonProperty//both the mehtod are annotated with jsonProperty which allow jackson to both deserialize the propertied. from a YAML file but also to serialize it
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTdemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaltName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }
    
    
    //-------
    
    @Valid
    @NotNull
    private CassandraFactory cassandra = new CassandraFactory();

    @JsonProperty("cassandra")
    public CassandraFactory getCassandraFactory() {
        return cassandra;
    }

    @JsonProperty("cassandra")
    public void setCassandraFactory(CassandraFactory cassandra) {
        this.cassandra = cassandra;
    }
}