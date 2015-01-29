package com.example.planeproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

public class MainActivity extends Activity
{
	private MySurfaceView mySurfaceView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mySurfaceView=new MySurfaceView(this);
		setContentView(mySurfaceView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		if(keyCode==event.KEYCODE_BACK)
		{
			if(mySurfaceView.GameStatus==mySurfaceView.GAMEING || mySurfaceView.GameStatus==mySurfaceView.GAME_WIN || mySurfaceView.GameStatus==mySurfaceView.GAME_LOST)
			{
				mySurfaceView.GameStatus=mySurfaceView.GAME_MENU;
				mySurfaceView.initGame();
				mySurfaceView.emenyArrayIndex=0;
				mySurfaceView.isBoss=false;
			}
			else if(mySurfaceView.GameStatus==mySurfaceView.GAME_MENU)
			{
				MainActivity.this.finish();
				System.exit(0);
			}
		}
		return true;
	}

}
