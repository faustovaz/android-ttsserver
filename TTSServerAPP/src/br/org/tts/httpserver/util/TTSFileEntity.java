package br.org.tts.httpserver.util;

import java.io.IOException;

import br.org.tts.app.TTSServerActivity;

/**
 * 
 * @author fausto
 *
 */
public class TTSFileEntity implements Comparable<TTSFileEntity>{
	
	public String name;
	public long size;
	public String extension;
	public String normalizedSize;
	public String normalizedFileName;
	public String fileTypeImageName;

	
	public void setName(String name){
		this.name = name;
		this.setExtension();
		this.setNormalizedFileName();
	}
	
	
	protected void setExtension(){
		String nameParts[] = this.name.split("\\.");
		if(nameParts.length > 0){
			int lastIndex = nameParts.length - 1;
			this.extension = nameParts[lastIndex];	
		}
		else{
			this.extension = "unknown";
		}
	}
	
	protected void setNormalizedFileName(){
		if(this.name.length() > 60){
			this.normalizedFileName = this.name.substring(0,60) + "...";
		}
		else{
			this.normalizedFileName = this.name;
		}
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
	
	
	public String getNormalizedFileName(){
		return this.normalizedFileName;
	}
	
	public String getFileTypeImageName() throws IOException{
		String images[] = TTSServerActivity.getAssetManager().list("httpserver/webappfiles/img");
		for (String imageName : images) {
			String[] fileName = imageName.split("\\.");
			if (this.getExtension().equals(fileName[0])){
				return this.getExtension();
			}
		}
		return "unknown";
	}


	@Override
	public int compareTo(TTSFileEntity o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}
}
