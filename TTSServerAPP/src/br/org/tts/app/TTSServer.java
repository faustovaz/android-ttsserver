package br.org.tts.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ToggleButton;

public class TTSServer extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final ToggleButton button = (ToggleButton) findViewById(R.id.toggleButton1);
        button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(button.isChecked()){
					Log.v("UHET", "pressed fdp");
				}
				else{
					Log.v("UHET", "not pressed fdp");
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
}
