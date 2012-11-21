package br.org.tts.httpserver.util;

import java.util.ResourceBundle;


/**
 * <b>TSSServerProperties</b>
 * </br>
 * @author fausto
 *
 */
public class TTSServerProperties 
{

	public static ResourceBundle resourceBundle;
	
	
	public static ResourceBundle getResourceBundle()
	{
		if(resourceBundle == null){
			resourceBundle = ResourceBundle.getBundle("assets/httpserver/TTSServer"); 
		}
		return resourceBundle; 
	}
	
	
	public static String getDocumentRoot()	{
		return getResourceBundle().getString("documentRoot");
	}
	
	
	public static String uploadedFilesPath(){
		return getResourceBundle().getString("uploadedFilesPath");
	}
	
	
	public static String getMaxNumberOfBytesToReadWithoutSaving(){
		return getResourceBundle().getString("maxNumberOfBytesToReadWithoutSaving");
	}
	
	
	public static String getMaxNumberOfBytesToReadWithoudSengind(){
		return getResourceBundle().getString("maxNumberOfBytesToReadWithoutSending");
	}
	
	
	public static String getPathForMimeTypesFile(){
		return getResourceBundle().getString("mimeTypeFilePath");
	}
	
}
