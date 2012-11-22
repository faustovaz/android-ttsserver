package br.org.tts.httpserver.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;
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
	 * @param requestedResource
	 */
	public void generateRequestedResourceFromTemplate(String requestedResource){
		String resourceType = FileMap.getContentTypeFor(requestedResource);
	
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
		AssetManager m = TTSServerActivity.getAssetManager();
		try {
			
			InputStream input = m.open(templateName + ".amr");
			
			MiniTemplator templator = new MiniTemplator(input, "");
			
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
			Context context = TTSServerActivity.getContext();
			FileOutputStream inputStream = context.openFileOutput("index.html", Context.MODE_PRIVATE);
			OutputStreamWriter writer = new OutputStreamWriter(inputStream);
			//templator.generateOutput(writer);
			String s = templator.generateOutput();
			Log.v("UHET", s);
			writer.close();
			inputStream.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

