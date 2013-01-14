package com.example.com2android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {	

	EditText usernameField;
	EditText hostField;
	EditText portField;

	final int CLI = 1;
	final int HUB = 2;
	final int NC = 3;
	int activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		usernameField = (EditText) findViewById(R.id.cliUsernameText);
		usernameField.setFocusable(true);

		hostField = (EditText) findViewById(R.id.cliHostText);
		portField = (EditText) findViewById(R.id.cliPortText);

		activity = getIntent().getIntExtra("activity", 0);
		if (activity == HUB){
			portField.setVisibility(8);
			usernameField.setVisibility(8);
			hostField.setWidth(500);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_cli, menu);
		return true;
	}


	public void startTerminalSession(View v){
		Intent terminal = null;

		//TODO: remove
		switch (activity){
		case 0:
			terminal = new Intent(this, TerminalActivity.class);
			Toast.makeText(this,"Something is wrong...", Toast.LENGTH_SHORT).show();
			break;
		case CLI:
			terminal = new Intent(this, TerminalActivity.class);
			hostField.setText("root@10.64.88.85");
			usernameField.setText("emiklil");
			break;
		case HUB:
			terminal = new Intent(this, TerminalActivity.class);
			hostField.setText("emiklil@147.214.14.34");
			usernameField.setText("emiklil");
			break;
		case NC:
			terminal = new Intent(this, NetconfActivity.class);
			hostField.setText("root@10.64.88.85");
			usernameField.setText("emiklil");
			break;
		default:
			terminal = new Intent(this, TerminalActivity.class);
			break;
		}


		String _userName = usernameField.getText().toString();
		String _host = hostField.getText().toString();

		int _port = Integer.parseInt(portField.getText().toString());


		terminal.putExtra("host", _host);
		terminal.putExtra("port", _port);
		terminal.putExtra("username", _userName);
		terminal.putExtra("activity", activity);

		startActivity(terminal);
		Toast.makeText(this, "hej", Toast.LENGTH_SHORT).show();
	}

}    
