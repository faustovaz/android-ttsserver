package br.org.tts.httpserver.server;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.org.tts.httpserver.enumerator.HTTPMethod;
import br.org.tts.httpserver.util.HTTPRequestParser;
import br.org.tts.httpserver.util.TTSServerProperties;

/**
 * <b> HTTPRequest </b>
 * <br/>
 * A representation of HTTP Request (with Method used, request resource and sent HTTP Headers)	 
 * @author Fausto Vaz
 *
 */
public class HTTPRequest 
{
	
	private String resource;
	private HTTPMethod method;
	private Map<String, String> headers;
	private HTTPRequestParser httpRequestParser;
	private Boolean isARequestToDownload = false; 
	
		
	/**
	 * 
	 * @return Requested Resource (String)
	 */
	public String getResource()	{
		return this.resource;
	}
	
	
	
	/**
	 * 
	 * @return is HTTP Method GET (Boolean)
	 */
	public boolean isGET()
	{
		return this.method == HTTPMethod.GET;
	}
	
	
	
	/**
	 * 
	 * @return is HTTP Method POST (Boolean)
	 */
	public boolean isPOST()
	{
		return this.method == HTTPMethod.POST;
	}
	
	
	
	/**
	 * 
	 * @param resource
	 */
	protected void setResource(String resource)
	{
		try{
			resource = URLDecoder.decode(resource, "UTF-8");
			if(resource.equals("/")){
				this.resource = TTSServerProperties.getDocumentRoot() + "/index.html";
			}
			else{
				String resourceSplitted[] = resource.split("/"); //Example of expected resource download/file
				if(resourceSplitted[1].equals("download")){
					this.resource = TTSServerProperties.uploadedFilesPath() + resourceSplitted[2];
					this.isARequestToDownload = true;
				}
				else{
					this.resource = TTSServerProperties.getDocumentRoot() + resource;
				}
			}
		}
		catch(UnsupportedEncodingException e){
			e.printStackTrace(); //TODO - Handle this exception properly
		}
	}
	
	
	/**
	 * 
	 * @param method
	 */
	protected void setHTTPMethod(String method)
	{
		this.method = (method.equals(HTTPMethod.GET.toString())) ? HTTPMethod.GET : HTTPMethod.POST;
	}
	
	
	/**
	 * Adds HTTP Header Field and Value
	 * @param field (String)
	 * @param value (String)
	 */
	public void addHTTPHeaderField(String field, String value){
		if(this.headers == null)
			this.headers = new HashMap<String, String>();
		this.headers.put(field, value);
	}
	
	
	/**
	 * 
	 * @param name
	 * @return The value of the field represented by name (String)
	 */
	public String getHTTPHeaderFieldValue(String name){
		String value = this.headers.get(name);
		if (value == null)
			return "0";
		return value;
	}
	
	
	/**
	 * Load the HTTP Header
	 * @param header
	 */
	public void addHTTPHeader(String header){
		HTTPRequestParser httpRequestParser = this.getHTTPRequestParser();
		httpRequestParser.parseHTTPHeader(header);
		if(httpRequestParser.isHTTPMethodDescriptor()){ 
			this.setHTTPMethod(httpRequestParser.getHTTPMethod());
			this.setResource(httpRequestParser.getResource());
		}
		else{
			this.addHTTPHeaderField(httpRequestParser.getField(), httpRequestParser.getFieldValue());
		}
	}
	
	
	/**
	 * @return {@link HTTPRequestParser}
	 */
	protected HTTPRequestParser getHTTPRequestParser(){
		if (this.httpRequestParser == null)
			this.httpRequestParser = new HTTPRequestParser();
		return this.httpRequestParser;
	}
	
	
	/**
	 * 
	 * @return fileName
	 */
	public String getUploadedFileName(){
		String contentDisposition = this.getHTTPHeaderFieldValue("Content-Disposition");
		if (contentDisposition != null){
			Pattern pattern = Pattern.compile(HTTPRequestParser.REGEX_CONTENT_DISPOSITION_FILE_NAME);
			Matcher matcher;
			String properties[] = contentDisposition.split(";");
			for (String property : properties) {
				property = property.trim();
				matcher = pattern.matcher(property);
				if(matcher.matches()){
					return matcher.group(1);
				}
			}
		}
		return null;
	}
	
	
	public String getUploadedFileName(String t){
		String contentDisposition = this.getHTTPHeaderFieldValue("Content-Disposition");
		if (contentDisposition != null){
			Pattern pattern = Pattern.compile(HTTPRequestParser.REGEX_CONTENT_DISPOSITION_FILE_NAME);
			Matcher matcher;
			String properties[] = contentDisposition.split(";");
			for (String property : properties) {
				property = property.trim();
				matcher = pattern.matcher(property);
				if(matcher.matches()){
					String fieldAndValue[] = property.split("=");
					return fieldAndValue[1];
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 * @return boundary (HTTP Field)
	 */
	public String getFileBoundary(){
		String contentType = this.getHTTPHeaderFieldValue("Content-Type");
		if (contentType != null){
			String regex = "boundary=(-+[\\w]+)";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher;
			String contentTypeSplitted[] = contentType.split(";");
			for (String contentTypeProperty : contentTypeSplitted){
				contentTypeProperty = contentTypeProperty.trim();
				matcher = pattern.matcher(contentTypeProperty);
				if(matcher.matches()){
					return matcher.group(1);
				}
			}			
		}
		return this.getHTTPHeaderFieldValue("File-Boundary");
	}
	
	public Boolean isResourceMainIndexFile(){
		return this.getResource().equals(TTSServerProperties.getDocumentRoot() + "/index.html");
	}
	
	public Boolean isResourceForDownload(){
		return this.isARequestToDownload;
	}
	
}
