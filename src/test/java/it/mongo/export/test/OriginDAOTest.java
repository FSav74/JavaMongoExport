package it.mongo.export.test;

import it.mongo.export.db.dao.OriginDAO;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import de.svenkubiak.embeddedmongodb.EmbeddedMongo;
@Ignore
public class OriginDAOTest {

	public OriginDAOTest() {
		// TODO Auto-generated constructor stub
	}
	
	@BeforeClass
	public static void startup() {
		EmbeddedMongo.DB.port(27001).start();
	}

	@AfterClass
	public static void shutdown() {
		EmbeddedMongo.DB.stop();
	}
	
	@Test
	public void tryConnection(){
		OriginDAO dao = new OriginDAO();
		long result = dao.count();
		
		System.out.println(">>>>>>>>RESULT:"+result);
		assertEquals(result, 1);
	}

}
