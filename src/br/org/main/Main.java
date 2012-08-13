package br.org.main;

import java.io.IOException;

import br.org.ttsfiler.server.TTSFilerServer;

public class Main{

	public static void main(String[] args) throws IOException{
		TTSFilerServer server = new TTSFilerServer(8088);
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
		
		
		
		
	}
	
}
