package com.example.draganddrop;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.com2android.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.LinearLayout;
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
	
	TextView mo1TV;
	TextView mo2TV;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		v = new OurView(this);
		//		v.setOnTouchListener(this);
		//		ball = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

		setContentView(R.layout.activity_drag);

		ov  = new OurView(this);

		LinearLayout ll = (LinearLayout) findViewById(R.id.view1);

		ll.addView(ov);
		//		ov = (OurView) findViewById(R.id.ourView1);

		blob = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		//		v.resume();
		
		mo1TV = (TextView) findViewById(R.id.moTextField1);
		mo2TV = (TextView) findViewById(R.id.moTextField2);

		ov.setOnLongpressListener(new OurView.OnLongpressListener() {

			public void onLongpress(final OurView view, final int xCord, final int yCord) {
				runOnUiThread(new Runnable() {
					public void run() {

						if (ov.longpressTimerIsActive){
							Toast.makeText(getApplicationContext(), "LONG PRESSED at: x:" + xCord + " y:" + yCord , Toast.LENGTH_SHORT).show();
							//						showPopupMenu(ov, xCord, yCord);
							registerForContextMenu(ov);
							openContextMenu(ov);
							ov.longpressTimerIsActive = false;

						}


					}
				});

			}
		});

	}

	//	private void showPopupMenu(View v, int x, int y){
	//		   PopupMenu popupMenu = new PopupMenu(this, v);
	//		      popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());
	//		    
	//		      popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
	//		   
	//		@Override
	//		public boolean onMenuItemClick(MenuItem item) {
	//			// TODO Auto-generated method stub
	//		    Toast.makeText(MainActivity.this,
	//				      item.toString(),
	//				      Toast.LENGTH_LONG).show();
	//			return false;
	//		}
	//		  });
	//		    
	//		      popupMenu.show();
	//		  }
	//	

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
		Random rand = new Random();
		int whereX = rand.nextInt(500-1)+1;
		int whereY = rand.nextInt(500-1)+1;
		ov.addSprite(new Sprite(ov, blob, whereX,whereY,"name=1"));		
	}
	
	
	
	public synchronized void connectMo(View v){
		float x1 = 1;
		float y1 = 1;
		float x2 = 300;
		float y2 = 100;
		
		mo1TV.setText("added");
		
		
		
		
		if (mo1TV.getText() != "" && mo2TV.getText() != ""){
			ov.drawLine(mo1TV.getText().toString(), mo2TV.getText().toString());
//			ov.drawLine( x1, y1, x2, y2);
		}

	}
	
	
	public synchronized void addNewMoChild(View v){
		//		Sprite s = new Sprite(ov, blob, 100);
		Random rand = new Random();
		int whereX = rand.nextInt(500-1)+1;
		int whereY = rand.nextInt(500-1)+1;
		ov.addSprite(new Sprite(ov, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_blue), whereX,whereY,"name=1",true));		
	}

	//
	//	@Override
	//	public boolean onTouch(View v, MotionEvent me) {
	//		// TODO Auto-generated method stub
	//
	//
	//		try {
	//			Thread.sleep(50);
	//		} catch (InterruptedException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//
	//		switch(me.getAction()){
	//		case MotionEvent.ACTION_DOWN:
	//			x = me.getX();
	//			y = me.getY();
	//			break;
	//		case MotionEvent.ACTION_UP:
	//			break;
	//		case MotionEvent.ACTION_MOVE:
	//			x = me.getX();
	//			y = me.getY();
	//			break;
	//		}
	//
	//
	//		return true;
	//	}

}
