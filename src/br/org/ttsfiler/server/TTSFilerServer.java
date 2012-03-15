package br.org.ttsfiler.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.org.ttsfiler.exception.TTSFilerException;

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
	
	
	public void start() throws TTSFilerException
	{
		try
		{
			this.server = new ServerSocket(this.port);
			this.listen();
		}
		catch (IOException e)
		{
			throw new TTSFilerException("Some error ocurred while trying to start the TTSFilerServer", e.getCause());
		}
	}
	
	
	public void stop() throws TTSFilerException
	{
		try
		{
			this.server.close();
		} 
		catch (IOException e) 
		{
			throw new TTSFilerException("Some error ocurred while trying to stop the TTSFilerServer", e.getCause());
		}
	}
	
	
	protected void listen() throws TTSFilerException
	{
		while(true)
		{
			try 
			{
				Socket socket = this.server.accept();
				this.startHTTPRequestHandlerJob(socket);
			}
			catch (Exception e) 
			{
				throw new TTSFilerException("Impossible to accept new connection in TTSFilerServer", e.getCause());
			}
		}
	}

	
	protected void startHTTPRequestHandlerJob(Socket socket)
	{
		HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(socket);
		Thread httpHandlerJob = new Thread(httpRequestHandler);
		httpHandlerJob.start();
	}

	
	public static void main(String[] args) throws IOException
	{
		TTSFilerServer server = new TTSFilerServer(54873);
		try 
		{
			System.out.println("starting");
			server.start();
		}
		catch (TTSFilerException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}

