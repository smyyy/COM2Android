package com.example.draganddrop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.example.com2android.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//public class OurView extends SurfaceView{
public class OurView extends SurfaceView implements Runnable {

	Thread t = null;
	SurfaceHolder holder;
	boolean isItOK = false;
	Sprite sprite, sprite2;
	boolean spriteLoaded = false;
	private long lastClick;
	private List<Sprite> sprites = new ArrayList<Sprite>();
	private List<Line> lines = new ArrayList<Line>();
	Bitmap ball, blob;
	float x = 0, y = 0;

	final int LONG_PRESS_TIME = 1000; //1 sec
	Timer longPressTimer = new Timer();
	private OurView.OnLongpressListener longpressListener;
	protected boolean longpressTimerIsActive = false;

	Sprite coll1;
	Sprite coll2;
	Sprite lastColl1 = null;
	Sprite lastColl2 = null;

	EditText mo1TV;
	EditText mo2TV;


	public OurView(Context context) {
		super(context);
		init();
	}

	public OurView(Context context, AttributeSet attri) {
		super(context);
		// TODO Auto-generated constructor stub		
		init();
	}

	private void init(){


		isItOK = true;
		t = new Thread(this);
		t.start();

		holder = getHolder();
		blob = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

		mo1TV = (EditText) findViewById(R.id.moTextField1);
		mo2TV = (EditText) findViewById(R.id.moTextField2);

		sprite = new Sprite(this, blob, 100, 100, "name=1");
		sprites.add(sprite);
		sprite2 = new Sprite(this, blob, 200, 200, "name=2");
		sprites.add(sprite2);

		addSprite(new Sprite(this, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_blue), 300, 300, "name=3",true));
		addSprite(new Sprite(this, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_blue), 400, 400, "name=4",true));

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub


		while (isItOK == true){
			if (!holder.getSurface().isValid()){
				continue;
			}

			Canvas c = holder.lockCanvas();
			draw(c);
			holder.unlockCanvasAndPost(c);
		}
	}

	public synchronized void drawLine(Sprite mo1, Sprite mo2){

		lines.add(new Line(mo1,mo2));
	}


	public synchronized void draw(Canvas c){
		c.drawARGB(255, 100, 100, 100);


		Paint mPaint = new Paint();
		// smooths
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.GREEN);
		mPaint.setStyle(Paint.Style.FILL); 
		mPaint.setStrokeWidth(4.5f);
		// opacity
		mPaint.setAlpha(0x80); //


		Paint mPaint2 = new Paint();
		// smooths
		mPaint2.setAntiAlias(true);
		mPaint2.setColor(Color.WHITE);
		mPaint2.setStyle(Paint.Style.STROKE); 
		mPaint2.setStrokeWidth(4.5f);
		mPaint2.setPathEffect(new DashPathEffect(new float[] {20,20}, 0));
		// opacity
		mPaint2.setAlpha(0x80); //


		Rect r = new Rect(100, 100, getWidth()-100, getHeight()-100);
		Rect r2 = new Rect(50, 50, getWidth()-50, getHeight()-50);
		c.drawRect(r, mPaint);
		c.drawRect(r2, mPaint2);


		if (checkCollision()){

			if (lastColl1 != coll1 && lastColl2 != coll2){
				if (coll1.isChild() && coll2.isChild()){
					removeSprite(coll2);
				}
				else if (!coll1.isChild() && !coll2.isChild()){
					blob = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_blue);

					addSprite(new Sprite(this, blob, coll1.xCorner,coll1.yCorner,"name=1", true));
					lastColl1 = coll1;
					lastColl2 = coll2;
				}
				else{
					//do nothing right now
				}
			}
		}


		Paint p1 = new Paint();
		p1.setColor(Color.BLACK);
		p1.setStyle(Paint.Style.STROKE); 
		p1.setStrokeWidth(5);

		for (Line l : lines){
			c.drawLine(l.getMo1().getCenterX(), l.getMo1().getCenterY(), l.getMo2().getCenterX(), l.getMo2().getCenterY(), p1);
		}

		for (Sprite spr : sprites){
			spr.onDraw(c);
		}
	}



	private boolean checkCollision(){
		for (Sprite spr1 : sprites){
			for (Sprite spr : sprites){
				if (spr.isCollition(spr1.xCorner, spr1.yCorner)){
					coll1 = spr1;
					coll2 = spr;
					return true;
				}
			}
		}
		return false;
	}


	public synchronized void removeSprite(Sprite s){
		System.out.println("REMOVING SPRITE");

		spriteLoaded = true;
		sprites.remove(s);

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		invalidate();
	}



	public synchronized void addSprite(Sprite s){
		System.out.println("ADDING SPRITE");

		spriteLoaded = true;
		sprites.add(s);

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		invalidate();
	}


	public interface OnLongpressListener {
		public void onLongpress(OurView view, int xCord, int yCord, Sprite sprite);
	}	




	public void setOnLongpressListener(OurView.OnLongpressListener listener) {
		longpressListener = listener;
	}



	private Sprite getClickedSprite(float x, float y){
		for (Sprite spr : sprites){
			if (spr.isCollition(x, y)){
				return spr;
			}
		}
		return null;
	}


	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			if (System.currentTimeMillis() - lastClick > 500) {
				lastClick = System.currentTimeMillis();
				longpressTimerIsActive = true;



				longPressTimer.schedule(new TimerTask(){
					@Override
					public void run() {
						longpressListener.onLongpress(OurView.this, (int) event.getY(), (int) event.getY(),null);
					}

				}, LONG_PRESS_TIME);
				break;

			}

			else{//dubbelklick
				lastClick = System.currentTimeMillis();
				synchronized (getHolder()) {

					Sprite s = getClickedSprite(event.getX(), event.getY());

					if (s != null){
						longpressTimerIsActive = true;			

						longpressListener.onLongpress(OurView.this, (int) event.getY(), (int) event.getY(), s);						
						//						removeSprite(s);	
					}
				}
			}
			break;	




		case MotionEvent.ACTION_MOVE:
			//			if (canBeMOved){
			Sprite s = getClickedSprite(x, y);
			if (s != null)
				s.paintComponent(x, y);
			invalidate();
			//			}
		case MotionEvent.ACTION_UP:
			longpressTimerIsActive = false;
			break;	

		}



		return true;
	}


	public void pause(){
		isItOK = false;
		while (true){
			try{
				t.join();
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}
			break;
		}
		t = null;
	}
	public void resume(){
		isItOK = true;
		t = new Thread(this);
		t.start();

	}

	private class Line {
		Sprite mo1 = null;
		Sprite mo2 = null;

		public Line(Sprite mo1, Sprite mo2){
			this.mo1 = mo1;
			this.mo2 = mo2;

		}
		public Sprite getMo1(){
			return mo1;
		}
		public Sprite getMo2(){
			return mo2;
		}		
	}
}