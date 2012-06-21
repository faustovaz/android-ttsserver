package br.org.ttsfiler.server;

import java.util.HashMap;
import java.util.Map;

import br.org.ttsfiler.enumerator.HTTPMethod;

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
	
	
	/**
	 * 
	 * @param method
	 * @param requestedResource
	 */
	public HTTPRequest(HTTPMethod method, String requestedResource)
	{
		this.method = method;
		this.setResource(requestedResource);
		this.headers = new HashMap<String, String>();
	}
	
	
	/**
	 * 
	 * @param method
	 * @param requestedResource
	 */
	public HTTPRequest(String method, String requestedResource)
	{
		this.setHTTPMethodEnum(method);
		this.setResource(requestedResource);
	}

	
	/**
	 * 
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
	public void setResource(String resource)
	{
		this.resource = resource;
	}
	
	
	/**
	 * 
	 * @param method
	 */
	protected void setHTTPMethodEnum(String method)
	{
		this.method = (method.equals(HTTPMethod.GET.toString())) ? HTTPMethod.GET : HTTPMethod.POST;
	}
	
	
	/**
	 * 
	 * @param property
	 * @param value
	 */
	public void addHTTPHeaderField(String field, String value)
	{
		if(this.headers == null)
			this.headers = new HashMap<String, String>();
		this.headers.put(field, value);
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public String getHTTPHeaderField(String name)
	{
		return this.headers.get(name);
	}
}
