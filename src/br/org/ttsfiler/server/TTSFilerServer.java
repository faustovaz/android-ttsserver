package br.org.ttsfiler.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * 
 * @author jefferson.fausto
 */

public class TTSFilerServer{

	private ServerSocket server;
	private int port;
	
	
	/**
	 * 
	 */
	public TTSFilerServer()	{
		this.port = 8182;
	}

	
	/**
	 * 
	 * @param port
	 */
	public TTSFilerServer(int port){
		this.port = port;
	}
	
	
	/**
	 * 
	 */
	public void start(){
		try	{
			this.server = new ServerSocket(this.port);
			this.listen();
		}
		catch (IOException e){
			e.printStackTrace(); //TODO Handle the exception
		}
	}
	
	
	/**
	 * 
	 * @throws IOException
	 */
	public void stop() throws IOException	{
		try	{
			this.server.close();
		} 
		catch (IOException e){
			e.printStackTrace(); //TODO Handle the exception
		}
	}
	
	
	/**
	 * 
	 * @throws IOException
	 */
	protected void listen() throws IOException{
		while(true){
			Socket socket = this.server.accept();
			this.startHTTPRequestHandlerJob(socket);
		}
	}

	
	/**
	 * 
	 * @param socket
	 */
	protected void startHTTPRequestHandlerJob(Socket socket){
		HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(socket);
		Thread httpHandlerJob = new Thread(httpRequestHandler);
		httpHandlerJob.start();
	}

}

