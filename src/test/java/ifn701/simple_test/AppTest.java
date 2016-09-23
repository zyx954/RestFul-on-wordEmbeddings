package ifn701.simple_test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ifn701.Project.resources.SimilarityData;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
        List<SimilarityData> array =new Li<SimilarityData>();
        //SimilarityData[] a2 = new Sim
        Collections.sort(array);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
