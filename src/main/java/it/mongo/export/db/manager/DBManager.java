package it.mongo.export.db.manager;


import java.io.Serializable;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public enum DBManager implements Serializable{
    
    INSTANCE;
    
    private MongoClient mongoClient = null;
    private MongoDatabase blogDatabase = null;
    
    private DBManager(){
    	
    	mongoClient = new MongoClient(new MongoClientURI(ConfigurationProperty.INSTANCE.getProperty(ConfigurationProperty.DATABASE_STRING)));
       
    }
    
    public MongoDatabase getDatabase(String dataBaseNAme){
    	
    	return blogDatabase = mongoClient.getDatabase(dataBaseNAme);
    }

}
