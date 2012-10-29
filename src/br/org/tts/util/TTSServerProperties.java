package br.org.tts.util;

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
	
	
	/**
	 * 
	 * @return
	 */
	public static ResourceBundle getResourceBundle()
	{
		if(resourceBundle == null)
		{
			resourceBundle = ResourceBundle.getBundle("TTSServer"); 
		}
		return resourceBundle; 
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static String getDocumentRoot()	{
		return getResourceBundle().getString("documentRoot");
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static String uploadedFilesPath(){
		return getResourceBundle().getString("uploadedFilesPath");
	}
	
	
	public static String getMaxNumberOfBytesToReadWithoutSaving(){
		return getResourceBundle().getString("maxNumberOfBytesToReadWithouSaving");
	}
	
	
	public static String getMaxNumberOfBytesToReadWithoudSengind(){
		return getResourceBundle().getString("maxNumberOfBytesToReadWithougSending");
	}
	
	
	
}
