package br.org.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.FileNameMap;
import java.util.HashMap;

import br.org.tts.log.Logger;

public class FileMap implements FileNameMap {
	
	private static HashMap<String, String> mimeTypes;

	@Override
	public String getContentTypeFor(String filename) {
		String fileAndExtension[] = filename.split("\\.");
		if (fileAndExtension.length > 1){
			String mymeType = this.getMimeTypesMap().get(fileAndExtension[fileAndExtension.length - 1]);
			if(mymeType == null){
				return "application/octet-stream";
			}
			return mymeType;
		}
		return "application/octet-stream";
	}
	
	
	protected HashMap<String, String> getMimeTypesMap(){
		try{
			if (mimeTypes == null){
				mimeTypes = new HashMap<String, String>();
				File file = new File("resources/META-INF/mime.types");
				FileInputStream fileInputStream = new FileInputStream(file);
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

}
