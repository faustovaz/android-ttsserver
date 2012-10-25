package br.org.tts.util;

import java.util.ResourceBundle;


/**
 * <b>TSSServerMessages</b>
 * </br>
 * @author fausto
 *
 */
public class TTSServerMessages 
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
			resourceBundle = ResourceBundle.getBundle("TTSServerMessages"); 
		}
		return resourceBundle; 
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static String getMessage(final String message){
		return getResourceBundle().getString(message);
	}
	
}
