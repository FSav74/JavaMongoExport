package it.mongo.export.db.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;






import it.mongo.export.db.manager.ConfigurationProperty;
import it.mongo.export.db.manager.DBManager;

import org.bson.Document;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class OriginDAO {
	
	private final MongoCollection<Document> originCollection;
	
	public OriginDAO(){
		originCollection = DBManager.INSTANCE.getDatabase(ConfigurationProperty.INSTANCE.getProperty(ConfigurationProperty.DATABASE_NAME))
				                      .getCollection(ConfigurationProperty.INSTANCE.getProperty(ConfigurationProperty.ORIGIN_COLLECTION));
	}
	
	

	
	
	public void exportCollectionToFile(int limit){
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id.TIPO_POSIZIONE", "ANAGRAFE");
		searchQuery.put("_id.GRUPPO", "00223");
		searchQuery.put("_id.ABI", "03032");
		searchQuery.put("INPUT.TIPO_INTERMEDIARIO", "02");
		
		//-- ArrayList
		List<String> recipers = new ArrayList<String>();
		recipers.add("AAAAAA00A01D138W");
		recipers.add("AAAAAA00A01C942C");
		recipers.add("AAAAAA00A01Z360X");
		recipers.add("AAAAAA00A00P408F");
		recipers.add("AAAAAA00A00Z308D");
		recipers.add("AAAAAA00A00L767M");
		recipers.add("AAAAAA00A01M758M");
		recipers.add("AAAAAA00A00A630G");
		recipers.add("AAAAAA00A00E585E");
		recipers.add("AAAAAA00A00W666A");
		recipers.add("AAAAAA00A00F767Z");

		
		//------------------------------------------
		// From ArrayList --> BasicDBList
		//------------------------------------------
		BasicDBList listaRecipers = new BasicDBList();
		listaRecipers.addAll(recipers);
		
		Document inClause = new Document();
		inClause.put("$in",listaRecipers);
		searchQuery.put("_id.COD_SOGGETTO", inClause);
		
//		db.DEV_ORIGIN.find(  
//
//				{ "_id.TIPO_POSIZIONE" : "ANAGRAFE", "_id.GRUPPO" : "00223", "_id.ABI" : "03032", "INPUT.TIPO_INTERMEDIARIO" : "02",
//				    "INPUT.DATA_RIFERIMENTO_CALCOLO" : { "$gte" : ISODate("2017-05-31T22:00:00.000Z"), "$lte" : ISODate("2017-06-30T22:00:00.000Z") },
//				    "INDEX_NODE_COD_SOGGETTO" : { "$gte" : 0, "$lte" : 16 }, 
//				    "_id.COD_SOGGETTO" : { "$in" : ["AAAAAA00A01D138W", "AAAAAA00A01C942C", "AAAAAA00A01Z360X", "AAAAAA00A00P408F", "AAAAAA00A00Z308D", "AAAAAA00A00L767M", "AAAAAA00A01M758M", "AAAAAA00A00A630G", "AAAAAA00A00E585E", "AAAAAA00A00W666A", "AAAAAA00A00F767Z"] } }
//				)

		
		
		BufferedWriter writer = null;
		PrintWriter outFile = null;
		try{
			//FileWriter writer2 = new FileWriter("C:\\software\\MONGODOC\\filename2.txt"); //FileWriter non permette 
																							//di specificare la formattazione
			
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\software\\MONGODOC\\filenameANAG.txt"), "utf-8"));
			outFile = new PrintWriter(writer);
			
			FindIterable<Document> iterable = originCollection.find(searchQuery).limit(limit);
			MongoCursor<Document> cursor = iterable.iterator();
	
			while (cursor.hasNext()) {
				outFile.println(cursor.next().toJson());
			}
		}catch(IOException e){
			e.printStackTrace();
		} finally {
			   try {outFile.close();} catch (Exception ex) {/*ignore*/}
			}
	
	}
	
	
	/**
	 * export Operazioni
	 * 
db.DEV_ORIGIN.find(  

{ "_id.TIPO_POSIZIONE" : "OPERAZIONE", "_id.GRUPPO" : "00223", "_id.ABI" : "03032",
     "_id.COD_SOGGETTO" : { "$in" : ["AAAAAA00A01D138W", "AAAAAA00A01C942C", "AAAAAA00A01Z360X", "AAAAAA00A00P408F", "AAAAAA00A00Z308D", "AAAAAA00A00L767M", "AAAAAA00A01M758M", "AAAAAA00A00A630G", "AAAAAA00A00E585E", "AAAAAA00A00W666A", "AAAAAA00A00F767Z"] } }
)
	 */
	
	
	/*
	db.ASS_ORIGIN.aggregate([
{$match: {"INPUT.TIPO_PREMIO" : "04","_id.RUOLO" : "CLIENTE"}}
,
{$group:{ _id : "$_id.ID_OPERAZIONE"}
}

])
	 */
	public void exportAggregateToFile(int limit){
//		BasicDBObject searchQuery = new BasicDBObject();
//		//searchQuery.put("_id.TIPO_POSIZIONE", "OPERAZIONE");
//		searchQuery.put("_id.TIPO_POSIZIONE", "ANAGRAFE");
//		searchQuery.put("_id.GRUPPO", "00223");
//		//searchQuery.put("_id.ABI", "03032");
		
		System.out.println("Aggregateeeeee");
		

		
		
		AggregateIterable<Document> output = originCollection.aggregate(Arrays.asList(
		        
		        new Document("$match", new Document("INPUT.TIPO_PREMIO", "04").append("_id.TIPO_POSIZIONE","OPERAZIONE").append("_id.RUOLO","CLIENTE")),
		        new Document("$group", new Document("_id", "$_id.ID_OPERAZIONE"))
		        
//		        ,
//		        new Document("$limit", 200),
//		        new Document("$project", new Document("_id", 0)
//		                    .append("url", "$views.url")
//		                    .append("date", "$views.date"))
		        ));

		// Print for demo
		if(output== null)
		    System.out.println("E' null");

		
		BufferedWriter writer = null;
		PrintWriter outFile = null;
		try{
			//FileWriter writer2 = new FileWriter("C:\\software\\MONGODOC\\filename2.txt"); //FileWriter non permette 
																							//di specificare la formattazione
			
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\software\\MONGODOC\\ASS_ORIGIN.txt"), "utf-8"));
			outFile = new PrintWriter(writer);
			
//			FindIterable<Document> iterable = originCollection.find(searchQuery).limit(limit);

			MongoCursor<Document> cursor = output.iterator();
	
			while (cursor.hasNext()) {
				outFile.println(cursor.next().get("_id"));
			}

//			for (Document dbObject : output)
//				{
//				    outFile.println(dbObject);
//				}
//			
			
		}catch(IOException e){
			e.printStackTrace();
		} finally {
			   try {outFile.close();} catch (Exception ex) {/*ignore*/}
			}
		
		
	}
	
	
	
	public void exportOperazioniToFile(int limit){
		
		BasicDBObject searchQuery = new BasicDBObject();
		//searchQuery.put("_id.TIPO_POSIZIONE", "OPERAZIONE");
		searchQuery.put("_id.TIPO_POSIZIONE", "ANAGRAFE");
		searchQuery.put("_id.GRUPPO", "00223");
		//searchQuery.put("_id.ABI", "03032");
		
		searchQuery.put("_id.ABI", "99999");
		
		
		//-- ArrayList
/*
 * CLAUSOLA IN		
		List<String> recipers = new ArrayList<String>();
		recipers.add("AAAAAA00A01D138W");
		recipers.add("AAAAAA00A01C942C");
		recipers.add("AAAAAA00A01Z360X");
		recipers.add("AAAAAA00A00P408F");
		recipers.add("AAAAAA00A00Z308D");
		recipers.add("AAAAAA00A00L767M");
		recipers.add("AAAAAA00A01M758M");
		recipers.add("AAAAAA00A00A630G");
		recipers.add("AAAAAA00A00E585E");
		recipers.add("AAAAAA00A00W666A");
		recipers.add("AAAAAA00A00F767Z");

		
		//------------------------------------------
		// From ArrayList --> BasicDBList
		//------------------------------------------
		BasicDBList listaRecipers = new BasicDBList();
		listaRecipers.addAll(recipers);
		
		Document inClause = new Document();
		inClause.put("$in",listaRecipers);
		searchQuery.put("_id.COD_SOGGETTO", inClause);

FINE CLAUSOLA IN
*/		
		
		
//		db.DEV_ORIGIN.find(  
//
//				{ "_id.TIPO_POSIZIONE" : "ANAGRAFE", "_id.GRUPPO" : "00223", "_id.ABI" : "03032", "INPUT.TIPO_INTERMEDIARIO" : "02",
//				    "INPUT.DATA_RIFERIMENTO_CALCOLO" : { "$gte" : ISODate("2017-05-31T22:00:00.000Z"), "$lte" : ISODate("2017-06-30T22:00:00.000Z") },
//				    "INDEX_NODE_COD_SOGGETTO" : { "$gte" : 0, "$lte" : 16 }, 
//				    "_id.COD_SOGGETTO" : { "$in" : ["AAAAAA00A01D138W", "AAAAAA00A01C942C", "AAAAAA00A01Z360X", "AAAAAA00A00P408F", "AAAAAA00A00Z308D", "AAAAAA00A00L767M", "AAAAAA00A01M758M", "AAAAAA00A00A630G", "AAAAAA00A00E585E", "AAAAAA00A00W666A", "AAAAAA00A00F767Z"] } }
//				)

		
		
		BufferedWriter writer = null;
		PrintWriter outFile = null;
		try{
			//FileWriter writer2 = new FileWriter("C:\\software\\MONGODOC\\filename2.txt"); //FileWriter non permette 
																							//di specificare la formattazione
			
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\software\\MONGODOC\\ANAGRAFICHE_SCIPIONE.txt"), "utf-8"));
			outFile = new PrintWriter(writer);
			
			FindIterable<Document> iterable = originCollection.find(searchQuery).limit(limit);
			MongoCursor<Document> cursor = iterable.iterator();
	
			while (cursor.hasNext()) {
				outFile.println(cursor.next().toJson());
			}
		}catch(IOException e){
			e.printStackTrace();
		} finally {
			   try {outFile.close();} catch (Exception ex) {/*ignore*/}
			}
	}
	
	
	
	public void importFileInCollection(String myfile){
		
        try {
        	FileReader file = new FileReader(new File(myfile));

            BufferedReader read = new BufferedReader(file);
            String line = null;
			while ((line = read.readLine()) != null) 
			{
				//TODO: si potrebbe pensare di creare un array e usare insertMany
				//Se il cursore è grande : si potrebbe comunque pensare di fare una array (di dimensione limitata)
				Document doc = Document.parse(line);
				originCollection.insertOne(doc);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public long count(){
		
		BasicDBObject searchQuery = new BasicDBObject();
		//searchQuery.put("_id.TIPO_POSIZIONE", "OPERAZIONE");
		searchQuery.put("_id.TIPO_POSIZIONE", "ANAGRAFE");
		searchQuery.put("_id.GRUPPO", "00223");
		//searchQuery.put("_id.ABI", "03032");
		
		searchQuery.put("_id.ABI", "00001");
		
		long result = originCollection.count(searchQuery);
		return result;
	}
	
}
