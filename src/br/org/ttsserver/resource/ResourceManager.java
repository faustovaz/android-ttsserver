package br.org.tts.resource;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import br.org.tts.filemanager.TTSFileManager;
import br.org.tts.server.HTTPRequest;
import br.org.tts.util.TTSServerProperties;
import br.org.tts.util.TemplateEngine;

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
	private HTTPRequest httpRequest;
	private int httpStatusCode;
	private String httpStatusDescription;
	
	
	/**
	 * 
	 * @param httpRequest
	 * @return
	 */
	public RequestedResource getRequestedResource(HTTPRequest httpRequest){
		this.httpRequest = httpRequest;
		this.loadFile();
		this.loadFileInputStream();
		this.loadDataInputStream();
		return new RequestedResource(this.file, this.dataInputStream, this.httpStatusCode, this.httpStatusDescription);
	}

	
	/**
	 * 
	 */
	protected void loadFile(){
		this.getTemplateEngine().generateRequestedResourceFromTemplate(this.httpRequest.getResource());
		this.file = new File(this.httpRequest.getResource());
		if(this.file.exists()){
			this.httpStatusCode = 200;
			this.httpStatusDescription = "OK";
		}
		else{
			this.load404File();
		}
	}
	
	
	/**
	 * 
	 */
	protected void loadFileInputStream(){
		try{
			this.fileInputStream = new FileInputStream(this.file);
		}
		catch(FileNotFoundException fileNotFound){
			this.load404File();
			try {
				this.fileInputStream = new FileInputStream(this.file);
			} 
			catch (FileNotFoundException e) {
				//This is catch block is kind of impossible to execute, once the file we are attempting to open is the 404.html
				//The 404.html file is located at resources/webappfiles and as a part of the TTSServer is going to be impossible to not find it.
				e.printStackTrace(); //Just to be nice :D
			}
		}
	}
	
	/**
	 * 
	 */
	protected void loadDataInputStream(){
		this.dataInputStream = new DataInputStream(this.fileInputStream);
	}
	
	
	protected TemplateEngine getTemplateEngine(){
		return new TemplateEngine();
	}
	
	
	public void saveResource(byte bytes[], String resourceName){
		TTSFileManager fileManager = new TTSFileManager();
		fileManager.save(bytes, resourceName);
	}
	
	
	public void load404File(){
		this.file = new File(TTSServerProperties.getDocumentRoot()+ "/404.html");
		this.httpStatusCode = 400;
		this.httpStatusDescription = "NOT FOUND";
	}

}
