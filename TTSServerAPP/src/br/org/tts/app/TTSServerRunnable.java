package br.org.tts.app;

import br.org.tts.httpserver.exception.TTSException;
import br.org.tts.httpserver.server.TTSServer;

public class TTSServerRunnable extends Thread {

	
	private TTSServer server;
	
	public TTSServerRunnable() throws TTSException{
		this.server = new TTSServer();
	}
	
	
	@Override
	public void run() {
		try {
			this.server.start();
		} catch (TTSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopServer() throws TTSException{
		this.server.stop();
	}
	
	public String getIP() throws TTSException{
		return this.server.getHTTPAddress();
	}

}
