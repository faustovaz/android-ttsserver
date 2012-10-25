package br.org.ttsfiler.exception;

import br.org.ttsfiler.log.Logger;
import br.org.ttsfiler.util.TTSServerMessages;

public class TTSException extends Exception
{

	private static final long serialVersionUID = 1L;
	private String ttsMessage;
	
	public TTSException(String message)	{
		super(message);
		Logger.error(message);
	}
	
	public TTSException(String message, Throwable cause){
		super(message,cause);
		Logger.error(message, cause);
	}
	
	public TTSException(String ttsMessage, String message, Throwable cause ){
		super(message, cause);
		this.ttsMessage = ttsMessage;
		Logger.error(this.ttsMessage, message, cause);
	}
	
	public String getTTSCodeMessage(){
		return this.ttsMessage;
	}
	
	public String getTTSMessage(){
		return TTSServerMessages.getMessage(this.ttsMessage);
	}

}
