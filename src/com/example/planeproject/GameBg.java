package com.example.planeproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class GameBg
{
	private Bitmap bmpground1,bmpground2;
	private float bg1x,bg1y,bg2x,bg2y;
	private int speed=3;
	public GameBg(Bitmap bmpbackground)
	{
		this.bmpground1=bmpbackground;
		this.bmpground2=bmpbackground;
		bg1x=bg2x=0;
		bg1y=-Math.abs(MySurfaceView.screenH-bmpbackground.getHeight());
		bg2y=bg1y-bmpbackground.getHeight()+220;//220ÓÃì¶ˆDÆ¬ã•½Ó
	}
	public void draw(Canvas canvas,Paint paint)
	{
		canvas.save();
		canvas.scale((float)MySurfaceView.screenW/(float)bmpground1.getWidth(), 1);
		canvas.drawBitmap(bmpground1, bg1x, bg1y, paint);
		canvas.drawBitmap(bmpground2, bg2x, bg2y, paint);
		canvas.restore();
	}
	public void logic()
	{
		bg1y+=speed;
		bg2y+=speed;
		if(bg1y>MySurfaceView.screenH)
		{
			bg1y=bg2y-bmpground1.getHeight()+220;
		}
		if(bg2y>MySurfaceView.screenH)
		{
			bg2y=bg1y-bmpground2.getHeight()+220;
		}
	}
}
