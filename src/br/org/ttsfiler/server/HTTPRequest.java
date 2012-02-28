package br.org.ttsfiler.server;

import br.org.ttsfiler.enumerator.HTTPMethod;

public class HTTPRequest 
{
	
	private String resource;
	private HTTPMethod method;
	private String data;
	
	
	public HTTPRequest(HTTPMethod method, String requestedResource)
	{
		this.method = method;
		this.resource = requestedResource;
		this.data = "";
	}
	
	
	public HTTPRequest(String method, String requestedResource)
	{
		this.setHTTPMethodEnum(method);
		this.resource = requestedResource;
		this.data = "";
	}


	public String getResource()
	{
		return this.resource;
	}
	
	
	public String getData()
	{
		return this.data;
	}
	
	
	public boolean isGET()
	{
		return this.method == HTTPMethod.GET;
	}
	
	
	public boolean isPOST()
	{
		return this.method == HTTPMethod.POST;
	}
	
	
	private void setHTTPMethodEnum(String method)
	{
		if(method.equals(HTTPMethod.GET.toString()))
			this.method = HTTPMethod.GET;
		else
			if(method.equals(HTTPMethod.POST.toString()))
				this.method = HTTPMethod.POST;
	}
	
}
