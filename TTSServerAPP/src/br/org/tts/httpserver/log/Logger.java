package br.org.tts.httpserver.log;

import android.util.Log;

public class Logger {
	
	public static final String TAG = "TTSSERVER";
	
	public static void error(String message){
		Log.e(TAG, message);
	}

	
	public static void error(String message, Throwable cause){
		Log.e(TAG, message, cause);
	}
	
	
	public static void error(String ttsMessage, String message, Throwable cause){
		Log.e(TAG, message, cause);
	}
	
	
	public static void warning(){
		
	}

	
	public static void info(){
		
	}
	
	
	public static void verbose(){
		
	}
	
	public static Object getLogger(){
		return null;
	}
}
