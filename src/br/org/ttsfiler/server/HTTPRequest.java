package br.org.ttsfiler.server;

import java.util.HashMap;
import java.util.Map;

import br.org.ttsfiler.enumerator.HTTPMethod;
import br.org.ttsfiler.util.HTTPRequestParser;
import br.org.ttsfiler.util.TTSServerProperties;

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
		if(resource.equals("/")){
			this.resource = TTSServerProperties.getDocumentRoot() + "/index.html";
		}
		else{
			String resourceSplitted[] = resource.split("/");
			if(resourceSplitted[1].equals("download")){
				this.resource = TTSServerProperties.uploadedFilesPath() + resourceSplitted[2];
			}
			else{
				this.resource = TTSServerProperties.getDocumentRoot() + resource;
			}
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
		return this.headers.get(name);
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
	
	public void writeAllKeys(){

	}
}
