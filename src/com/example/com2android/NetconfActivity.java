package com.example.com2android;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class NetconfActivity extends Activity {	

	final String TAG = "NC-Terminal";
	final String TAB = Character.toString ((char) 9);
	final int TERMINAL = 1;
	final int COMMANDS = 2;
	final int HISTORY = 3;

	private String _host;
	private String _username;
	private int _port;
	private int _activity;
	private String _passwd;
	private boolean appendNextLine = true;
	private boolean connectedToCli = false;
	private boolean updateCommandList = false;
	private boolean isRunning = false;
	private boolean isStarted = false;
	private Session session;
	Channel channel;
	String lastString = "";

	TextView terminal;
	EditText input;

	ListView commandsView;
	ListView historyView;
	ArrayAdapter<String> commandsList;
	ArrayAdapter<String> historyList;


	HistoryHandler historyHandler;

	final NetconfMessages items[] = new NetconfMessages[3];

	/*
	 * TAB-funktionen
	 * HUB-bildens f�rg
	 * Why dubblett i utmatningen?
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		_host = intent.getStringExtra("host");
		_username = intent.getStringExtra("username");
		_port = intent.getIntExtra("port", 22);
		_activity = intent.getIntExtra("activity", 0);

		setContentView(R.layout.activity_terminal_nc);
		prepareList();

		isStarted = true;

		historyHandler = new HistoryHandler();
		terminal = (TextView) findViewById(R.id.terminalView);
		terminal.setFocusable(true);
		terminal.setMovementMethod(new ScrollingMovementMethod());

		input = (EditText) findViewById(R.id.inputText);
		input.setOnKeyListener(sendCommand);
		input.setFocusable(true);
		input.setFocusableInTouchMode(true);

		Log.d("THIS", "host: " + _host + " _username: " + _username + " port: " + _port  );

		if (!isRunning && isStarted){
			setPassword("Enter password");
			isRunning = true;
		}
	}


	private void prepareList(){

		Spinner spinner = (Spinner) findViewById(R.id.ncCommands);
		//		s.setPrompt("hej hej");
		//Prepar adapter 
		//HERE YOU CAN ADD ITEMS WHICH COMES FROM SERVER.

		items[0] = new NetconfMessages("Select one", "");
		items[1] = new NetconfMessages("Hello", NetconfMessages.NC_HELLO);
		items[2] = new NetconfMessages("Get-Config", NetconfMessages.NC_GETCONFIG);


		ArrayAdapter<NetconfMessages> adapter = new ArrayAdapter<NetconfMessages>(this, android.R.layout.simple_spinner_item, items);
		ArrayAdapter<NetconfMessages> adapter2 = new ArrayAdapter<NetconfMessages>(this, android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.sendMsgFilter);
        textView.setAdapter(adapter2);
        textView.setOnItemSelectedListener(sendMessage2);
        
        spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(sendMessage);



	}
	
	private OnItemSelectedListener sendMessage2 = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			NetconfMessages d = items[arg2];
			input.setText(d.getValue());
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};


	private OnItemSelectedListener sendMessage = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {

			if(position != 0){
				NetconfMessages d = items[position];

				//Get selected value of key 
				String value = d.getValue();
				String key = d.getSpinnerText();

				sendCmd(d.getValue());
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			input.setText("ETst");
		}
	}; 

	private OnItemClickListener getCommandString = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			String selectedItem = (String) commandsView.getItemAtPosition(arg2);
			input.setText(selectedItem + " ");
			input.setSelection(input.getText().length());
		}
	};

	int count = 1;
	private OnKeyListener sendCommand = new OnKeyListener() {
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_TAB:
					//					commandsList.clear();
					Log.d("TAB", "TAB clicked:");

					//input.setText("tab clicked: "+ count++ + " times" );
					String cmd = input.getText().toString() + TAB;
					//					printStringAsAscii(cmd);

					//					Log.d("TAB", "TAB string: " + cmd);
					//					commandsList.clear();

					//					if (connectedToCli){
					//						excecuteCmd("" + Character.toString ((char) 9));
					//					}
					//					else
					//					excecuteCmd("commands");
					//					sendCmd("" + Character.toString ((char) 9));
					sendCmd(TAB);
					//TODO: Find string from TAB, add to commandlist

					return true;
				case KeyEvent.KEYCODE_ENTER:
					isRunning = true;
					String s = input.getText().toString();
					if (!s.equals("")){
						sendCmd(s);
						//						if (appendNextLine)
						//							historyHandler.addStringToHistory(s);
						input.setText("");
					}
					return true;

				}
			}
			return false;
		}
	};


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		isStarted = false;
		if (isRunning){
			this.finish();
			android.os.Process.killProcess(android.os.Process.myPid()) ;
		}
		//		historyHandler.save(100);		
		//		this.finish();
		//		
	}



	private void printStringAsAscii(String str){
		for ( int i = 0; i < str.length(); ++i ) {
			char c = str.charAt( i );
			int j = (int) c;
			System.out.println(c + ": " + j);
			Log.d("TAG", "meddelande");
		}
	}

	
	public void updateTerminal(final String inputString, final int whereToUpdate){

		Handler refresh = new Handler(Looper.getMainLooper());
		refresh.post(new Runnable() {
			public void run()
			{
				switch(whereToUpdate){
				case TERMINAL:
					//					Log.d("OUTPUT2", "LastString: " + lastString + "inputString: " + inputString);
					if (appendNextLine && lastString != inputString){
						terminal.append(inputString + "\n");	
						lastString = inputString;
					}
					appendNextLine = true;
					input.setTextColor(Color.GREEN);
					input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);

					if (inputString.indexOf("Password:") > 0){
						appendNextLine = false;

						input.setTextColor(Color.BLACK);
						input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
						isRunning = false;
					}
					//					isRunning = true;

					if (inputString.indexOf("IF YOU ARE NOT AN AUTHORIZED USER, PLEASE EXIT IMMEDIATELY") > 0){
						System.out.println("Connected to CLI...");
						connectedToCli = true;
					}
					final int scrollAmount = terminal.getLayout().getLineTop(terminal.getLineCount())
							-terminal.getHeight();
					// if there is no need to scroll, scrollAmount will be <=0
					if(scrollAmount>0)
						terminal.scrollTo(0, scrollAmount);
					else
						terminal.scrollTo(0,0);

					break;
				default:
					break;
				}

			}
		});
	}

	public void shellThread(){

		new Thread (new Runnable() {

			@Override
			public void run() {
				String host="svi.se.ericsson.net";
				String user="emiklil";
				//			        String command1="ssh root@147.214.14.190";
				try{

					java.util.Properties config = new java.util.Properties();
					config.put("StrictHostKeyChecking", "no");
					JSch jsch = new JSch();
					session=jsch.getSession(user, host, 22);
					session.setPassword(_passwd);
					session.setConfig(config);
					session.connect();

					updateTerminal("\nConnected\n",TERMINAL);

					channel=session.openChannel("shell");
					channel.setInputStream(null);

					//excecuteCmd("commands");

					InputStream in=channel.getInputStream();
					channel.connect();
					byte[] tmp=new byte[1024];


					while(true){
						while(in.available()>0){
							int i=in.read(tmp, 0, 1024);
							if(i<0)
								break;
							String s = new String(tmp, 0, i);	
							if (s.contains("\b")){
								s = s.substring(s.indexOf("\b")+1);
							}

							if (updateCommandList){

								String[] temp;

								temp = s.split(Character.toString ((char) 13));
								for(int xyt =0; i < temp.length ; xyt++){
									System.out.print(temp[xyt]);
									updateTerminal(temp[xyt],COMMANDS);
								}

								printStringAsAscii(s);


								updateCommandList = false;
							}
							else{
								//TODO: Uppdatera commands?
								updateTerminal(s,TERMINAL);		

							}

							if (!connectedToCli){

								String command = "";
								switch(_activity){
								case 0:
									//									Toast.makeText(getApplicationContext(), "something is wrong..", Toast.LENGTH_SHORT).show();
									Log.d(TAG, "Activity error...");
									break;
									//cli
								case 1:
									command = "ssh " + _host + " -p " + _port + " -t -s cli";
									break;
									//hub
								case 2:
									command = "ssh " + _host;
									break;
									//Netconf
								case 3:
									command = "ssh " + _host + " -p " + _port + " -t -s netconf";
									break;

								}

								//sendCmd("ssh root@10.64.88.75 -p 22 -t -s cli");	
								sendCmd(command);


								connectedToCli = true;
							}




							//							excecuteCmd(TAB);
							//							sendCmd(TAB);

						}

						if(channel.isClosed()){

							System.out.println("exit-status: "+channel.getExitStatus());
							break;
						}
						try{
							Thread.sleep(1000);

						}catch(Exception ee){}
					}
				}catch(Exception e){
					e.printStackTrace();
				}	
			}
		}).start();
	}



	public void sendCmd(final String command){
		Log.d("cmd", "sending string");
		OutputStream toServer;
		try {
			toServer = channel.getOutputStream();
			toServer.write((command + "\r").getBytes());
			//			toServer.write((command + "\r\n").getBytes());
			toServer.flush();


			if (command.equalsIgnoreCase(TAB)){

				Log.d(TAG, "Got TAB!: command: " + command);
				//				appendNextLine = false;
				updateCommandList = true;
				updateTerminal(command, TERMINAL);
			}

			else{
				Log.d(TAG, "Updating terminal!");
				updateTerminal("> ", TERMINAL);
				if (appendNextLine)
					updateTerminal(command, HISTORY);
				//				input.setText("");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}


	//	public void excecuteCmd(String cmd){
	//
	//		new ArrayList<String>();
	//		try {
	//			Channel channel = session.openChannel("exec"); 
	//
	//			((ChannelExec)channel).setCommand(cmd);
	//			channel.setInputStream(null);
	//			((ChannelExec)channel).setErrStream(System.err);
	//
	//			InputStream in=channel.getInputStream();
	//			channel.connect();
	//			byte[] tmp=new byte[1024];
	//			while(true){
	//				while(in.available()>0){
	//					int i=in.read(tmp, 0, 1024);
	//					if(i<0)
	//						break;
	//
	//					String str = new String(tmp, 0, i);
	//					Log.d("terminal", "GOT String: " + str );
	//
	//					String[] temp = str.split("\n");
	//					commandsList.clear();
	//					for (int x = 0; x < temp.length; x++){
	//						updateTerminal(temp[x], COMMANDS);
	//					}
	//				}
	//				if(channel.isClosed()){
	//					break;
	//				}
	//				try{Thread.sleep(1000);}catch(Exception ee){}
	//			}
	//			channel.disconnect();
	//			//			session.disconnect();
	//		} catch (JSchException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//	}


	private void setPassword(String msgIn){

		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this, R.style.myBackgroundStyle);


		helpBuilder.setTitle(null);

		helpBuilder.setMessage(msgIn);


		final EditText input = new EditText(this);
		input.setSingleLine();

		input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
		input.setTextColor(Color.GREEN);
		input.setGravity(Gravity.CENTER);

		input.setWidth(75);
		input.setMinWidth(50);

		input.setMaxWidth(100);

		input.setHeight(40);

		input.setBackgroundResource(R.drawable.roundedcorners);

		helpBuilder.setView(input);
		helpBuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				_passwd = input.getText().toString();

				shellThread();
			}
		});


		AlertDialog helpDialog = helpBuilder.create();
		helpDialog.setCanceledOnTouchOutside(false);
		helpDialog.setCancelable(false);
		helpDialog.show();
	} 



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_cli, menu);
		return true;
	}


	public void startCliSession(View v){
		Toast.makeText(this, "hej", Toast.LENGTH_SHORT).show();
	}


} 



