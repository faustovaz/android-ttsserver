package br.org.ttsfiler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b> HTTPRequestHeader </b> 
 * <br/>
 * Class used to parse HTTP Headers
 * @author Fausto Vaz
 *
 */
public class HTTPRequestParser {

	
	/**
	 *	Regex used for parsing and retrieve the values of the first line of HTTP Header.
	 *	Example of messages: 	GET /index.html HTTP1.1
	 *							POS /home/site/example.html HTTP1.1 
	 */
	public static final String REGEX_METHOD_AND_RESOURCE_DESCRIPTOR = "(GET|POST)\\s*([\\w./?-]+)\\s*.*";
	
	/**
	 * 	Regex used for parsing and retrieve the values of HTTP headers.
	 * 	Example of messages:	Host: http://mysite.com:8081
	 * 							Cache-Control: no-cache
	 */
	public static final String REGEX_HTTP_FIELD_AND_VALUE = "([\\w.-]+):\\s*([\\w.=;\",*/)(+:\\s-]+)";
	
	/**
	 * 	Regex used for validating File Boundary (those chars sent when you upload a file using HTTP)
	 * 	Example of messages:	--------------------2334562452452
	 * 							--------------------1234567891234
	 */
	public static final String REGEX_HTTP_UPLOADED_FILE_BOUNDARY = "-+[\\w]+";
	
	/**
	 * 
	 */
	public static final String REGEX_CONTENT_DISPOSITION_FILE_NAME = "filename=\"([\\w\\s&.-]+)\"";
	
	
	private String httpMethod;
	private String resource;
	private String field;
	private String fieldValue;
	private Boolean isMethodDescriptor = false;
	private Pattern methodResourcePattern;
	private Pattern httpFieldValuePattern;
	private Pattern uploadedFileBoundaryPattern;
	
	
	
	public HTTPRequestParser(){
		this.methodResourcePattern = Pattern.compile(REGEX_METHOD_AND_RESOURCE_DESCRIPTOR);
		this.httpFieldValuePattern = Pattern.compile(REGEX_HTTP_FIELD_AND_VALUE);
		this.uploadedFileBoundaryPattern = Pattern.compile(REGEX_HTTP_UPLOADED_FILE_BOUNDARY);
	}
	
	
	
	/**
	 * Parse and load to HTTPRequestParser properties HTTP headers fields, values, method and requested resource 
	 * @param header (String)
	 */
	public void parseHTTPHeader(String header){
		Matcher matcher = this.methodResourcePattern.matcher(header);
		if(matcher.matches()){
			this.isMethodDescriptor = true;
			this.httpMethod = matcher.group(1);
			this.resource = matcher.group(2);
		}
		else{
			matcher = this.httpFieldValuePattern.matcher(header);
			if(matcher.matches()){
				this.isMethodDescriptor = false;
				this.field = matcher.group(1);
				this.fieldValue = matcher.group(2);
			}
			else{
				matcher = this.uploadedFileBoundaryPattern.matcher(header);
				if (matcher.matches()){
					this.isMethodDescriptor = false;
					this.field = "File-Boundary";
					this.fieldValue = header;
				}
			}
		}
	}
	
	
	/**
	 * Return True if the HTTP Header line describes the HTTP Method and requested resource (Ex.: GET /index.html HTTP1.1) <br/>
	 * of False if not. If False the line read represents a Field and a Field Value sent by the client (HTTP).
	 * @return isHTTPMethodDescriptor (Boolean)
	 */
	public Boolean isHTTPMethodDescriptor(){
		return this.isMethodDescriptor;
	}
	
	
	/**
	 * Return the HTTPMethod used for the request (GET or POST)
	 * @return
	 */
	public String getHTTPMethod(){
		return this.httpMethod;
	}
	
	
	/**
	 * Return the name of the requested resource.
	 * @return resource (String)
	 */
	public String getResource(){
		return this.resource;
	}
	
	
	/**
	 * Return the HTTP Header Field name.
	 * @return field (String)
	 */
	public String getField(){
		return this.field;
	}
	
	
	/**
	 * Return the HTTP Header Field Value.
	 * @return field value (String)
	 */
	public String getFieldValue(){
		return this.fieldValue;
	}
	
}
