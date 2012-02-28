package br.org.ttsfiler.exception;

public class TTSFilerException extends Exception
{

	private static final long serialVersionUID = 1L;
	
	
	public TTSFilerException(String message)
	{
		super(message);
	}
	
	public TTSFilerException(String message, Throwable cause)
	{
		super(message,cause);
	}

}
