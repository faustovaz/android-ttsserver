package br.org.tts.app;

import br.org.tts.httpserver.exception.TTSException;
import br.org.tts.httpserver.setup.Setup;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TTSServerActivity extends Activity{
	
	private static AssetManager manager;
	private static Context applicationContext;
	private TTSServerRunnable server;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        manager = getAssets();
        applicationContext = getApplicationContext();
        new Setup().executeSetup();
        
        final ToggleButton button = (ToggleButton) findViewById(R.id.btnTurnOnOff);
        final TextView label = (TextView) findViewById(R.id.txtViewIPMessage);
               
        button.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				if(button.isChecked()){
					try{
						server = new TTSServerRunnable();
						server.start();
						label.setText("Open up your browser at: \n" + server.getIP());
					} 
					catch (TTSException e) {
						e.printStackTrace();
					}
				}
				else{
					try {
						label.setText("");
						server.stopServer();
						server.stop();
					} catch (TTSException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    public static AssetManager getAssetManager(){  	
    	return manager;
    }
    
    
    public static Context getContext(){
    	return applicationContext;
    }
}
