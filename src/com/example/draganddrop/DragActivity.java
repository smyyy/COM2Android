package com.example.draganddrop;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.com2android.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class DragActivity extends Activity{
	/**
	 * Called when the activity is first created.
	 */

	OurView ov;
	private List<Sprite> sprites = new ArrayList<Sprite>();
	Bitmap blob;
	
	Sprite mo1 = null;
	Sprite mo2 = null;
	Sprite clickedSprite = null;
	
	TextView mo1TV;
	TextView mo2TV;
	
	ArrayList<Sprite> appointment;
    ArrayAdapter<Sprite> aa;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_drag);

		ov  = new OurView(this);

		LinearLayout ll = (LinearLayout) findViewById(R.id.view1);

		ll.addView(ov);

		blob = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		//		v.resume();
		
		mo1TV = (TextView) findViewById(R.id.moTextField1);
		mo2TV = (TextView) findViewById(R.id.moTextField2);

		ListView listView = (ListView)findViewById(R.id.listView1);
		
		appointment = new ArrayList<Sprite>();
        aa = new ArrayAdapter<Sprite>(this, android.R.layout.simple_list_item_1,  
                appointment);
        listView.setAdapter(aa);
		
		
		ov.setOnLongpressListener(new OurView.OnLongpressListener() {

			public void onLongpress(final OurView view, final int xCord, final int yCord, final Sprite sprite) {
				runOnUiThread(new Runnable() {
					public void run() {

						if (ov.longpressTimerIsActive){
							Toast.makeText(getApplicationContext(), "LONG PRESSED at: x:" + xCord + " y:" + yCord , Toast.LENGTH_SHORT).show();

							registerForContextMenu(ov);
							openContextMenu(ov);
							ov.longpressTimerIsActive = false;
							clickedSprite = sprite;
						}


					}
				});

			}
		});

	}


	/** This will be invoked when an item in the listview is long pressed */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.popupmenu, menu);
	}

	/** This will be invoked when a menu item is selected */
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

		switch (item.getItemId()){
		case R.id.menu1:
			makeConnection();
			break;
		case R.id.menu2:
			mo1TV.setText(clickedSprite.getName());
			mo1 = clickedSprite;
			break;
		case R.id.menu3:
			mo2TV.setText(clickedSprite.getName());
			mo2 = clickedSprite;
			break;
		default:
					
		Toast.makeText(DragActivity.this,
				item.toString(),
				Toast.LENGTH_LONG).show();
		}

		return true;
	}
	
	private void makeConnection(){
		Toast.makeText(DragActivity.this, "To where?",Toast.LENGTH_LONG).show();
		
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//		v.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//		v.resume();
	}
	
	public synchronized void addNewMo(View v){
		//		Sprite s = new Sprite(ov, blob, 100);
		addMO(blob);

	}
		
	public synchronized void addNewMoChild(View v){
		addMO(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_blue));
	}
	
	
	
	
	private void addMO(final Bitmap blobben){
		Random rand = new Random();
		final int whereX = rand.nextInt(500-1)+1;
		final int whereY = rand.nextInt(500-1)+1;
		
		final EditText input = new EditText(DragActivity.this);

		new AlertDialog.Builder(DragActivity.this)
		    .setTitle("Update Status")
		    .setMessage("Name?")
		    .setView(input)
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int whichButton) {
		        	 Sprite newSprite = new Sprite(ov, blobben, whereX,whereY,input.getText().toString());
		        	 aa.add(newSprite);
		             ov.addSprite(newSprite);	           
		             // deal with the editable
		         }
		    })
		    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int whichButton) {
		                // Do nothing.
		         }
		    }).show();
	}
	
	
	
	public synchronized void connectMo(View v){
		if (mo1TV.getText() != "" && mo2TV.getText() != ""){
			ov.drawLine(mo1, mo2);
		}

	}

}
