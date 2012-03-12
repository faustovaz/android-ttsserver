package br.org.ttsfiler.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.org.ttsfiler.enumerator.HTTPMethod;

public class HTTPRequestHandler implements Runnable 
{

	private Socket socket;
	private List<String> requestMessages;
	private HTTPRequest httpRequest;
	
	
	public HTTPRequestHandler(Socket socket)
	{
		this.socket = socket;
	}
	
	
	@Override
	public void run()
	{
		try 
		{
			this.processRequest();
			this.socket.close();
		} 
		catch (IOException e) 
		{
			System.out.println("Arrrgh!!! We have a problem in closing the connection with some client!");
			e.printStackTrace();
		}
	}
	
	
	protected void processRequest()
	{
		this.readRequestMessages();
		this.loadHTTPRequest();
		this.sendResponse();
	}
	
	
	protected void readRequestMessages()
	{
		this.requestMessages = new ArrayList<String>();
		try 
		{
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String requestInfo = input.readLine();
			while(requestInfo != null)
			{
				this.requestMessages.add(requestInfo);
				requestInfo = input.readLine();
				if(requestInfo.equals(""))
					break;
			}
			socket.shutdownInput();
		}
		catch (IOException e) 
		{
			System.out.println("Arrrgh!!! We have a problem in reading the request messages");
			e.printStackTrace();
		}

	}
	
	
	protected void loadHTTPRequest()
	{
		String requestHeaderMessage = this.requestMessages.get(0);
		String regexForHTTPMessage = "(GET|POST|PUT|DELETE)\\s([\\w/.]+)\\s([\\w/.]+)";
		Pattern pattern = Pattern.compile(regexForHTTPMessage);
		Matcher matcher = pattern.matcher(requestHeaderMessage);
		if(matcher.matches())
		{
			this.httpRequest = new HTTPRequest(matcher.group(1), matcher.group(2));
		}
		else
		{
			System.out.println("Argh!! An error have been found trying to parser the HTTP REquest Header MEssage");
		}
	}
	
	
	protected void sendResponse()
	{
		
	}

}
