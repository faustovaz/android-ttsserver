package br.org.tts.resource;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

import br.org.tts.util.TTSServerProperties;

public class RequestedResource {

	private File file;
	private DataInputStream dataInputStream;
	private int httpStatusCode;
	private String httpStatusDescription;
	private int totalBytesRead;
	private Boolean areThereBytesToRead;
	
	
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
		this.totalBytesRead = 0;
		this.areThereBytesToRead = Boolean.TRUE;
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
//	public byte[] getBytesFromResource() throws IOException{
//		int fileSize = this.getFileSize();
//		byte bytes[] = new byte[fileSize];
//		this.dataInputStream.read(bytes);
//		return bytes;
//	}
	
	
	protected int getFileSize(){
		if (this.file != null){
			return (int) this.file.length();
		}
		return 0;
	}
	
	
	public Boolean areThereBytesToRead(){
		return this.areThereBytesToRead;
	}
	
	
	public byte[] getNextBytes() throws IOException{
		byte bytes[] = new byte[this.getNumberOfBytesToRead()];
		this.dataInputStream.read(bytes);
		return bytes;
	}
	
	
	protected int getNumberOfBytesToRead(){
		int maxNumberOfBytes = Integer.valueOf(TTSServerProperties.getMaxNumberOfBytesToReadWithoudSengind());
		int fileSize = this.getFileSize();

		if (maxNumberOfBytes > fileSize){
			this.areThereBytesToRead = false;
			return fileSize;
		}
		else{
			this.totalBytesRead = this.totalBytesRead + maxNumberOfBytes;
			if(this.totalBytesRead > fileSize){
				int numberOfBytesRemaining = this.totalBytesRead - fileSize;
				this.areThereBytesToRead = false;
				return numberOfBytesRemaining;
			}
			else{
				return maxNumberOfBytes;
			}
		}
	}
	
	public void close() throws IOException{
		this.dataInputStream.close();
	}
	
}
