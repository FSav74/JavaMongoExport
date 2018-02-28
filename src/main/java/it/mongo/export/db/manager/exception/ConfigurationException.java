package it.mongo.export.db.manager.exception;

public class ConfigurationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1907365861303693303L;

	public ConfigurationException(String message) {
		super(message);
	}
	
	public ConfigurationException(String message, Throwable e) {
		super(message, e);
	}
	
	public ConfigurationException(String message, Exception e) {
		super(message, e);
	}
}
