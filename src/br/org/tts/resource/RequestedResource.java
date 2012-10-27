package br.org.tts.resource;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

public class RequestedResource {

	private File file;
	private DataInputStream dataInputStream;
	private int httpStatusCode;
	private String httpStatusDescription;
	
	
	/**
	 * 
	 * @param file
	 * @param dataInputStream
	 * @param httpStatusCode
	 * @param httpStatusDescription
	 */
	public RequestedResource(File file, DataInputStream dataInputStream, int httpStatusCode, String httpStatusDescription){
		this.file = file;
		this.dataInputStream = dataInputStream;
		this.httpStatusCode = httpStatusCode;
		this.httpStatusDescription = httpStatusDescription;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public File getFile(){
		return this.file;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public DataInputStream getDataInputStream(){
		return this.dataInputStream;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getHTTPStatusCode(){
		return this.httpStatusCode;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getHTTPStatusDescription(){
		return this.httpStatusDescription;
	}
	
	
	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	public byte[] getBytesFromResource() throws IOException{
		byte bytes[] = new byte[(int) this.file.length()];
		this.dataInputStream.read(bytes);
		return bytes;
	}
}
