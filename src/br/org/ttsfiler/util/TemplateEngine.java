package br.org.ttsfiler.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import br.org.ttsfiler.filemanager.TTSFileManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author Fausto Vaz
 *
 */
public class TemplateEngine {

	private MimetypesFileTypeMap mimeTypes;
	private Configuration templateConfiguration;
	
	/**
	 * 
	 */
	public TemplateEngine(){
		this.mimeTypes = new MimetypesFileTypeMap();
		this.templateConfiguration = new Configuration();
	}
	
	
	
	/**
	 * 
	 * @param requestedResource
	 */
	public void generateRequestedResourceFromTemplate(String requestedResource){
		String resourceType = this.mimeTypes.getContentType(requestedResource);
	
		if(resourceType.equals("text/html")){
			this.generateHTMLFileFromTemplate(requestedResource);
		}
	}
	
	
	
	/**
	 * 
	 * @param requestedResource
	 */
	protected void generateHTMLFileFromTemplate(String requestedResource){
		String templateName = requestedResource + ".ftl";
		try {
			Template template = this.templateConfiguration.getTemplate(templateName);
			TTSFileManager fileManager = new TTSFileManager();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("files", fileManager.getUploadedFiles());
			File f = new File(requestedResource);
			Writer out = new PrintWriter(f);
			try {
				template.process(map, out);
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Writer generateHTMLFileFromTemplate_2(String requestedResource){
		String templateName = TTSServerProperties.getDocumentRoot() + requestedResource + ".ftl";
		try {
			Template template = this.templateConfiguration.getTemplate(templateName);
			TTSFileManager fileManager = new TTSFileManager();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("files", fileManager.getUploadedFiles());
			Writer out = new StringWriter();
			
		//	File f = new File(requestedResource);
		//	Writer out = new PrintWriter(f);
			try {
				template.process(map, out);
				return out;
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return out;
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		TemplateEngine e = new TemplateEngine();
		Writer o = e.generateHTMLFileFromTemplate_2("/index.html");
		BufferedInputStream d = new BufferedInputStream(new BufferedOut)
		
		Reader r = new StringReader(o.toString());
		StringReader reader = new StringReader(o.toString()) ;
		
		
		
		
		DataInputStream d = new DataInputStream(reader);
				
		System.out.println(o);
		
	}
	

}

