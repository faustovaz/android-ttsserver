package br.org.ttsfiler.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

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
		BufferedInputStream reader = new BufferedInputStream(socket.getInputStream());
		this.httpRequest = new HTTPRequest();
		byte bytesRead[] = new byte[1];
		byte byteRead;
		char charRead;
		String httpHeader = "";
		
		while(reader.read(bytesRead) != -1){
			byteRead = bytesRead[0];
			charRead = (char) byteRead;
			if(charRead != '\r'){
				if(charRead != '\n'){
					httpHeader = httpHeader + charRead;
				}
				else{
					this.httpRequest.addHTTPHeader(httpHeader);
					httpHeader = "";
				}
			}
			else{
				if (httpHeader.equals("")){
					break;
				}
			}
		}
	}
	
	
	
	/**
	 * Handle HTTP and process request depending on HTTP Method
	 */
	protected void processHTTPRequest()
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
	 * 
	 */
	protected void processHTTPPostRequest(){

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
