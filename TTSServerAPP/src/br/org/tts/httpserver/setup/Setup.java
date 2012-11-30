package br.org.tts.httpserver.setup;

import java.io.File;

import br.org.tts.app.TTSServerActivity;
import br.org.tts.httpserver.util.TTSServerProperties;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
	
	public Boolean isDeviceConnected(){
		ConnectivityManager connectivityManager = TTSServerActivity.getConnectivityManager();
		NetworkInfo networks[] = connectivityManager.getAllNetworkInfo();
		for (NetworkInfo networkInfo : networks) {
			if (networkInfo.getTypeName().equalsIgnoreCase("WIFI")){
				return networkInfo.isConnected();
			}
		}
		return false;
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
