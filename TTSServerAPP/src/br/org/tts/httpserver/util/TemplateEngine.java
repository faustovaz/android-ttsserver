package br.org.tts.httpserver.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import br.org.tts.app.TTSServerActivity;
import br.org.tts.httpserver.filemanager.FileMap;
import br.org.tts.httpserver.filemanager.TTSFileManager;
import br.org.tts.templator.MiniTemplator;

/**
 * 
 * @author Fausto Vaz
 *
 */
public class TemplateEngine {

	
	/**
	 * 
	 */
	public TemplateEngine(){
	}
	
	
	
	/**
	 * 
	 * @param requestedResource
	 */
	public String generateRequestedResourceFromTemplate(String requestedResource){
		String resourceType = FileMap.getContentTypeFor(requestedResource);
	
		if(resourceType.equals("text/html") && (requestedResource.equals(TTSServerProperties.getDocumentRoot() + "/index.html"))){
			return this.generateHTMLFileFromTemplate(requestedResource);
		}
		return null;
	}
	
	
	
	/**
	 * 
	 * @param requestedResource
	 */
	protected String generateHTMLFileFromTemplate(String requestedResource){
		String templateName = requestedResource + ".ftl";
		AssetManager m = TTSServerActivity.getAssetManager();
		try {
			AssetFileDescriptor assetFileDescriptor = m.openFd(templateName + ".amr");
			FileInputStream f = new FileInputStream(assetFileDescriptor.getFileDescriptor());
			MiniTemplator templator = new MiniTemplator(f, "");
			
			TTSFileManager t = new TTSFileManager();
			List<TTSFileEntity> files = t.getUploadedFiles();
			for (TTSFileEntity ttsFileEntity : files) {
				
				templator.addBlock("Block");
				templator.setVariable("fileTypeImageName", ttsFileEntity.getFileTypeImageName());
				templator.setVariable("normalizedFileName", ttsFileEntity.getNormalizedFileName());
				templator.setVariable("normalizedFileSize", ttsFileEntity.normalizedSize);
				templator.setVariable("name", ttsFileEntity.getName());
				templator.addBlock("Block");
				
			}
			return templator.generateOutput();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
}

