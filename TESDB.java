package io.javabrains.test;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DBTest {
	
	private Session session;
	
	@Before
	public void before() throws Exception
	{
	    EmbeddedCassandraServerHelper.startEmbeddedCassandra(200000L);
	    Cluster cluster = new Cluster.Builder().addContactPoints("127.0.0.1").withPort(9142).build();
	    session = cluster.connect();
	    CQLDataLoader cqlDataLoader = new CQLDataLoader(session);
	    cqlDataLoader.load(new ClassPathCQLDataSet("Simple.cql", true, "test"));
	}
    
	@Test
    public void calCulateTime() throws Exception {
        ResultSet result = session.execute("select ttl(name) from test.testdb");
       assertEquals(5000, result.all().get(0).getInt(0));
        
    }
    
	@Test
    public void ttl() throws Exception {
      ResultSet result = session.execute("select ttl(name) from test.testdb");
      assertEquals(4, result.all().size());
    }
    
    @After
    public void close() {
   // 	session.close();
    }
}
