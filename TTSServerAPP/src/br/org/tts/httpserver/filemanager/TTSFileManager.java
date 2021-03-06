package br.org.tts.httpserver.filemanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Environment;
import br.org.tts.httpserver.util.ArrayListUtils;
import br.org.tts.httpserver.util.TTSFileEntity;
import br.org.tts.httpserver.util.TTSServerProperties;

/**
 * <b>TTSFileManager</b>
 * </br>
 * This class is responsible for managing the user's files uploaded using the application.
 * @author Fausto Vaz
 *
 */
public class TTSFileManager {

	private FileOutputStream output;
	
	/**
	 * Get all files uploaded (files that is in) to the application directory.
	 * @return List<String>
	 */
	public List<TTSFileEntity> getUploadedFiles(){
		File uploadedFilesPath = new File(Environment.getExternalStorageDirectory() + TTSServerProperties.uploadedFilesPath());
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
		Collections.sort(list);
		return list;
	}
	
	
	public void save(List<Byte> bytes, String fileName) throws IOException{
		byte data[] = ArrayListUtils.toBytes(bytes);
		this.getFileOutputStream(fileName).write(data);
	}
	
	
	protected FileOutputStream getFileOutputStream(String fileName) throws IOException{
		if (this.output == null){
			this.output = new FileOutputStream(Environment.getExternalStorageDirectory() + TTSServerProperties.uploadedFilesPath() + fileName);
		}
		return this.output;
	}
	
	
	public void closeSavedFile() throws IOException{
		if(this.output != null){
			this.output.close();
		}
	}
	
}
