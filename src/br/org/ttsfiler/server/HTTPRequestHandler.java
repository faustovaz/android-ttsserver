package br.org.ttsfiler.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
	}
	
	
	protected void loadHTTPRequest()
	{
		this.httpRequest = new HTTPRequest(HTTPMethod.GET, "/pagina.html");
	}
	
	
	protected void sendResponse()
	{
		
	}
	
	
//	public RequestData getRequestData(List<String> data)
//	{
//		String requestHeader = data.get(0);
//		String regex = "(GET|POST|PUT|DELETE)\\s([\\w/.]+)\\s([\\w/.]+)";
//		Pattern pattern = Pattern.compile(regex);
//		Matcher matcher = pattern.matcher(requestHeader);
//		
//		if(matcher.matches())
//		{
//			RequestData requestData = new RequestData(matcher.group(1), matcher.group(2));
//			return requestData;
//		}
//		
//		
//		return null;
//	}

}
