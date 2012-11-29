package br.org.tts.httpserver.setup;

import java.io.File;

import br.org.tts.httpserver.util.TTSServerProperties;

import android.os.Environment;

/**
 * 
 * @author fausto
 *
 */
public class Setup {

	public void executeSetup(){
		if (!this.filesDirectoryExists()){
			this.createFilesDirectory();
		}
	}
	
	
	protected Boolean filesDirectoryExists(){
		File directory = new File(Environment.getExternalStorageDirectory() + TTSServerProperties.uploadedFilesPath());
		return directory.exists();
	}
	
	
	protected void createFilesDirectory(){
		File dir = new File(Environment.getExternalStorageDirectory() + TTSServerProperties.uploadedFilesPath());
		dir.mkdir();
	}
	
	
}
