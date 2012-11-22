package br.org.tts.httpserver.filemanager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import br.org.tts.app.TTSServerActivity;
import br.org.tts.httpserver.log.Logger;
import br.org.tts.httpserver.util.TTSServerProperties;

public class FileMap {
	
	private static HashMap<String, String> mimeTypes;

	public static String getContentTypeFor(String filename) {
		String fileAndExtension[] = filename.split("\\.");
		String mimeType;
		if (fileAndExtension.length < 1){
			 return "application/octet-stream";
		}
		else{
			mimeType = getMimeTypesMap().get(fileAndExtension[fileAndExtension.length - 1]);
			return mimeType;
		}
	}
	
	
	protected static HashMap<String, String> getMimeTypesMap(){
		try{
			if (mimeTypes == null){
				mimeTypes = new HashMap<String, String>();
				AssetFileDescriptor assetManager = getAssetManager().openFd(TTSServerProperties.getPathForMimeTypesFile());
				FileInputStream fileInputStream = new FileInputStream(assetManager.getFileDescriptor());
				BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
				String line;
				String mimeTypeSplitted[];
				while((line = reader.readLine()) != null){
					mimeTypeSplitted = line.split("[\\s]+");
					if(mimeTypeSplitted.length == 2){
						mimeTypes.put(mimeTypeSplitted[0], mimeTypeSplitted[1]);
					}
				}				
			}			
		}
		catch(IOException ioException){
			Logger.error("ioException.MIMETYPE_FILE_NOT_FOUND");
		}
		return mimeTypes;
		
	}
	
	
	protected static AssetManager getAssetManager(){
		return TTSServerActivity.getAssetManager();
	}

}
