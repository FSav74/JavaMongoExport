package it.mongo.export.db.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum ConfigurationProperty {
	
	INSTANCE;
	
	/** ATTENZIONE AMBIENTE OASI **/
	//	public static final String DATABASE_NAME = "DATI_AML_DEV";
	//	public static final String DATABASE_STRING = "mongodb://10.229.232.156"; 	
	//	public static final String ORIGIN_COLLECTION = "ASS_ORIGIN"; 
	
	/** NOMI PROPRIETA che devono essere presenti sul file di CONF **/ 
	public static final String DATABASE_NAME = "DATABASE_NAME"; 
	public static final  String DATABASE_STRING = "DATABASE_STRING";
	public static final  String ORIGIN_COLLECTION = "ORIGIN_COLLECTION";
	
	public static final  String MATCH_QUERY1 = "MATCH_QUERY1";
	public static final  String GROUP_QUERY1 = "GROUP_QUERY1";
	public static final  String PROJECT_QUERY1 = "PROJECT_QUERY1";
	public static final  String FILE_EXPORT = "FILE_EXPORT";
	
	private Properties prop = new Properties();
	
	private ConfigurationProperty() {
		
		/**
		 * Il path completo del file di configurazione è in una JVM variable 
		 */
		String fileName = System.getProperty("fileConfiguration");
		if (fileName==null){
			throw new RuntimeException("Path del File di configurazione non valorizzato: controllare"
					+ "che sia definita e valorizzata la jvm variable -DfileConfiguration");
		}
		System.out.println("PATH COMPLETO FILE DI CONFIGURAZIONE:"+fileName);
	    try {
	    	
	    	InputStream is = new FileInputStream(fileName);

			prop.load(is);			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Non è stato possibile caricare le proprietà dal file di configurazione.",e);
			
		}
	}
	
	public String getProperty(String key){
		
		String value=  prop.getProperty(key);
		if (value==null) System.out.println("Errore Paramentro non esistente nelle configurazioni:"+key);
		return value;

	}
}
