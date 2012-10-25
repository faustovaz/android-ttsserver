package br.org.main;

import br.org.ttsfiler.server.TTSServer;

public class Main{

	

	public static void main(String[] args) throws Exception{
		TTSServer server = new TTSServer();
		System.out.println("http://" + server.getIPAddress() + ":" + server.getPort());
		server.start();	  
	}
	 

	//TODO - Handle Exceptions
	//TODO - Log all exceptions
	//TODO - Export Library
	//TODO - Create android application
	//TODO - Implement drag n' drop files in javascript
	//TODO - Change font-family of html page
	
//	POST /index.html HTTP/1.1
//	Host: localhost:8088
//	User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:15.0) Gecko/20100101 Firefox/15.0.1
//	Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
//	Accept-Language: pt-br,pt;q=0.8,en-us;q=0.5,en;q=0.3
//	Accept-Encoding: gzip, deflate
//	Connection: keep-alive
//	Referer: http://localhost:8088/
//	Content-Length: 38584628
//	Content-Type: multipart/form-data; boundary=---------------------------265001916915724
//	Pragma: no-cache
//	Cache-Control: no-cache
//	-----------------------------265001916915724
//	Content-Disposition: form-data; name="file"; filename="tabelas_portal.sql"
//	Content-Type: application/octet-stream

	
}
