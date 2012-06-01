package br.org.ttsfiler.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import br.org.ttsfiler.enumerator.HTTPMethod;

public class HTTPRequestHandler implements Runnable 
{

	private Socket socket;
	private List<String> httpRequestHeaders;
	private String resource;
	private HTTPMethod method;
	
	
	public HTTPRequestHandler(Socket socket)
	{
		this.socket = socket;
	}
	
	
	@Override
	public void run()
	{
		try 
		{
			this.handleRequest();
			this.socket.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace(); //TODO Handle properly this exception;
		}
	}
	
	
	protected void handleRequest() throws IOException
	{
		this.readHTTPHeaders();
		this.loadHTTPMethodOfRequest();
		this.processHTTPRequest();
		this.sendResponse();
	}
	
	
	protected void readHTTPHeaders() throws IOException
	{
		this.httpRequestHeaders = new ArrayList<String>();
		InputStreamReader input = new InputStreamReader(socket.getInputStream());
		BufferedReader reader = new BufferedReader(input);
		String requestInfo = "";
		while((requestInfo = reader.readLine()) != null)
		{
			this.httpRequestHeaders.add(requestInfo);
			if(requestInfo.equals(""))
				break;
		}
	}
	
	
	protected void loadHTTPMethodOfRequest()
	{
		String header = this.httpRequestHeaders.get(0); //Format: METHOD RESOURCE HTTPVERSION - ex.: GET /index.html HTTP1.1
		String headerParts[] = header.split("\\s");
		this.setHTTPMethod(headerParts[0]);
		this.setResource(headerParts[1]);
	}
	
	
	protected void processHTTPRequest()
	{
	//	TemplateEngine engine = new TemplateEngine("resources/webappfiles/index.tpl");
	//	engine.createHTMLFile();
		
	}
	
	
	protected void sendResponse()
	{
		try 
		{
			File file = new File("resources/webappfiles/" + this.getResource());
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
		catch (IOException e) 
		{
			this.send404Response();
		} 
	}
	
	
	protected String buildHTMLHeader(File requiredResource)
	{
		MimetypesFileTypeMap mimeTypeMap = new MimetypesFileTypeMap();
		String header = "HTTP/1.1 200 OK\n";
		header = header + "Content_type: " + mimeTypeMap.getContentType(requiredResource) + "\n";
		header = header + "Content_length: " + requiredResource.length() + "\n";
		header = header + "\n";
		return header;
	}
	
	
	protected void send404Response()
	{
		try 
		{
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
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	}
	
	protected String buildHTML404Header(File requiredResource)
	{
		String header = "HTTP/1.1 404 NOTFOUND\n";
		header = header + "Content-type: text/html\n";
		header = header + "Content-length: " + requiredResource.length() + "\n";
		header = header + "\n";
		return header;
	}
	
	
	protected void setHTTPMethod(String method)
	{
		if(method.equals(HTTPMethod.GET.toString()))
		{
			this.method = HTTPMethod.GET;
		}
		else
		{
			this.method = HTTPMethod.POST;
		}
	}
	
	protected void setResource(String resource)
	{
		this.resource = resource;
	}
	
	protected String getResource()
	{
		if(this.resource.equals("/"))
			return "index.html";
		else
			return this.resource;
	}
	
	
	protected boolean isGET()
	{
		return this.method.equals(HTTPMethod.GET);
	}
	

}
