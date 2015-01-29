package com.example.planeproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet
{
	public Bitmap bmpbullet;
	public float bulletX,bulletY;
	private int speed;
	public boolean isDead;
	public int type;
	
	public static final int BULLET_PLAYER=-1;
	public static final int BULLET_DUCK=1;
	public static final int BULLET_FLY=2;
	public static final int BUTTLE_BOSS=3;
	
	public Bullet(Bitmap bmpbullet,float bulletX,float bulletY,int type)
	{
		this.bmpbullet=bmpbullet;
		this.bulletX=bulletX;
		this.bulletY=bulletY;
		this.type=type;
		switch (type)
		{
		case BULLET_PLAYER:
			speed=4;
			break;
		case BULLET_DUCK:
			speed=3;
			break;
		case BULLET_FLY:
			speed=4;
			break;
		case BUTTLE_BOSS:
			speed=5;
			break;

		default:
			break;
		}
		isDead=false;
	}
	public void draw(Canvas canvas,Paint paint)
	{
		canvas.drawBitmap(bmpbullet, bulletX, bulletY, paint);
	}
	public void logic()
	{
		switch (type)
		{
		case BULLET_PLAYER:
			bulletY-=speed;
			if(bulletY<-50)
				isDead=true;
			break;
		case BULLET_DUCK:
			bulletY+=speed;
			if(bulletY>MySurfaceView.screenH)
				isDead=true;
			break;
		case BULLET_FLY:
			bulletY+=speed;
			if(bulletY>MySurfaceView.screenH)
				isDead=true;
			break;
		case BUTTLE_BOSS:
			bulletY+=speed;
			if(bulletY>MySurfaceView.screenH)
				isDead=true;
			break;

		default:
			break;
		}
	}
}
