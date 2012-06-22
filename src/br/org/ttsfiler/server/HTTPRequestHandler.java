package br.org.ttsfiler.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.activation.MimetypesFileTypeMap;

/**
 * 
 * @author jefferson.fausto
 */
public class HTTPRequestHandler implements Runnable 
{

	private Socket socket;
	private HTTPRequest httpRequest;
	
	
	/**
	 * 
	 * @param socket
	 */
	public HTTPRequestHandler(Socket socket)
	{
		this.socket = socket;
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
		this.sendResponse();
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
	 * 
	 */
	protected void processHTTPRequest()
	{
		if(this.httpRequest.isGET()){
			this.sendResponse();
		}
		else{
			//Tratar post
		}
	}
	
	
	/**
	 * 
	 */
	protected void sendResponse(){
		try	{
			File file = new File("resources/webappfiles/" + this.httpRequest.getResource());
			FileInputStream fileInputStream = new FileInputStream(file);
			DataInputStream dataInputStream = new DataInputStream(fileInputStream);
			byte bytes[] = new byte[(int) file.length()];
			dataInputStream.readFully(bytes);
			
			PrintStream input = new PrintStream(this.socket.getOutputStream());
			input.print(this.buildHTMLHeader(file));
			input.write(bytes);
			input.close();
			this.socket.close();
		}
		catch (IOException e){
			this.send404Response();
		} 
	}
	
	
	
	/**
	 * 
	 * @param requiredResource
	 * @return
	 */
	protected String buildHTMLHeader(File requiredResource){
		MimetypesFileTypeMap mimeTypeMap = new MimetypesFileTypeMap();
		String header = "HTTP/1.1 200 OK\n";
		header = header + "Content_type: " + mimeTypeMap.getContentType(requiredResource) + "\n";
		header = header + "Content_length: " + requiredResource.length() + "\n";
		header = header + "\n";
		return header;
	}
	
	
	/**
	 * 
	 */
	protected void send404Response(){
		try{
			PrintStream input = new PrintStream(this.socket.getOutputStream());
			File file = new File("resources/webappfiles/404.html");
			FileInputStream fileInputStream = new FileInputStream(file);
			DataInputStream dataInputStream = new DataInputStream(fileInputStream);
			byte bytes[] = new byte[(int) file.length()];
			dataInputStream.readFully(bytes);
			input.print(this.buildHTML404Header(file));
			input.write(bytes);
			input.close();
			this.socket.close();
		} 
		catch (IOException e1){
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param requiredResource
	 * @return
	 */
	protected String buildHTML404Header(File requiredResource){
		String header =		"HTTP/1.1 404 NOTFOUND\n";
		header = header + 	"Content-type: text/html\n";
		header = header + 	"Content-length: " + requiredResource.length() + "\n";
		header = header + 	"\n";
		return header;
	}	
}
