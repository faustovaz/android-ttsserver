package br.org.ttsfiler.util;

import java.util.ResourceBundle;

public class TTSServerProperties 
{

	public static ResourceBundle resourceBundle;
	
	public static ResourceBundle getResourceBundle()
	{
		if(resourceBundle == null)
		{
			resourceBundle = ResourceBundle.getBundle("TTSServer"); 
		}
		return resourceBundle; 
	}
	
	
	public static String getDocumentRoot()
	{
		return getResourceBundle().getString("documentRoot");
	}
	
}
