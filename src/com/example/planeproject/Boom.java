package com.example.planeproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Boom
{
	private Bitmap bmpboom;
	private float boomX,boomY;
	private int totalFramenum;
	private int curFrameIndex;
	private float frameW,frameH;
	public boolean isPlayEnd=false;
	public Boom(Bitmap bmpboom,float boomX,float boomY,int totalFramenum)
	{
		this.bmpboom=bmpboom;
		this.boomX=boomX;
		this.boomY=boomY;
		this.totalFramenum=totalFramenum;
		curFrameIndex=0;
		frameW=bmpboom.getWidth()/totalFramenum;
		frameH=bmpboom.getHeight();
	}
	public void draw(Canvas canvas,Paint paint)
	{
		canvas.save();
		canvas.clipRect(boomX, boomY, boomX+frameW, boomY+frameH);
		canvas.drawBitmap(bmpboom, boomX-curFrameIndex*frameW, boomY, paint);
		canvas.restore();
	}
	public void logic()
	{
		if(curFrameIndex<totalFramenum)
		{
			curFrameIndex++;
		}else {
			curFrameIndex=0;
			isPlayEnd=true;
		}
		
	}
}
