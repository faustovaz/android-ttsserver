package br.org.tts.httpserver.resource;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import android.content.res.AssetFileDescriptor;
import br.org.tts.app.TTSServerActivity;
import br.org.tts.httpserver.filemanager.TTSFileManager;
import br.org.tts.httpserver.server.HTTPRequest;
import br.org.tts.httpserver.util.TTSFileEntity;
import br.org.tts.httpserver.util.TTSServerProperties;
import br.org.tts.httpserver.util.TemplateEngine;

/**
 * <b>ResourceManager</b>
 * </br>
 * @author Fausto Vaz
 *
 */
public class ResourceManager {

	private TTSFileEntity fileEntity;
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
		this.loadTTSFileEntity();
		this.loadDataInputStream();
		return new RequestedResource(this.fileEntity, this.dataInputStream, this.httpStatusCode, this.httpStatusDescription);
	}

	
	/**
	 * 
	 */
	protected void loadTTSFileEntity(){
			this.getTemplateEngine().generateRequestedResourceFromTemplate(this.httpRequest.getResource());	
			this.fileEntity = new TTSFileEntity();
			try{
				if(this.httpRequest.isResourceMainIndexFile()){
					this.fileInputStream = TTSServerActivity.getContext().openFileInput("index.html");
					this.fileEntity.setName(this.httpRequest.getResource());
					this.fileEntity.setSize(this.fileInputStream.getChannel().size());
				}
				else{
					AssetFileDescriptor fileDescriptor = TTSServerActivity.getAssetManager().openFd(this.httpRequest.getResource());
					this.fileInputStream = fileDescriptor.createInputStream();
					this.fileEntity.setName(this.httpRequest.getResource());
					this.fileEntity.setSize(this.fileInputStream.getChannel().size());
				}
				this.httpStatusCode = 200;
				this.httpStatusDescription = "OK";
			}
			catch(IOException ioException){
				try{
					AssetFileDescriptor fileDescriptor = TTSServerActivity.getAssetManager().openFd(this.httpRequest.getResource() + ".amr");
					this.fileInputStream = fileDescriptor.createInputStream();
					this.fileEntity.setName(this.httpRequest.getResource());
					this.fileEntity.setSize(this.fileInputStream.getChannel().size());
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
			AssetFileDescriptor fileDescriptor = TTSServerActivity.getAssetManager().openFd(TTSServerProperties.getDocumentRoot() + "/404.html.amr");
			this.fileEntity.setName("404.html");
			this.fileEntity.setSize(fileDescriptor.getLength());
			this.fileInputStream = fileDescriptor.createInputStream();
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
