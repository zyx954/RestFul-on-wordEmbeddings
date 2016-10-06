package ifn701.Project;

import java.awt.List;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.google.common.primitives.Doubles;

import ifn701.Project.health.ProjectHealthCheck;
import ifn701.Project.resources.AnalogyResources;
import ifn701.Project.resources.GetVecResource;
import ifn701.Project.resources.MostSimilarWords;
import ifn701.Project.resources.SimilarityResource;
import ifn701.Project.resources.VecFile;
import io.dropwizard.Application;
//import io.dropwizard.jersey.sessions.Session;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class WordEmbeddingApplication extends Application<ProjectConfiguration> {
	Cluster cluster;
    public static void main(String[] args) throws Exception {
    	new WordEmbeddingApplication().run(args);
        //application’s entry point
        //here I enter the = [server, hello-world.yml]
        
        
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<ProjectConfiguration> bootstrap) {
        // nothing to do yet
    	//An initialize method is used to configure aspects of the application required 
    	//before the application is run, 
    	//like bundles, configuration source providers, 
    }

    @Override
    public void run(ProjectConfiguration configuration,Environment environment) {
    	

    	final ProjectHealthCheck healthCheck =new ProjectHealthCheck(configuration.getTemplate());
    	environment.healthChecks().register("template", healthCheck);
    		    
    	
    	//get cluster through cassandra-Dropwizard buddle.
    	 cluster = configuration.getCassandraFactory().build(environment);
    	 //regester resources to jetty
     	final GetVecResource resource = new GetVecResource(configuration.getTemplate(),configuration.getDefaltName(),cluster);
    	environment.jersey().register(resource);
    	final SimilarityResource resource2 = new SimilarityResource(configuration.getTemplate(),configuration.getDefaltName(),cluster);
    	environment.jersey().register(resource2);
    	final MostSimilarWords resource3 = new MostSimilarWords(configuration.getTemplate(),configuration.getDefaltName(),cluster);
    	environment.jersey().register(resource3);
     	final AnalogyResources resource4 = new AnalogyResources(configuration.getTemplate(),configuration.getDefaltName(),cluster);
    	environment.jersey().register(resource4);

    
    }
    
    

}