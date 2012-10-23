package br.org.ttsfiler.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * <b>TTSFileServer</b>
 * </br>
 * TTSFileServer is responsible for listen some TCP/IP Port, receive connections and start HTTPRequestHandler
 * @author Fausto Vaz
 * 
 */
public class TTSFilerServer{

	private ServerSocket server;
	private int port;
	
	
	public TTSFilerServer(){
		try {
			this.server = new ServerSocket(0);
			this.port = this.server.getLocalPort();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Starting listen to the {this.port}
	 */
	public void start(){
		try	{
			this.listen();
		}
		catch (IOException e){
			e.printStackTrace(); //TODO Handle the exception
		}
	}
	
	
	/**
	 * Stop the server
	 * @throws IOException
	 */
	public void stop() throws IOException	{
		try	{
			this.server.close();
		} 
		catch (IOException e){
			e.printStackTrace(); //TODO Handle the exception
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
		httpHandlerJob.start();
	}
	
	
	public int getPort(){
		return this.port;
	}
	
	
	public String getIPAddress() throws Exception{
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
			throw new Exception(socketException.getCause());
		}

	}
	

}