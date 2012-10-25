package br.org.tts.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

import br.org.tts.exception.TTSException;
import br.org.tts.log.Logger;
import br.org.tts.resource.RequestedResource;
import br.org.tts.resource.ResourceManager;
import br.org.tts.util.HTTPHeaderBuilder;

/**
 * <b> HTTPRequestHandler </b>
 * </br>
 * Handle HTTP Requests
 * @author Fausto Vaz
 */
public class HTTPRequestHandler implements Runnable{

	
	private Socket socket;
	private HTTPRequest httpRequest;
	private HTTPHeaderBuilder httpHeaderBuilder;
	private BufferedInputStream reader;
	private ResourceManager resourceManager;
	
	
	/**
	 * 
	 * @param socket
	 */
	public HTTPRequestHandler(Socket socket){
		this.socket = socket;
		this.httpHeaderBuilder = new HTTPHeaderBuilder();
	}
	
	
	/**
	 * 
	 */
	@Override
	public void run(){
		try	{
			this.handleRequest();
			this.socket.close();
		} 
		catch(TTSException ttsException){
			Logger.error(ttsException.getTTSCodeMessage(), ttsException.getMessage(), ttsException.getCause());
		}
		catch (IOException e){
			Logger.error("TTSServerMessage.Socket_IOERROR", e.getMessage(), e.getCause());
		}
	}

	
	/**
	 * 
	 * @throws IOException
	 */
	protected void handleRequest() throws TTSException{
		this.readHTTPHeaders();
		if (this.getHTTPRequest().isGET()){
			this.processHTTPGetRequest();
		}
		else{
			this.processHTTPPostRequest();
		}
	}
	
	
	/**
	 * 
	 * @throws TTSException 
	 */
	protected int readHTTPHeaders() throws TTSException{
		try{
			this.setReader(this.socket.getInputStream()); 		
			byte byteRead[] = new byte[1];
			char charRead;
			int numberOfBytesRead = 0;
			StringBuffer buffer = new StringBuffer();
			while(this.reader.read(byteRead) != -1){
				charRead = (char) byteRead[0];
				buffer.append(charRead);
				numberOfBytesRead++;
				if(charRead == '\n'){
					if(buffer.toString().equals("\r\n")){
						break;
					}
					else{
						this.addHTTPHeader(buffer);
					}
				}
			}
			return numberOfBytesRead;
		}
		catch (IOException ioException){
			throw new TTSException("TTSServerMessage.Socket_IOERROR", ioException.getMessage(), ioException.getCause());
		}
	}
	
	
	protected void processHTTPGetRequest() throws TTSException{
		try{
			this.sendRequestedResource();	
		}
		catch(IOException ioException){
			throw new TTSException("TTSServerMessage.Socket_IOERROR", ioException.getMessage(), ioException.getCause());
		}
		
	}
	
	
	/**
	 * @throws IOException 
	 * 
	 */
	protected void processHTTPPostRequest() throws TTSException{
		int numberOfBytesRead = this.readHTTPHeaders(); //Continue reading HTTP Fields, ex.: Content-Disposition, boundary, etc
		try{
			Integer contentLength = Integer.valueOf(this.httpRequest.getHTTPHeaderFieldValue("Content-Length"));
			String fileBoundary = this.httpRequest.getFileBoundary();
			Integer totalOfBytesToBeRead = contentLength - numberOfBytesRead - fileBoundary.length() - 6;
			byte byteOfTheFile[] = new byte[totalOfBytesToBeRead];
			byte byteRead[] = new byte[1];
			int index = 0;
			while(totalOfBytesToBeRead > 0 ){
				this.reader.read(byteRead);
				byteOfTheFile[index] = byteRead[0];
				index++;
				totalOfBytesToBeRead--;
			}
			//The purpose of this is read file contents, so other types of posts request will not be handled
			this.getResourceManager().saveResource(byteOfTheFile, this.httpRequest.getUploadedFileName());
			this.sendRequestedResource();
		}
		catch(IOException ioException){
			throw new TTSException("TTSServerMessage.Socket_IOERROR", ioException.getMessage(), ioException.getCause());
		}
	}	
	
	
	protected void addHTTPHeader(StringBuffer httpHeaderBuffer){
		httpHeaderBuffer.delete(httpHeaderBuffer.length() - 2, httpHeaderBuffer.length()); //Delete the last two chars in the buffer: \r\n
		this.getHTTPRequest().addHTTPHeader(httpHeaderBuffer.toString());
		httpHeaderBuffer.delete(0, httpHeaderBuffer.length()); //Empty the buffer.
	}
	
	
	protected void sendRequestedResource() throws IOException{
		RequestedResource requestedResource = this.getResourceManager().getRequestedResource(this.httpRequest);
		this.sendResponse(requestedResource);
	}
			
	
	/**
	 * @throws IOException 
	 * 
	 */
	protected void sendResponse(RequestedResource requestedResource) throws IOException{
			byte bytes[] = requestedResource.getBytesFromResource();
			PrintStream input = new PrintStream(this.socket.getOutputStream());
			input.print(this.httpHeaderBuilder.buildHTTPHeader(requestedResource));
			input.write(bytes);
			input.close();
	}
	
	
	protected void setReader(InputStream stream){
		if(this.reader == null){
			this.reader = new BufferedInputStream(stream);
		}
	}
	
	
	protected HTTPRequest getHTTPRequest(){
		if (this.httpRequest == null){
			this.httpRequest = new HTTPRequest();
		}
		return this.httpRequest;
	}
	
	
	protected ResourceManager getResourceManager(){
		if(this.resourceManager == null){
			this.resourceManager = new ResourceManager();
		}
		return this.resourceManager;
	}
	
}
