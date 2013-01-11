package com.example.com2android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class HelpClass {
	
	static AlertDialog.Builder builder;
	static AlertDialog alert;

	public HelpClass() {
		
		
		// TODO Auto-generated constructor stub
	}
	
	public void nicke(){

	}
	
static void createAlert(String alertMsg, String yesMsg, String noMsg, final Context c){
		
        builder = new AlertDialog.Builder(c);
        builder.setMessage(alertMsg);     
        builder.setCancelable(false);        
        builder.setPositiveButton(yesMsg, new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {				
				Toast.makeText(c,"Done!", 
						Toast.LENGTH_SHORT).show();				
			}
		});      
        builder.setNegativeButton(noMsg, new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {				
				dialog.cancel();
			}
		});        
        alert = builder.create();				
	}
}
