package br.org.main;

import br.org.tts.server.TTSServer;

public class Main{

	public static void main(String[] args) throws Exception{
		TTSServer server = new TTSServer();
		System.out.println("http://" + server.getIPAddress() + ":" + server.getPort());
		server.start();	  
	}
	
}
