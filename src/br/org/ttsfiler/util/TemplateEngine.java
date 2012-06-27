package br.org.ttsfiler.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
}

