package com.example.draganddrop;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {


	int xCorner, yCorner;
	//	public int xSpeed, ySpeed;
	int picHeight, picWidth;
	Bitmap picture;
	OurView ov;
	float xCoord; //mitten
	float yCoord; //mitten
	
	String name;

	public Sprite(OurView ourView, Bitmap blob, int xCoord, int yCoord, String name) {
		// TODO Auto-generated constructor stub
		picture = blob;
		ov = ourView;
		picHeight = picture.getHeight(); // /4 om man har 4 bilder i spritcheetet
		picWidth = picture.getWidth(); // /4 om man har 4 bilder i spritcheetet
		
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		
		this.xCorner = (int)xCoord - (picHeight/2);
		this.yCorner = (int)yCoord - (picWidth/2);
		
		this.name = name;

	}

	public void paintComponent(int xCenter, int yCenter){
		this.xCorner = (int)xCenter - (picHeight/2);
		this.yCorner = (int)yCenter - (picWidth/2);
		//	invalidate();
	}



	public void onDraw(Canvas c) {
		// TODO Auto-generated method stub
		
		Rect src = new Rect (0,0,picWidth, picHeight); //var man vill skära i bilden
		Rect dst = new Rect (xCorner,yCorner, xCorner+picWidth,yCorner+picHeight); //var den ska hamna på skärmen

		c.drawBitmap(picture, null ,dst, null);	
//		c.drawText(name, 10,10 , null);
	}


	protected void onManagedUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub

	}

	public boolean isCollition(float x2, float y2) {
		return x2 > xCorner && x2 < xCorner + picWidth && y2 > yCorner && y2 < yCorner + picHeight;
	}

	public void moveSprite(int x, int y){

		this.xCorner = x;
		this.yCorner = y;
	}

}
