package br.org.tts.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import br.org.tts.filemanager.TTSFileManager;
import br.org.tts.log.Logger;
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
	
		if(resourceType.equals("text/html") && (requestedResource.equals(TTSServerProperties.getDocumentRoot() + "/index.html"))){
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
			TTSFileManager fileManager = new TTSFileManager();
			Template template = this.templateConfiguration.getTemplate(templateName);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("files", fileManager.getUploadedFiles());
			File f = new File(requestedResource);
			Writer out = new PrintWriter(f);
			template.process(map, out);
		} 
		catch (TemplateException templateException){
			Logger.error("TTSServerMessage.Template_TEMPLATE_EXCEPTION", templateException.getMessage(), templateException.getCause());
		}
		catch (IOException ioException) {
			Logger.error("TTSServerMessage.File_IO_EXCEPTION", ioException.getMessage(), ioException.getCause());
		}

	}
}

