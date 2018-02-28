package it.mongo.export.db.dao;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

import it.mongo.export.db.manager.ConfigurationProperty;
import it.mongo.export.db.manager.DBManager;

import org.bson.Document;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class AggregationDAO {

	private final MongoCollection<Document> originCollection;
	
	public AggregationDAO(){
		originCollection = DBManager.INSTANCE.getDatabase(ConfigurationProperty.INSTANCE.getProperty(ConfigurationProperty.DATABASE_NAME))
				                      .getCollection(ConfigurationProperty.INSTANCE.getProperty(ConfigurationProperty.ORIGIN_COLLECTION));
	}
	
	public void exportAmountformatted2(int limit){

		
		System.out.println("**********************");
		System.out.println("**Eseguo aggregate ***");
		System.out.println("**********************");
		
		/**
		 * I vari stage dell'aggregate sono sul file di properties. Document.parse li 
		 * rende Document
		 */
		String matchStage = ConfigurationProperty.INSTANCE.getProperty(ConfigurationProperty.MATCH_QUERY1);
		String projectStage = ConfigurationProperty.INSTANCE.getProperty(ConfigurationProperty.PROJECT_QUERY1);
		Document stageMatch =  Document.parse(matchStage);
		Document stageProject =  Document.parse(projectStage);
		//AggregateIterable<Document> output = originCollection.aggregate(Arrays.asList(stageMatch,stageProject)).allowDiskUse(true);
		AggregateIterable<Document> output = originCollection.aggregate(Arrays.asList(stageProject)).allowDiskUse(true);
		
		String pathFileExport = ConfigurationProperty.INSTANCE.getProperty(ConfigurationProperty.FILE_EXPORT);

		// Print for demo
		if(output== null){
		    System.out.println(">>> Cursore E' null");
		    System.exit(-1);
		}
		
		BufferedWriter writer = null;
		PrintWriter outFile = null;
		try{
			//FileWriter writer2 = new FileWriter("C:\\software\\MONGODOC\\filename2.txt"); //FileWriter non permette 
																							//di specificare la formattazione
			
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathFileExport), "utf-8"));
			outFile = new PrintWriter(writer);
			
//			FindIterable<Document> iterable = originCollection.find(searchQuery).limit(limit);

			MongoCursor<Document> cursor = output.iterator();
	
			while (cursor.hasNext()) {
				//outFile.println(cursor.next().get("_id"));
				outFile.println(cursor.next().toJson());
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
	
	public void exportAmountformatted(int limit){

		
		System.out.println("**********************");
		System.out.println("**Eseguo aggregate ***");
		System.out.println("**********************");
		
		/**
		 * I vari stage dell'aggregate sono scritti con stringhe e poi tramite il metodo Document.parse
		 * resi Document
		 */
		
		Document stageMatch =  Document.parse("{ \"$match\" : {  \"TOT_IMP\" :  { \"$gte\" : 1000000}   } }");
		Document stageProject =  Document.parse("{ $project: { _id: 1, \"TOT_IMP\" :1, \"TOT_IMP_ARR\": { $ceil: \"$TOT_IMP\" },\"TOTALE_OPERAZIONI\":1  } }");
		AggregateIterable<Document> output = originCollection.aggregate(Arrays.asList(stageMatch,stageProject)).allowDiskUse(true);
		
		

		// Print for demo
		if(output== null){
		    System.out.println(">>> Cursore E' null");
		    System.exit(-1);
		}
		
		BufferedWriter writer = null;
		PrintWriter outFile = null;
		try{
			//FileWriter writer2 = new FileWriter("C:\\software\\MONGODOC\\filename2.txt"); //FileWriter non permette 
																							//di specificare la formattazione
			
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\PROGETTI\\OASI-DOC\\ESTRAZIONE_SAV.txt"), "utf-8"));
			outFile = new PrintWriter(writer);
			
//			FindIterable<Document> iterable = originCollection.find(searchQuery).limit(limit);

			MongoCursor<Document> cursor = output.iterator();
	
			while (cursor.hasNext()) {
				//outFile.println(cursor.next().get("_id"));
				outFile.println(cursor.next().toJson());
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
	
	

}
