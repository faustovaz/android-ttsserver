package br.org.ttsfiler.filemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.org.ttsfiler.util.TTSFileEntity;
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
	public List<TTSFileEntity> getUploadedFiles(){
		File uploadedFilesPath = new File(TTSServerProperties.uploadedFilesPath());
		List<TTSFileEntity> list = new ArrayList<TTSFileEntity>();
		if(uploadedFilesPath.isDirectory()){
			File filesInPath[] = uploadedFilesPath.listFiles();
			for (File file : filesInPath) {
				TTSFileEntity ttsFileEntity = new TTSFileEntity();
				ttsFileEntity.setName(file.getName());
				ttsFileEntity.setSize(file.length());
				list.add(ttsFileEntity);
			}
		}
		return list;
	}
	
}
