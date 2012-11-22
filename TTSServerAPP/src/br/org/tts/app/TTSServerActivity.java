package br.org.tts.app;

import br.org.tts.httpserver.exception.TTSException;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TTSServerActivity extends Activity {
	
	private static AssetManager manager;
	private static Context applicationContext;
	private TTSServerRunnable thread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getAssets();
        applicationContext = getApplicationContext();
        
        final ToggleButton button = (ToggleButton) findViewById(R.id.toggleButton1);
        final TextView label = (TextView) findViewById(R.id.textView2);
        

        
        button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(button.isChecked()){
					try {
						thread = new TTSServerRunnable();
						thread.start();
						label.setText(thread.getIP());
						
					} catch (TTSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else{
					label.setText("nao clicado");
					try {
						thread.stopServer();
						thread.stop();
					} catch (TTSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
    }
//    ToggleButton toggle = (ToggleButton) findViewById(R.id.togglebutton);
//    toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            if (isChecked) {
//                // The toggle is enabled
//            } else {
//                // The toggle is disabled
//            }
//        }
//    });
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
