package br.org.ttsfiler.util;

/**
 * 
 * @author fausto
 *
 */
public class TTSFileEntity {
	
	public String name;
	public long size;
	public String normalizedSize;

	
	public void setName(String name){
		this.name = name;
	}
	
	
	public void setSize(long size){
		this.size = size;
		long calculatedSize = this.size / 1024;
		if ((calculatedSize) > 1000 ){
			calculatedSize = calculatedSize / 1024;
			this.normalizedSize = String.valueOf(calculatedSize) + "MB";
		}
		else{
			this.normalizedSize = String.valueOf(calculatedSize) + "KB";
		}
			
	}
	
	
	public String getName(){
		return this.name;
	}
	
	
	public long getSize(){
		return this.size;
	}
	
	
	public String getNormalizedSize(){
		return this.normalizedSize;
	}
}
