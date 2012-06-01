package br.org.ttsfiler.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TTSFilerServer 
{

	private ServerSocket server;
	private int port;
	
	
	public TTSFilerServer()
	{
		this.port = 8182;
	}

	
	public TTSFilerServer(int port)
	{
		this.port = port;
	}
	
	
	public void start()
	{
		try
		{
			this.server = new ServerSocket(this.port);
			this.listen();
		}
		catch (IOException e)
		{
			e.printStackTrace(); //TODO Handle the exception
		}
	}
	
	
	public void stop() throws IOException
	{
		try
		{
			this.server.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace(); //TODO Handle the exception
		}
	}
	
	
	protected void listen() throws IOException
	{
		while(true)
		{
			Socket socket = this.server.accept();
			this.startHTTPRequestHandlerJob(socket);
		}
	}

	
	protected void startHTTPRequestHandlerJob(Socket socket)
	{
		HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(socket);
		Thread httpHandlerJob = new Thread(httpRequestHandler);
		httpHandlerJob.start();
	}

}

