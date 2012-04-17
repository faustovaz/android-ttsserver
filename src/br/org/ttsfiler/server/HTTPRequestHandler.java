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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.MimetypesFileTypeMap;

import br.org.ttsfiler.util.TTSServerProperties;

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
		File file;
		FileInputStream fileInputStream;
		DataInputStream dataInputStream;
		byte bytes[];
		try 
		{
			file = new File(TTSServerProperties.getDocumentRoot() + this.httpRequest.getResource());
			fileInputStream = new FileInputStream(file);
			dataInputStream = new DataInputStream(fileInputStream);
			bytes = new byte[(int) file.length()];
			dataInputStream.readFully(bytes);
			
			PrintStream input = new PrintStream(this.socket.getOutputStream());
			input.print(this.buildHTMLHeader(file));
			input.write(bytes);
			input.close();
			this.socket.close();
		}
		catch (IOException e) 
		{
			PrintStream input;
			try 
			{
				input = new PrintStream(this.socket.getOutputStream());
				file = new File(TTSServerProperties.getDocumentRoot() + "/404.html");
				fileInputStream = new FileInputStream(file);
				dataInputStream = new DataInputStream(fileInputStream);
				bytes = new byte[(int) file.length()];
				dataInputStream.readFully(bytes);
				input.print(this.buildHTML404Header(file));
				input.write(bytes);
				input.close();
				this.socket.close();
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

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
	
	protected String buildHTML404Header(File requiredResource)
	{
		String header = "HTTP/1.1 404 NOTFOUND\n";
		header = header + "Content-type: text/html\n";
		header = header + "Content-length: " + requiredResource.length() + "\n";
		header = header + "\n";
		return header;
	}

}
