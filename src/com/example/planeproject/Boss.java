package com.example.planeproject;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Boss
{
	public int bossHp=30;
	private Bitmap bmpboss;
	private float frameW,frameH;
	public float bossX,bossY;
	private int curframeIndex=0;
	private int speed=5;
	public boolean isCollsion=false;
	private int nocollsionTime=30;
	private int count=0;
	public Boss(Bitmap bmpboss)
	{
		this.bmpboss=bmpboss;
		frameW=bmpboss.getWidth()/10;
		frameH=bmpboss.getHeight();
		bossX=MySurfaceView.screenW/2-frameW/2;
		bossY=20;
	}
	public void draw(Canvas canvas,Paint paint)
	{
		canvas.save();
		canvas.clipRect(bossX, bossY, bossX+frameW, bossY+frameH);
		canvas.drawBitmap(bmpboss, bossX, bossY, paint);
		canvas.restore();
	}
	public void logic()
	{
		curframeIndex++;
		curframeIndex=curframeIndex%10;
		bossX+=speed;
		if(bossX+frameW>=MySurfaceView.screenW)
			speed=-speed;
		else if(bossX<=0)
			speed=-speed;
		
		if(isCollsion)
		{
			count++;
			if(count==nocollsionTime)
			{
				count=0;
				isCollsion=false;
			}
		}
	}
	public boolean isCollsionWith(Bullet bullet)
	{
		if(!isCollsion)
		{
			float x2=bullet.bulletX;
			float y2=bullet.bulletY;
			float w2=bullet.bmpbullet.getWidth();
			float h2=bullet.bmpbullet.getHeight();
			if(x2<bossX && x2+w2<bossX)
			{
				return false;
			}
			else if(x2>bossX && x2>bossX+frameW)
			{
				return false;
			}
			else if(y2<bossY && y2+h2<bossY)
			{
				return false;
			}
			else if(y2>bossY && y2>bossY+frameH)
			{
				return false;
			}
			else 
			{
				isCollsion=true;
				return true;
			}
		}else {
			return false;
		}
	}
}
