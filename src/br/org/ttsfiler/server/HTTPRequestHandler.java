package br.org.ttsfiler.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import br.org.ttsfiler.util.HTTPHeaderBuilder;
import br.org.ttsfiler.util.TTSServerProperties;
import br.org.ttsfiler.util.TemplateEngine;

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
		TemplateEngine templateEngine = new TemplateEngine();
		if(this.httpRequest.isGET()){
			templateEngine.generateRequestedResourceFromTemplate(TTSServerProperties.getDocumentRoot() + this.httpRequest.getResource());
			this.sendResponse();
		}
		else{
			
			//Handle POST HTTP Request
		}
	}
	
	
	
	/**
	 * 
	 */
	protected void sendResponse(){
		File file;
		FileInputStream fileInputStream;
		try	{
			file = new File(TTSServerProperties.getDocumentRoot() + this.httpRequest.getResource());
			fileInputStream = new FileInputStream(file);
			DataInputStream dataInputStream = new DataInputStream(fileInputStream);
			byte bytes[] = new byte[(int) file.length()];
			dataInputStream.readFully(bytes);
			
			PrintStream input = new PrintStream(this.socket.getOutputStream());
			input.print(this.httpHeaderBuilder.buildHTTP200Header(file));
			input.write(bytes);
			input.close();
			this.socket.close();
		}
		catch(FileNotFoundException fileNotFound){
			try{
				file = new File(TTSServerProperties.uploadedFilesPath() + this.httpRequest.getResource());
				fileInputStream = new FileInputStream(file);
				DataInputStream dataInputStream = new DataInputStream(fileInputStream);
				byte bytes[] = new byte[(int) file.length()];
				dataInputStream.readFully(bytes);
				
				PrintStream input = new PrintStream(this.socket.getOutputStream());
				input.print(this.httpHeaderBuilder.buildHTTP200Header(file));
				input.write(bytes);
				input.close();
				this.socket.close();
			}
			catch (IOException e){
				this.send404Response();
			} 
		}
		catch (IOException e){
			this.send404Response();
		} 

	}
	
	
	
	/**
	 * 
	 */
	protected void send404Response(){
		try{
			PrintStream input = new PrintStream(this.socket.getOutputStream());
			File file = new File(TTSServerProperties.getDocumentRoot() + "404.html");
			FileInputStream fileInputStream = new FileInputStream(file);
			DataInputStream dataInputStream = new DataInputStream(fileInputStream);
			byte bytes[] = new byte[(int) file.length()];
			dataInputStream.readFully(bytes);
			input.print(this.httpHeaderBuilder.buildHTTP404Header(file));
			input.write(bytes);
			input.close();
			this.socket.close();
		} 
		catch (IOException e1){
			e1.printStackTrace();
		}
	}
	
}
