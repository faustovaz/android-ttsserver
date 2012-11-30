package br.org.tts.app;

import br.org.tts.httpserver.exception.TTSException;
import br.org.tts.httpserver.setup.Setup;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TTSServerActivity extends Activity{
	
	private static AssetManager manager;
	private static Context applicationContext;
	private TTSServerRunnable server;
	private Setup setup;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        manager = getAssets();
        applicationContext = getApplicationContext();
        this.setup = new Setup();
        this.setup.executeSetup();
        
        final ToggleButton button = (ToggleButton) findViewById(R.id.btnTurnOnOff);
        final TextView label = (TextView) findViewById(R.id.txtViewIPMessage);
               
        button.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				try{
					if(button.isChecked()){
						if(setup.isDeviceConnected()){
							server = new TTSServerRunnable();
							server.start();
							label.setText("Open up your browser at: \n" + server.getIP());
						}
						else{
							showMessage("Please turn on your WiFi!");
							button.setChecked(false);
						}
					}
					else{
						label.setText("");
						server.stopServer();
						server.stop();
					}
				}
				catch(TTSException e){
					showMessage(e.getTTSMessage());
				}
			}
		});
    }
   
    public void showMessage(String message){
    	AlertDialog.Builder dialog = new AlertDialog.Builder(TTSServerActivity.this);
    	dialog.setTitle("Ooops");
    	dialog.setMessage(message);
    	
    	dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
    		@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
    	
    	AlertDialog alert = dialog.create();
    	alert.show();
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
    
    
    public static ConnectivityManager getConnectivityManager(){
    	return (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }
   
}
