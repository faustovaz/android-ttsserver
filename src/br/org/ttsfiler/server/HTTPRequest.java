package br.org.ttsfiler.server;

import java.util.HashMap;
import java.util.Map;

import br.org.ttsfiler.enumerator.HTTPMethod;
import br.org.ttsfiler.util.HTTPRequestParser;

/**
 * 
 * @author jefferson.fausto
 *
 */
public class HTTPRequest 
{
	
	private String resource;
	private HTTPMethod method;
	private Map<String, String> headers;
	private HTTPRequestParser httpRequestParser;
	
		
	/**
	 * @
	 * @return
	 */
	public String getResource()
	{
		return (resource.equals("/")) ? "index.html" : this.resource;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean isGET()
	{
		return this.method == HTTPMethod.GET;
	}
	
	
	/**
	 * 
	 * @return
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
		this.resource = resource;
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
	 * 
	 * @param property
	 * @param value
	 */
	public void addHTTPHeaderField(String field, String value){
		if(this.headers == null)
			this.headers = new HashMap<String, String>();
		this.headers.put(field, value);
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public String getHTTPHeaderFieldValue(String name){
		return this.headers.get(name);
	}
	
	
	/**
	 * 
	 * @param header
	 */
	public void addHTTPHeader(String header){
		HTTPRequestParser httpRequestParser = getHTTPRequestParser();
		httpRequestParser.parseHTTPHeader(header);
		
		if(httpRequestParser.isHTTPMethodDescriptor()){ //Load the first line of the message, example: HTTP1.1 /index.hml GET
			this.setHTTPMethod(httpRequestParser.getMethod());
			this.setResource(httpRequestParser.getResource());
		}
		else{
			this.addHTTPHeaderField(httpRequestParser.getField(), httpRequestParser.getFieldValue());
		}
	}
	
	
	protected HTTPRequestParser getHTTPRequestParser(){
		if (this.httpRequestParser == null)
			this.httpRequestParser = new HTTPRequestParser();
		return this.httpRequestParser;
	}
}
