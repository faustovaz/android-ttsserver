package br.org.main;

import java.io.IOException;

import br.org.ttsfiler.server.TTSFilerServer;

public class Main 
{

	public static void main(String[] args) throws IOException
	{
		TTSFilerServer server = new TTSFilerServer(8086);
		server.start();
	}
	
}
