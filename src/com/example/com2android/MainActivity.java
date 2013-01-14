package com.example.com2android;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

	int i = 0;
	
	final int CLI = 1;
	final int HUB = 2;
	final int NC = 3;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        Button txt = (Button) findViewById(R.id.cliBtn);  
//        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/EricssonCapitalTT.ttf");  
//        txt.setTypeface(font); 
//        
//        
//        TextView txt2 = (TextView) findViewById(R.id.custom_font);  
//        Typeface font2 = Typeface.createFromAsset(getAssets(), "EricssonCapitalTT.ttf");  
//        txt.setTypeface(font2); 
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
        
    
    public void openSVIpage(View v){
    	Intent i = new Intent(Intent.ACTION_VIEW, 
    		       Uri.parse("https://svi-account.ericsson.se"));
    		startActivity(i);
    }
   
    
    public void startCliActivity(View v){
    	Toast.makeText(this, "Clicked: " + ++i + " times.", Toast.LENGTH_SHORT).show();
    	Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    	intent.putExtra("activity", CLI);
    	startActivity(intent);
    	
  	
    }
    
    public void startHubActivity(View v){
    	Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    	intent.putExtra("activity", HUB);
    	startActivity(intent); 
    	
    }
    
    public void startNCActivity(View v){
    	Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    	intent.putExtra("activity", NC);
    	startActivity(intent); 
    }
   
}
