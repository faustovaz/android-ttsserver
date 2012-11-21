package br.org.tts.httpserver.resource;

import java.io.DataInputStream;
import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import br.org.tts.httpserver.util.TTSServerProperties;

public class RequestedResource {

	private String fileName;
	private AssetFileDescriptor fileDescriptor;
	private DataInputStream dataInputStream;
	private int httpStatusCode;
	private String httpStatusDescription;
	private int totalBytesRead;
	private Boolean areThereBytesToRead;
	
	
	/**
	 * 
	 * @param dataInputStream
	 * @param httpStatusCode
	 * @param httpStatusDescription
	 */
	public RequestedResource(String fileName, AssetFileDescriptor fileDescriptor, DataInputStream dataInputStream, int httpStatusCode, String httpStatusDescription){
		this.fileName = fileName;
		this.fileDescriptor = fileDescriptor;
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
	public String  getFileName(){
		return this.fileName;
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
		if (this.fileDescriptor != null){
			return (int) this.fileDescriptor.getLength();
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
	
	public int getFileLength(){
		return this.getFileSize();
	}
	
	public void close() throws IOException{
		this.dataInputStream.close();
	}
	
}
