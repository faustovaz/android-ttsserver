package br.org.ttsfiler.filemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.org.ttsfiler.util.TTSServerProperties;

/**
 * <b>TTSFileManager</b>
 * </br>
 * This class is responsible for managing the user's files uploaded using the application.
 * @author Fausto Vaz
 *
 */
public class TTSFileManager {

	
	/**
	 * Get all files uploaded (files that is in) to the application directory.
	 * @return List<String>
	 */
	public List<String> getUploadedFiles(){
		File uploadedFilesPath = new File(TTSServerProperties.uploadedFilesPath());
		List<String> list = new ArrayList<String>();
		if(uploadedFilesPath.isDirectory()){
			File filesInPath[] = uploadedFilesPath.listFiles();
			for (File file : filesInPath) {
				list.add(file.getName());
			}
		}
		return list;
	}
	
}
