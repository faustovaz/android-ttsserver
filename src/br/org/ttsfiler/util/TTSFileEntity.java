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
	public String extension;

	
	public void setName(String name){
		this.name = name;
		this.setExtension();
	}
	
	
	protected void setExtension(){
		String nameParts[] = this.name.split("\\.");
		int lastIndex = nameParts.length - 1;
		this.extension = nameParts[lastIndex];
	}
	
	public void setSize(long size){
		this.size = size;
		this.setNormalizedSize();
	}
	
	
	protected void setNormalizedSize(){
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
	
	
	public String getExtension(){
		return this.extension;
	}
}
