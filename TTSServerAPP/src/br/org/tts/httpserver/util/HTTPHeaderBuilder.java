package br.org.tts.httpserver.util;

import java.io.File;

import br.org.tts.httpserver.filemanager.FileMap;
import br.org.tts.httpserver.resource.RequestedResource;

/**
 * <b> HTTPHeaderBuilder </b>
 * <br/>
 * Generates HTTP headers for responses.
 * @author Fausto Vaz
 *
 */
public class HTTPHeaderBuilder {

	
	public String buildHTTPHeader(RequestedResource resource){
		String header = "HTTP/1.1  " +  resource.getHTTPStatusCode() + " " + resource.getHTTPStatusDescription() +"\n";
		header = header + "Content_type: " + FileMap.getContentTypeFor(resource.getFileName()) + "\n";
		header = header + "Content_length: " + resource.getFileLength() + "\n";
		header = header + "\n";
		return header;
	}
	
	
	/**
	 * Build HTTP Response for HTTP status 200 (OK) 
	 * @param requiredResource (File)
	 * @return HTTP Header (String)
	 */
//	public String buildHTTP200Header(File requiredResource){
//		String header = "HTTP/1.1 200 OK\n";
//		header = header + "Content_type: " + FileMap.getContentTypeFor(requiredResource.getName()) + "\n";
//		header = header + "Content_length: " + requiredResource.length() + "\n";
//		header = header + "\n";
//		return header;
//	}
	
	
	
	/**
	 * Build HTTP Response for HTTP status 404 (NOT FOUND) 
	 * @param requiredResource (File)
	 * @return HTTP Header (String)
	 */
	public String buildHTTP404Header(File requiredResource){
		String header =		"HTTP/1.1 404 NOTFOUND\n";
		header = header + 	"Content-type: text/html\n";
		header = header + 	"Content-length: " + requiredResource.length() + "\n";
		header = header + 	"\n";
		return header;
	}
	
}
