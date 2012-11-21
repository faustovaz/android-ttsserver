package br.org.tts.httpserver.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import br.org.tts.httpserver.exception.TTSException;

/**
 * <b>TTSFileServer</b>
 * </br>
 * TTSFileServer is responsible for listen some TCP/IP Port, receive connections and start HTTPRequestHandler
 * @author Fausto Vaz
 * 
 */
public class TTSServer{

	private ServerSocket server;
	private int port;
	
	
	public TTSServer() throws TTSException{
		try {
			this.server = new ServerSocket(0);
			this.port = this.server.getLocalPort();
		} 
		catch (IOException ioException) {
			throw new TTSException("TTSServerMessage.Socket_IO_EXCEPTION", ioException.getMessage(), ioException.getCause());
		}
		catch (SecurityException securityException){
			throw new TTSException("TTSServerMessage.Socket_SECURITY_ERROR", securityException.getMessage(), securityException.getCause());
		}
		catch(IllegalArgumentException illegalArgumentException){
			throw new TTSException("TTSServerMEssage.Socket_ILLEGAL_ARGUMENT", illegalArgumentException.getMessage(), illegalArgumentException.getCause());
		}
		
	}
	
	/**
	 * Starting listen to the {this.port}
	 * @throws TTSException 
	 */
	public void start() throws TTSException{
		try	{
			if(this.server.isBound()){
				this.listen();
			}
			else{
				throw new TTSException("HIIII fudeu");
			}
			
		}
		catch (IOException e){
			throw new TTSException("TTSServerMessage.Socket_IO_EXCEPTION_listening", e.getMessage(), e.getCause());
		}
	}
	
	
	/**
	 * Stop the server
	 * @throws TTSException 
	 */
	public void stop() throws TTSException{
		try	{
			this.server.close();
		} 
		catch (IOException e){
			throw new TTSException("TTSServerMessage.Socket_IO_EXCEPTION", e.getMessage(), e.getCause());
		}
	}
	
	
	/**
	 * 
	 * @throws IOException
	 */
	protected void listen() throws IOException{
		while(true){
			Socket socket = this.server.accept();
			this.startHTTPRequestHandlerJob(socket);
		}
	}

	
	/**
	 * Create a new instance of HTTPRequestHandler and start the Thread which is going to handle the HTTP Request
	 * @param socket
	 */
	protected void startHTTPRequestHandlerJob(Socket socket){
		HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(socket);
		Thread httpHandlerJob = new Thread(httpRequestHandler);
		httpHandlerJob.start(); //TODO See a better way to start thread. This kind of thread needs to start in the same time that a request arrives
	}
	
	
	protected int getPort(){
		return this.port;
	}
	
	
	protected String getIPAddress() throws TTSException{
		try{
			String ip = "";
			  for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
		            NetworkInterface intf = en.nextElement();
		            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
		                InetAddress inetAddress = enumIpAddr.nextElement();
		                if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress())
		                    ip =  inetAddress.getHostAddress();
		            }
			  }
			  return ip;
		}
		catch(SocketException socketException){
			throw new TTSException("TTSServerMessage.Socket_SOCKET_EXCEPTION", socketException.getMessage(), socketException.getCause());
		}
		
	}
	
	
	public String getHTTPAddress() throws TTSException{
		return "http://" + this.getIPAddress() + ":" + this.getPort();
	}
	
	
	

}