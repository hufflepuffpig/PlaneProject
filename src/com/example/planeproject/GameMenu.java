package com.example.planeproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class GameMenu
{
	private Bitmap bmpmenu,bmpbutton,bmpbutton_press;
	private float btnX,btnY,sx,sy;
	private boolean ispress;
	public GameMenu(Bitmap bmpMenu,Bitmap bmpbutton,Bitmap bmpbutton_press)
	{
		this.bmpmenu=bmpMenu;
		this.bmpbutton=bmpbutton;
		this.bmpbutton_press=bmpbutton_press;
		btnX=MySurfaceView.screenW/2-bmpbutton.getWidth()/2;
		btnY=MySurfaceView.screenH-bmpbutton.getHeight();
		sx=(float)MySurfaceView.screenW/(float)bmpmenu.getWidth();
		sy=(float)MySurfaceView.screenH/(float)bmpmenu.getHeight();
		ispress=false;
	}
	public void draw(Canvas canvas,Paint paint)
	{
		canvas.save();
		canvas.scale(sx, sy, 0, 0);
		canvas.drawBitmap(bmpmenu, 0, 0, paint);
		canvas.restore();
		if(!ispress)
			canvas.drawBitmap(bmpbutton, btnX, btnY, paint);
		else {
			canvas.drawBitmap(bmpbutton_press, btnX, btnY, paint);
		}
	}
	public void onTouchEvent(MotionEvent event)
	{
		float x=event.getX();
		float y=event.getY();
		if(event.getAction()==MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_MOVE)
		{
			if(x>btnX && x<btnX+bmpbutton.getWidth())
			{
				if(y>btnY && y<btnY+bmpbutton.getHeight())
				{
					//Touch在按钮区域
					ispress=true;
				}
				else {
					ispress=false;
				}
			}
			else
				ispress=false;
		}
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			if(x>btnX && x<btnX+bmpbutton.getWidth())
			{
				if(y>btnY && y<btnY+bmpbutton.getHeight())
				{
					//Touch在按钮区域
					ispress=false;
					MySurfaceView.GameStatus=MySurfaceView.GAMEING;
				}
			}
		}
	}

}
