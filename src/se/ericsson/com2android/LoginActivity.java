package se.ericsson.com2android;

import com.example.com2android.R;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends FragmentActivity {

	FragmentTransaction transaction;
	static public ViewPager viewPager;
	
	TextView TVusername, TVpassword, TVip;
	CheckBox TVcli, TVnc, TVterminal;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.fragment_main);
		setContentView(R.layout.login);
		getActionBar().hide();
		
		setFonts();
		fillFormat();

	}
	
	private void fillFormat(){
		EditText ETusername = (EditText) findViewById(R.id.username_input_login);
		EditText ETip = (EditText) findViewById(R.id.ip_input_login);
		
		ETusername.setText("emiklil");
		ETip.setText("10.64.87.150");
	}
	
	private void setFonts(){
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/EricssonCapitalTT.ttf");
		Typeface tf2 = Typeface.createFromAsset(getAssets(),"fonts/Hashtag.ttf");


		TVusername = (TextView) findViewById(R.id.username_text_login);
		TVpassword = (TextView) findViewById(R.id.password_text_login);
		TVip = (TextView) findViewById(R.id.ip_text_login);
		TVcli = (CheckBox) findViewById(R.id.cli_checkbox);
		TVnc = (CheckBox) findViewById(R.id.nc_checkbox);
		TVterminal = (CheckBox) findViewById(R.id.terminal_checkbox);
		
		Button b1 = (Button) findViewById(R.id.loginBtn);
		b1.setTypeface(tf);
		
		TVusername.setTextSize(20);
		TVpassword.setTextSize(20);
		TVip.setTextSize(20);
		TVcli.setTextSize(40);
		TVnc.setTextSize(40);
		TVterminal.setTextSize(40);
		

		TVusername.setTypeface(tf);
		TVpassword.setTypeface(tf);
		TVip.setTypeface(tf);
		TVcli.setTypeface(tf2);
		TVnc.setTypeface(tf2);
		TVterminal.setTypeface(tf2);
	}
	
	
	

	public void startNavActivity(View v){
		Intent main = new Intent(this, MainNavigationActivity.class);
		
		main.putExtra("cli_checkbox", TVcli.isChecked());
		main.putExtra("nc_checkbox", TVnc.isChecked());
		main.putExtra("terminal_checkbox", TVterminal.isChecked());
		
		startActivity(main);
	}

}



