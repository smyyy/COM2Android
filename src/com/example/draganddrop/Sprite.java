package com.example.draganddrop;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

public class Sprite {


	int x, y;
	public int xSpeed, ySpeed;
	int height, width;
	Bitmap b;
	OurView ov;
	boolean walking = true;

	int currentframe = 0;
	int direction = 2;


	
	public Sprite(OurView ourView, Bitmap blob, int xS) {
		// TODO Auto-generated constructor stub
		b = blob;
		ov = ourView;
		height = b.getHeight()/4; // /4 om man har 4 bilder i spritcheetet
		width = b.getWidth()/4; // /4 om man har 4 bilder i spritcheetet
		x = y = xS;
		xSpeed = 10;
		ySpeed = 0;	
		
//		setOnTouchListener(this);
		
	}



	public void onDraw(Canvas c) {
		// TODO Auto-generated method stub
		update();
		int srcY = direction * height;
		int srcX = currentframe * width;
		Rect src = new Rect (srcX,srcY,srcX+width, srcY+height); //var man vill skära i bilden
		Rect dst = new Rect (x,y, x+width, y+height); //var den ska hamna på skärmen
		
		c.drawBitmap(b,src ,dst , null);		
	}

	private void update(){

		//0 = down
		//1 = left
		//2 = right
		//3 = up		
		//facing down
		if (x > ov.getWidth() - width - xSpeed){
			xSpeed = 0;
			ySpeed = 10;
			direction = 0;
		}

		//going left
		if (y > ov.getHeight() - height - ySpeed){
			xSpeed = -10;
			ySpeed = 0;
			direction = 1;
		}

		//going up
		if (x + xSpeed < 0 ){
			x = 0;
			xSpeed = 0;
			ySpeed = -10;
			direction = 3;
		}

		//facing right
		if (y + ySpeed < 0 ){
			y = 0;
			xSpeed = 10;
			ySpeed = 0;
			direction = 2;
		}


		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (walking)
			currentframe = ++currentframe % 4; // /antal kolumner
		x += xSpeed;
		y += ySpeed;
	}


	protected void onManagedUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isCollition(float x2, float y2) {
		return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
	}
	
	public void moveSpite(int x, int y){
		
		this.x = x;
		this.y = y;
	}
	
	
	
		
	public void setSpeed(){

		if (xSpeed != 0 || ySpeed != 0){
			walking = false;
			xSpeed = 0;
			ySpeed = 0;
		}
		else{
			switch(direction){
			case 0:
				xSpeed = 0;
				ySpeed = 10;
				walking = true;
				break;
			case 1:
				xSpeed = -10;
				ySpeed = 0;
				walking = true;
				break;
			case 2:
				xSpeed = 10;
				ySpeed = 0;
				walking = true;
				break;
			case 3:
				xSpeed = 0;
				ySpeed = -10;
				walking = true;
				break;
			}
		}

	}

}
