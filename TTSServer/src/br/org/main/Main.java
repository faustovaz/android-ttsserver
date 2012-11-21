package br.org.main;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.FileNameMap;
import java.net.URLConnection;

import com.sun.imageio.plugins.common.InputStreamAdapter;

import br.org.main.FileMap;
import br.org.tts.exception.TTSException;
import br.org.tts.server.TTSServer;

public class Main{

	public static void main(String[] args) throws TTSException, IOException{
//		TTSServer server = new TTSServer();
//		System.out.println("http://" + server.getIPAddress() + ":" + server.getPort());
//		server.start();	  
		
		File f = new File("resources/META-INF/mime.types");
		FileInputStream s = new FileInputStream(f);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(s));
		String line;
		while((line = reader.readLine()) != null){
			if(!line.contains("#")){
				String mimeTypeSplitted[] = line.split("[\\s]+");
				System.out.print(mimeTypeSplitted[0].trim() + " ");
				System.out.println(mimeTypeSplitted[1].trim());
			}
		}
	}
	
}
