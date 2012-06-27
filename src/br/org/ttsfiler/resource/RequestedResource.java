package br.org.ttsfiler.resource;

import java.io.DataInputStream;
import java.io.File;

public class RequestedResource {

	private File file;
	private DataInputStream dataInputStream;
	private int httpStatusCode;
	private String httpStatusDescription;
	
	
	public RequestedResource(){
		
	}
	
	public RequestedResource(File file, DataInputStream dataInputStream, int httpStatusCode, String httpStatusDescription){
		this.file = file;
		this.dataInputStream = dataInputStream;
		this.httpStatusCode = httpStatusCode;
		this.httpStatusDescription = httpStatusDescription;
	}
	
	
	public File getFile(){
		return this.file;
	}
	
	
	public DataInputStream getDataInputStream(){
		return this.dataInputStream;
	}
	
	
	public int getHTTPStatusCode(){
		return this.httpStatusCode;
	}
	
	
	public String getHTTPStatusDescription(){
		return this.httpStatusDescription;
	}
	
	public byte[] getBytesFromResource(){
		return new byte[3];
	}
	
	
}
