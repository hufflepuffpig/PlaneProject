package com.example.planeproject;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy
{
	public int type;
	public final static int TYPE_FLY=1;
	public final static int TYPE_DUCKL=2;
	public final static int TYPE_DUCKR=3;
	private Bitmap bmpEnemy;
	public float x,y;
	public int frameW,frameH;
	private int frameIndex;
	private int speed;
	public boolean isDead;
	public Enemy(Bitmap bmpEnemy,int type,int x,int y)
	{
		this.bmpEnemy=bmpEnemy;
		this.type=type;
		this.x=x;
		this.y=y;
		frameW=bmpEnemy.getWidth()/10;
		frameH=bmpEnemy.getHeight();
		frameIndex=0;
		switch (type)
		{
		case TYPE_FLY:
			speed=30;
			break;
		case TYPE_DUCKL:
			speed=3;
			break;
		case TYPE_DUCKR:
			speed=3;
			break;
		default:
			break;
		}
		isDead=false;
	}
	public void draw(Canvas canvas,Paint paint)
	{
		canvas.save();
		canvas.clipRect(x, y, x+frameW, y+frameH);
		canvas.drawBitmap(bmpEnemy, x-frameIndex*frameW, y, paint);
		canvas.restore();
	}
	public void logic()
	{
		frameIndex++;
		frameIndex=frameIndex%10;
		switch (type)
		{
		case TYPE_FLY:
			speed-=1;
			y+=speed;
			if(y<=-200)
				isDead=true;
			break;
		case TYPE_DUCKL:
			x+=speed/2;
			y+=speed;
			if(x>MySurfaceView.screenW)
				isDead=true;
			break;
		case TYPE_DUCKR:
			x-=speed/2;
			y+=speed;
			if(x<-50)
				isDead=true;
			break;
		default:
			break;
		}
	}
	public boolean isCollsionWith(Bullet bullet)
	{
		float x2=bullet.bulletX;
		float y2=bullet.bulletY;
		float w2=bullet.bmpbullet.getWidth();
		float h2=bullet.bmpbullet.getHeight();
		if(x2<x && x2+w2<x)
		{
			return false;
		}
		else if(x2>x && x2>x+frameW)
		{
			return false;
		}
		else if(y2<y && y2+h2<y)
		{
			return false;
		}
		else if(y2>y && y2>y+frameH)
		{
			return false;
		}
		else 
		{
			isDead=true;
			return true;
		}
	}

}
