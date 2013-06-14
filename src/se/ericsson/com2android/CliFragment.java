package se.ericsson.com2android;


import com.example.com2android.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.EditText;

public class CliFragment extends Fragment
{	
	private String PROMPT = ">";
	EditText terminal = null;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);   
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_terminal, container, false);


		terminal = (EditText) view.findViewById(R.id.terminalView);

		terminal.setTextColor(Color.RED);

		terminal.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				if (event.getAction() == MotionEvent.ACTION_UP){
					terminal.setSelection(terminal.getText().length());
					Log.d("TERMIANL", "moving UP!");
//					return true;
				}
//
//				else{
					return false;
//				}
			}
		});


		terminal.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){	
					terminal.append("\n" + PROMPT);
					return true;
				}		
				else
					return false;
			}
		});

		return view;
	}


	public void setTextColor(Color c){
		terminal.setTextColor(Color.GREEN);
	}
}
