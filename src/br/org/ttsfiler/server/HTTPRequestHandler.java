package br.org.ttsfiler.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		InputStreamReader input = new InputStreamReader(socket.getInputStream());
		BufferedReader reader = new BufferedReader(input);
		this.httpRequest = new HTTPRequest();
		String httpHeader;
		
		while((httpHeader = reader.readLine()) != null && !httpHeader.equals("")){
			this.httpRequest.addHTTPHeader(httpHeader);
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
