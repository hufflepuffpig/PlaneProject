package com.example.planeproject;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class Player
{
	private int playerHp=3;
	private Bitmap bmpplayerHp,bmpplayer;
	public float x,y;
	private int speed=5;
	
	private boolean iscollsion=false;
	private int count=0;
	private int nocollsionTime=150;
	//∂®¡x ÷Ñ›
	public GestureDetector gestureDetector;
	
	public Player(final Bitmap bmpplayer,Bitmap bmphp,Context context)
	{
		this.bmpplayerHp=bmphp;
		this.bmpplayer=bmpplayer;
		x=MySurfaceView.screenW/2-bmpplayer.getWidth()/2;
		y=MySurfaceView.screenH-bmpplayer.getHeight();
		
		gestureDetector=new GestureDetector(context,new GestureDetector.OnGestureListener()
		{
			
			@Override
			public boolean onSingleTapUp(MotionEvent arg0)
			{
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
					float arg3)
			{
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
					float arg3)
			{
				// TODO Auto-generated method stub
				if(arg1.getX()-arg0.getX()<0)
				{
					//◊ÛﬂÖª¨Ñ”
					System.out.println("LEFT   ---------->");
					x-=speed;
					if(x<=0)
					{
						x=0;
					}
				}
				else if(arg1.getX()-arg0.getX()>0)
				{
					//”“ﬂÖª¨Ñ”
					System.out.println("RIGHT  <----------");
					x+=speed;
					if(x+bmpplayer.getWidth()>=MySurfaceView.screenW)
					{
						x=MySurfaceView.screenW-bmpplayer.getWidth();
					}
				}
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent arg0)
			{
				// TODO Auto-generated method stub
				return false;
			}
		});
	}
	public void draw(Canvas canvas,Paint paint)
	{
		if(iscollsion)
		{
			if(count%2==0)
				canvas.drawBitmap(bmpplayer, x, y, paint);
		}
		else {
			canvas.drawBitmap(bmpplayer, x, y, paint);
		}
		
		for(int i=0;i<playerHp;i++)
		{
			canvas.drawBitmap(bmpplayerHp, 0+i*bmpplayerHp.getWidth(), 0, paint);
		}
	}
	public boolean onTouchEvent(MotionEvent event)
	{
		if(event.getX()<x)
		{
			x-=speed;
			if(x<=event.getX())
			{
				x=event.getX();
			}
		}
		if(event.getX()>x)
		{
			x+=speed;
			if(x>=event.getX())
			{
				x=event.getX();
			}
		}
		return false;
	}
	public int getplayerHp()
	{
		return this.playerHp;
	}
	public void setplayerHp(int newHp)
	{
		this.playerHp=newHp;
	}
	public boolean isCollsionWith(Enemy enemy)
	{
		float x2=enemy.x;
		float y2=enemy.y;
		int w2=enemy.frameW;
		int h2=enemy.frameH;
		if(!iscollsion)
		{
			if(x2<x && x2+w2<x)
			{
				return false;
			}
			else if(x2>x && x2>x+bmpplayer.getWidth())
			{
				return false;
			}
			else if(y2<y && y2+h2<y)
			{
				return false;
			}
			else if(y2>y && y2>y+bmpplayer.getHeight())
			{
				return false;
			}
			else 
			{
				iscollsion=true;
				return true;
			}
		}
		else {
			return false;
		}
	}
	public boolean isCollsionWith(Bullet bullet)
	{
		float x2=bullet.bulletX;
		float y2=bullet.bulletY;
		float w2=bullet.bmpbullet.getWidth();
		float h2=bullet.bmpbullet.getHeight();
		if(!iscollsion)
		{
			if(x2<x && x2+w2<x)
			{
				return false;
			}
			else if(x2>x && x2>x+bmpplayer.getWidth())
			{
				return false;
			}
			else if(y2<y && y2+h2<y)
			{
				return false;
			}
			else if(y2>y && y2>y+bmpplayer.getHeight())
			{
				return false;
			}
			else 
			{
				iscollsion=true;
				return true;
			}
		}
		else {
			return false;
		}
	}
	public void logic()
	{
		count++;
		if(count>nocollsionTime)
		{
			count=0;
			iscollsion=false;
		}
	}
}
