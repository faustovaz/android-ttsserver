package br.org.main;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.org.ttsfiler.server.HTTPRequest;
import br.org.ttsfiler.server.HTTPRequestHandler;
import br.org.ttsfiler.server.TTSFilerServer;

public class Main{

	public static void main(String[] args) throws IOException{
		TTSFilerServer server = new TTSFilerServer(8086);
		server.start();
		
//		byte b = 13;
//		char newLine = '\n';
//		char newFeed = '\r';
//		
//		byte n = (byte) newLine;
//		byte f = (byte) newFeed;
//		
//		System.out.println("NewLine (char)= " + newLine);
//		System.out.println("newFeed (char)= " + newFeed);
//		
//		System.out.println("NewLine (byte)= " + n);
//		System.out.println("NewFeed (byte)= " + f);
		

		//HTTPRequest http = new HTTPRequest();
		//String s = "Content-Disposition: form-data; name=\"arquivoName\"; filename=\"r.pdf\"";
		//System.out.println(s);
		//http.addHTTPHeader(s);
		//System.out.println(http.getUploadedFileName());
		
		
	}
	
}
