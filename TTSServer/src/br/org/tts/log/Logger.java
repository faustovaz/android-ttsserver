package br.org.tts.log;

public class Logger {
	
	
	public static void error(String message){
		System.out.println(message);
	}

	
	public static void error(String message, Throwable cause){
		System.out.println(message);
	}
	
	
	public static void error(String ttsMessage, String message, Throwable cause){
		System.out.println(ttsMessage);
		cause.printStackTrace();
	}
	
	
	public static void warning(){
		
	}

	
	public static void info(){
		
	}
	
	
	public static void verbose(){
		
	}
	
	
	public static void wtf(){	
		
	}
}
