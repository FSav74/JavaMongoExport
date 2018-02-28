package it.mongo.export;

import it.mongo.export.db.dao.AggregationDAO;
import it.mongo.export.db.dao.OriginDAO;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		OriginDAO dao = new OriginDAO();
		/** export da db **/
		/** ATTENZIONE : Verificare i dati del db**/
//		dao.exportOperazioniToFile(10000);
		
		//dao.exportAggregateToFile(10000);
		
		AggregationDAO myDao =new AggregationDAO();
		myDao.exportAmountformatted2 (1000);
		
		/** IMPORT VERSO db **/
		/** ATTENZIONE : Verificare i dati del db**/
		
//		dao.importFileInCollection("C:\\software\\MONGODOC\\filenameANAG.txt");
//		
//		dao.importFileInCollection("C:\\software\\MONGODOC\\filenameOPER.txt");
		
		//dao.importFileInCollection("C:\\software\\MONGODOC\\ANAGRAFICHE_SCIPIONE.txt");
		
		
//		it.mongo.cursorExample.OriginDAO myCursor = new it.mongo.cursorExample.OriginDAO();
//		myCursor.findCursor(10000);
	}

}
