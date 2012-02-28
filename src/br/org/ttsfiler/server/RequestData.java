package br.org.ttsfiler.server;

import br.org.ttsfiler.enumerator.HTTPMethod;

public class RequestData 
{

	private HTTPMethod method;
	private String requestedResource;
	
	public RequestData(HTTPMethod method, String requestedResource)
	{
		this.method = method;
		this.requestedResource = requestedResource;
	}
	
	public RequestData(String method, String requestedResource)
	{
		if(method.equals(HTTPMethod.GET.toString()))
			this.method = HTTPMethod.GET;
		else
			if(method.equals(HTTPMethod.POST.toString()))
				this.method = HTTPMethod.POST;
		
		this.requestedResource = requestedResource;
		
	}


	public String getRequestedResource()
	{
		return this.requestedResource;
	}
	
	public boolean isGET()
	{
		return this.method == HTTPMethod.GET;
	}
	
	public boolean isPOST()
	{
		return this.method == HTTPMethod.POST;
	}
	
	
	
}
