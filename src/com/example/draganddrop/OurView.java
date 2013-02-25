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

//public class OurView extends SurfaceView{
public class OurView extends SurfaceView implements Runnable {

	Thread t = null;
	SurfaceHolder holder;
	boolean isItOK = false;
	Sprite sprite, sprite2;
	boolean spriteLoaded = false;
	private long lastClick;
	private List<Sprite> sprites = new ArrayList<Sprite>();
	Bitmap ball, blob;
	float x = 200, y = 200;

	final int LONG_PRESS_TIME = 1000; //1 sec
	Timer longPressTimer = new Timer();
	private OurView.OnLongpressListener longpressListener;
	boolean longpressTimerIsActive = false;

	//	final Handler _handler = new Handler(); 
	//	Runnable _longPressed = new Runnable() { 
	//	    public void run() {
	//	        System.out.println("LOOOONG PRESS!!");
	//	    }   
	//	};


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
		blob = BitmapFactory.decodeResource(getResources(), R.drawable.sprite2);

		sprite = new Sprite(this, blob, 100);
		sprites.add(sprite);
		sprite2 = new Sprite(this, blob, 200);
		sprites.add(sprite2);


		//		Canvas c = holder.lockCanvas();
		//		draw(c);
		//		holder.unlockCanvasAndPost(c);
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
		//			c.drawBitmap(ball, x - (ball.getWidth())/2, y - (ball.getHeight())/2, null);
		//			c.drawBitmap(ball, 300, 300, null);
		//		sprite.onDraw(c);
		//		sprite2.onDraw(c);
		//		
		if (spriteLoaded){
			try {
				Thread.sleep(50);
				spriteLoaded = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (Sprite spr : sprites){
			spr.onDraw(c);
		}


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
		public void onLongpress(OurView view, int xCord, int yCord);
	}	




	public void setOnLongpressListener(OurView.OnLongpressListener listener) {
		longpressListener = listener;
	}



	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch(event.getAction()){
		//		case MotionEvent.ACTION_DOWN:
		//			
		//			if (System.currentTimeMillis() - lastClick > 500) {
		//				lastClick = System.currentTimeMillis();
		//				synchronized (getHolder()) {
		//					for (int i = sprites.size() - 1; i >= 0; i--) {
		//						final Sprite clickedSprite = sprites.get(i);
		//						if (clickedSprite.isCollition(event.getX(), event.getY())) {
		//					
		//							System.out.println("ACTION DOWN");
		//							clickedSprite.setSpeed();
		//
		//							final int xCord = (int) event.getX();
		//							final int yCord = (int) event.getY();
		//
		//							longpressTimerIsActive = true;
		//							
		////							longPressTimer.schedule(new TimerTask(){
		////								@Override
		////								public void run() {
		////									
		////									longpressListener.onLongpress(OurView.this, xCord, yCord);
		////								}
		////
		////							}, LONG_PRESS_TIME);
		//
		//							break;
		//						}
		//					}
		//				}
		//			}
		//			else{
		//				lastClick = System.currentTimeMillis();
		//				synchronized (getHolder()) {
		//					for (int i = sprites.size() - 1; i >= 0; i--) {
		//						Sprite clickedSprite = sprites.get(i);
		//						if (clickedSprite.isCollition(event.getX(), event.getY())) {
		//							//							sprites.remove(sprite);
		//							System.out.println("ACTION DELETE");
		//							removeSprite(clickedSprite);
		//
		//							break;
		//						}
		//					}
		//				}
		//			}
		//
		//
		//			break;
				case MotionEvent.ACTION_UP:
					//			if (System.currentTimeMillis() - lastClick > 500) {
					//				lastClick = System.currentTimeMillis();
					//				synchronized (getHolder()) {
					//					for (int i = sprites.size() - 1; i >= 0; i--) {
					//						Sprite clickedSprite = sprites.get(i);
					//						if (clickedSprite.isCollition(event.getX(), event.getY())) {
					//							//							sprites.remove(sprite);
					System.out.println("ACTION UP");
					if(longpressTimerIsActive){
		//				longPressTimer.cancel();
					}
					longpressTimerIsActive = false;
		
					//							clickedSprite.setSpeed();
					//							break;
					//						}
					//					}
					//				}
					//			}
					break;
					
		case MotionEvent.ACTION_MOVE:

			//			lastClick = System.currentTimeMillis();
			synchronized (getHolder()) {
				for (Sprite spr : sprites){
					if (spr.isCollition(event.getX(), event.getY())){
						System.out.println("ACTION MOVE");
						
						spr.moveSpite((int) event.getX(), (int) event.getY());
						
						
						
						break;
					}
				}
				
				
//				
//				for (int i = sprites.size() - 1; i >= 0; i--) {
//					Sprite clickedSprite = sprites.get(i);
//					if (clickedSprite.isCollition(event.getX(), event.getY())) {
//
//						int moveX = (int) event.getX();
//						int moveY = (int) event.getY();
//
//
//						
//						Canvas c = holder.lockCanvas();
//						clickedSprite.moveSpite(moveX, moveY);
//						draw(c);
//						holder.unlockCanvasAndPost(c);
//
//						break;
//					}
//				}
			}
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

}