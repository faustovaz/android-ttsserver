package br.org.ttsfiler.resource;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import br.org.ttsfiler.server.HTTPRequest;
import br.org.ttsfiler.util.TTSServerProperties;

/**
 * <b>ResourceManager</b>
 * </br>
 * @author Fausto Vaz
 *
 */
public class ResourceManager {

	private File file;
	private FileInputStream fileInputStream;
	private DataInputStream dataInputStream;
	
	public RequestedResource getRequestedResource(HTTPRequest httpRequest){
	
		this.file = new File(TTSServerProperties.getDocumentRoot() +  httpRequest.getResource());
		if (this.file.exists())
		
		

			
			
			
			File file;
			FileInputStream fileInputStream;
			try	{
				file = new File(TTSServerProperties.getDocumentRoot() + this.httpRequest.getResource());
				fileInputStream = new FileInputStream(file);
				DataInputStream dataInputStream = new DataInputStream(fileInputStream);
				byte bytes[] = new byte[(int) file.length()];
				dataInputStream.readFully(bytes);
				PrintStream input = new PrintStream(this.socket.getOutputStream());
				input.print(this.httpHeaderBuilder.buildHTTP200Header(file));
				input.write(bytes);
				input.close();
				this.socket.close();
			}
			catch(FileNotFoundException fileNotFound){
				try{
					file = new File(TTSServerProperties.uploadedFilesPath() + this.httpRequest.getResource());
					fileInputStream = new FileInputStream(file);
					DataInputStream dataInputStream = new DataInputStream(fileInputStream);
					byte bytes[] = new byte[(int) file.length()];
					dataInputStream.readFully(bytes);
					
					PrintStream input = new PrintStream(this.socket.getOutputStream());
					input.print(this.httpHeaderBuilder.buildHTTP200Header(file));
					input.write(bytes);
					input.close();
					this.socket.close();
				}
				catch (IOException e){
					this.send404Response();
				} 
			}
			catch (IOException e){
				this.send404Response();
			} 

		}
		
		
		
		
		
		
		
		
		
		
		return new RequestedResource();
	}
	
	
}
