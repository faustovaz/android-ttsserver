package br.org.ttsfiler.server;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import br.org.ttsfiler.resource.RequestedResource;
import br.org.ttsfiler.resource.ResourceManager;
import br.org.ttsfiler.util.HTTPHeaderBuilder;

/**
 * <b> HTTPRequestHandler </b>
 * </br>
 * Handle HTTP Requests
 * @author Fausto Vaz
 */
public class HTTPRequestHandler implements Runnable 
{

	
	private Socket socket;
	private HTTPRequest httpRequest;
	private HTTPHeaderBuilder httpHeaderBuilder;
	private BufferedInputStream reader;
	
	
	/**
	 * 
	 * @param socket
	 */
	public HTTPRequestHandler(Socket socket){
		this.socket = socket;
		this.httpHeaderBuilder = new HTTPHeaderBuilder();
	}
	
	
	/**
	 * 
	 */
	@Override
	public void run(){
		try	{
			this.handleRequest();
			this.socket.close();
		} 
		catch (IOException e){
			e.printStackTrace(); //TODO Handle properly this exception;
		}
	}

	
	/**
	 * 
	 * @throws IOException
	 */
	protected void handleRequest() throws IOException	{
		this.readHTTPHeaders();
		this.processHTTPRequest();
	}
	
	
	
	/**
	 * 
	 * @throws IOException
	 */
	protected void readHTTPHeaders() throws IOException{
		this.reader = new BufferedInputStream(this.socket.getInputStream());
		this.httpRequest = new HTTPRequest();
		byte byteRead[] = new byte[1];
		char charRead;
		StringBuffer buffer = new StringBuffer();
		
		while(this.reader.read(byteRead) != -1){
			charRead = (char) byteRead[0];
			buffer.append(charRead);
			if(charRead == '\n'){
				if(buffer.toString().equals("\r\n")){
					break;
				}
				else{
					buffer.delete(buffer.length() - 2, buffer.length()); //Delete the last two char in the string: \r\n
					this.httpRequest.addHTTPHeader(buffer.toString());
					buffer.delete(0, buffer.length()); //Empty the string of the buffer.
				}
			}
			
		}
		
	}
	
	
	
	/**
	 * Handle HTTP and process request depending on HTTP Method
	 * @throws IOException 
	 */
	protected void processHTTPRequest() throws IOException
	{
		if(this.httpRequest.isGET()){
			this.processHTTPGetRequest();
		}
		else{
			
			this.processHTTPPostRequest();
		}
	}
	
	
	/**
	 * 
	 */
	protected void processHTTPGetRequest(){
		ResourceManager manager = new ResourceManager();
		RequestedResource requestedResource = manager.getRequestedResource(this.httpRequest);
		this.sendResponse(requestedResource);
	}
	
	
	/**
	 * @throws IOException 
	 * 
	 */
	protected void processHTTPPostRequest() throws IOException{
		byte byteRead[] = new byte[1];
		char charRead;
		StringBuffer buffer = new StringBuffer();
		List<String> strings = new ArrayList<String>();
		int length = 0;
		while(this.reader.read(byteRead) != -1){
			charRead = (char) byteRead[0];
			length++;
			buffer.append(charRead);
			if(charRead == '\n'){
				if(buffer.toString().equals("\r\n")){
					break;
				}
				else{
					buffer.delete(buffer.length() - 2, buffer.length());
					System.out.println(buffer.toString());
					strings.add(buffer.toString());
					buffer.delete(0, buffer.length());
					System.out.println(buffer.toString());
				}
			}
		}
		
		Integer contentLength = Integer.valueOf(this.httpRequest.getHTTPHeaderFieldValue("Content-Length"));
		byte byteOfFile[] = new byte[contentLength];
		byteRead = new byte[1];
		int i = length;
		
		System.out.println("Content-length = " + contentLength);
		System.out.println("Length = " + length);
		
		
		
		
//		while(i < (contentLength - 1)){
//			byteOfFile[i] = byteRead[0];
//			i++;
		//209631184319562528981191723699		
		
		while(this.reader.read(byteRead) != -1){
			System.out.println("Byte = " + (char) byteRead[0] + " TTS = " + byteRead[0]);
		}
		
//		for (int i = 0; i < contentLength - 1 || this.reader.read(byteRead) != -1; i++){
//			this.reader.read(byteRead);
//			byteOfFile[i] = byteRead[0];
//			System.out.println(i);
//		}
		FileOutputStream outPut = new FileOutputStream("/home/fausto/teste.jpg");
		outPut.write(byteOfFile);
		outPut.close();
	}
	
	/**
	 * 
	 */
	protected void sendResponse(RequestedResource requestedResource){
		try{
			byte b[] = requestedResource.getBytesFromResource();
			PrintStream input = new PrintStream(this.socket.getOutputStream());
			input.print(this.httpHeaderBuilder.buildHTTPHeader(requestedResource));
			input.write(b);
			input.close();
			this.socket.close();
		}
		catch(IOException ioException){
			
		}
	}
	
}
