package br.org.tts.httpserver.resource;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import br.org.tts.app.TTSServerActivity;
import br.org.tts.httpserver.filemanager.TTSFileManager;
import br.org.tts.httpserver.server.HTTPRequest;
import br.org.tts.httpserver.util.TTSServerProperties;
import br.org.tts.httpserver.util.TemplateEngine;

/**
 * <b>ResourceManager</b>
 * </br>
 * @author Fausto Vaz
 *
 */
public class ResourceManager {

	private String fileName;
	private AssetFileDescriptor fileDescriptor;
	private FileInputStream fileInputStream;
	private DataInputStream dataInputStream;
	private HTTPRequest httpRequest;
	private int httpStatusCode;
	private String httpStatusDescription;
	private TTSFileManager fileManager;
	
	
	/**
	 * 
	 * @param httpRequest
	 * @return
	 */
	public RequestedResource getRequestedResource(HTTPRequest httpRequest){
		this.httpRequest = httpRequest;
		this.loadFileDescriptor();
		this.loadFileInputStream();
		this.loadDataInputStream();
		return new RequestedResource(this.fileName, this.fileDescriptor, this.dataInputStream, this.httpStatusCode, this.httpStatusDescription);
	}

	
	/**
	 * 
	 */
	protected void loadFileDescriptor(){
			this.getTemplateEngine().generateRequestedResourceFromTemplate(this.httpRequest.getResource());	
			AssetManager assetManager = TTSServerActivity.getAssetManager();
			try{
				this.fileDescriptor = assetManager.openFd(this.httpRequest.getResource());
				this.fileName = this.httpRequest.getResource();
				this.httpStatusCode = 200;
				this.httpStatusDescription = "OK";
			}
			catch(IOException ioExceptiopn){
				try{
					this.fileDescriptor = assetManager.openFd(this.httpRequest.getResource() + ".amr");
					this.fileName = this.httpRequest.getResource();
					this.httpStatusCode = 200;
					this.httpStatusDescription = "OK";
				}
				catch (IOException e) {
					this.load404File();
				}
				
			}
	}
	
	
	/**
	 * 
	 */
	protected void loadFileInputStream(){
		try{
			this.fileInputStream = this.fileDescriptor.createInputStream();
		}
		catch(IOException fileNotFound){
			this.load404File();
			try {
				this.fileInputStream = this.fileDescriptor.createInputStream();
			} 
			catch (IOException e) {
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
	
	
	public void saveResource(List<Byte> bytes, String resourceName) throws IOException{
		this.getFileManager().save(bytes, resourceName);
	}
	
	public void finalizeSaveResource() throws IOException{
		this.getFileManager().closeSavedFile();
	}
	
	
	public void load404File(){
		try{
			this.fileDescriptor = TTSServerActivity.getAssetManager().openFd(TTSServerProperties.getDocumentRoot() + "/404.html.amr");
			this.fileName = "404.html";
			this.httpStatusCode = 400;
			this.httpStatusDescription = "NOT FOUND";
		}
		catch(IOException ioException){
			//dfd
		}
		
	}
	
	
	public TTSFileManager getFileManager(){
		if (this.fileManager == null){
			this.fileManager = new TTSFileManager();
		}
		return this.fileManager;
	}

}
