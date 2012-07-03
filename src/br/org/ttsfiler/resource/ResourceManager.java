package br.org.ttsfiler.resource;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import br.org.ttsfiler.server.HTTPRequest;
import br.org.ttsfiler.util.TTSServerProperties;
import br.org.ttsfiler.util.TemplateEngine;

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
		getTemplateEngine().generateRequestedResourceFromTemplate(this.httpRequest.getResource());
		this.file = new File(this.httpRequest.getResource());
		if(this.file.exists()){
			this.httpStatusCode = 200;
			this.httpStatusDescription = "OK";
		}
		else{
			this.file = new File(TTSServerProperties.getDocumentRoot()+ "/404.html");
			this.httpStatusCode = 400;
			this.httpStatusDescription = "NOT FOUND";
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

}
