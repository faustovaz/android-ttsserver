package br.org.tts.httpserver.util;

import java.util.List;

public class ArrayListUtils {

	public static byte[] toBytes(List<Byte> bytes){
		byte data[] = new byte[bytes.size()];
		for (int i = 0; i < bytes.size(); i++){
			data[i] = bytes.get(i);
		}
		return data;
	}
	
}
