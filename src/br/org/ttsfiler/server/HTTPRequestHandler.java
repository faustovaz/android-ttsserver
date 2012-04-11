package br.org.ttsfiler.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		//String defaultDir = "/home/fausto/www";
		String defaultDir = "D:\\www";
		String resource = this.httpRequest.getResource();
		
		try 
		{
			if(resource.equals("/foto.jpg"))
			{
				File file = new File(defaultDir + resource);
				FileInputStream fileInputStream = new FileInputStream(file);
				DataInputStream dataInputStream = new DataInputStream(fileInputStream);
				byte bytes[] = new byte[(int) file.length()];
				dataInputStream.readFully(bytes);
				
				System.out.println(Integer.MAX_VALUE);
				System.out.println(bytes.length);
				System.out.println(bytes);
				
				PrintStream input = new PrintStream(this.socket.getOutputStream());
				input.println("HTTP/1.1 200 OK");
				input.println("Content_type: image/gif");
				input.println("Content_type: text/html");
				input.println("Content_length: " + bytes.length);
				input.println("");
				input.write(bytes);
				input.close();
				this.socket.close();
			}
			else
			{
			
				FileInputStream fileInputStream = new FileInputStream(defaultDir + resource);
				BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(fileInputStream)));
				String line;
				StringBuffer buffer = new StringBuffer();
				int contentLength = 0;
				while((line = reader.readLine()) != null)
				{
					line = line.trim();
					buffer.append(line);
					contentLength += line.length();
					
				}
				
				PrintStream input = new PrintStream(this.socket.getOutputStream());
				PrintWriter w = new PrintWriter(this.socket.getOutputStream());
				
				
				input.println("HTTP/1.1 200 OK");
				input.println("Content_type: text/html");
				input.println("Content_length: " + contentLength);
				input.println("");
				input.println(buffer.toString());
				input.close();
				this.socket.close();
			}
		} 
		catch (IOException e) 
		{
			PrintStream input;
			try 
			{
				input = new PrintStream(this.socket.getOutputStream());
				input.println("HTTP/1.1 404 NOTFOUND");
				input.println("Content-type: text/html");
				
				String content = "<html><head></head><body><h1>NOT FOUND</h1></body></html>";
				
				input.println("Content-length: " + content.length());
				input.println("");
				input.println(content);
				
				input.close();
				this.socket.close();
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} 
		
		
		//this.httpRequest.
		
	}

}
