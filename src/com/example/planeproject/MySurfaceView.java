package com.example.planeproject;

import java.util.Random;
import java.util.Vector;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

public class MySurfaceView extends SurfaceView implements Callback
{

	public static final int GAME_MENU=0;//游戏菜单
	public static final int GAMEING=1;//游戏中
	public static final int GAME_WIN=2;//游戏胜利
	public static final int GAME_LOST=3;//游戏失败
	public static final int GAME_PAUSE=0;//游戏暂停
	public static int GameStatus=GAME_MENU;
	
	//声明位图
	private Bitmap bmpbackground,bmpboom,bmpboos_boom,bmpboosbullet,bmpbullet_enemy,bmpbullet
	,bmpbutton_press,bmpbutton,bmpenemy_duck,bmpenemy_fly,bmpenemy_pig,bmpgamelost,bmpgamewin,
	bmphp,bmpicon,bmpmenu,bmpplayer;
	//声明变量
	public static int screenW,screenH;
	private SurfaceHolder sfh;
	boolean flag;
	Canvas canvas;
	Paint paint;
	MyThread thread;
	Context context;

	//声明各种类
	private GameMenu gameMenu;
	private GameBg gameBg;
	private Player player;
	private Vector<Enemy> vcEnemies;
	private int createEnemyTime=50;
	private int count=0;
	private int enemyArray[][]={{1,2},{1,3},{1,3,2},{1,2},{2,3},{3,1,2},{2,2},{1,2},{2,2},{1,3,1,1},{2,1},{1,3},{2,1},{-1}};
	public int emenyArrayIndex=0;
	public boolean isBoss=false;
	private Random random;
	
	private Vector<Bullet> vcEnemyBullets;
	private int count_EnemyBullet=0;
	private Vector<Bullet> vcPlayerBullets;
	private int count_PlayerBullet=0;
	
	private Boss boss;
	
	private Vector<Boom> vcBooms;
	
	class MyThread extends Thread
	{
		@Override
		public void run()
		{
			while(flag)
			{
				try
				{
					long start=System.currentTimeMillis();
					myDraw();
					logic();
					long end=System.currentTimeMillis();
					if(end-start<50)
					{
						Thread.sleep(50-(end-start));
					}
				} catch (Exception e)
				{
					// TODO: handle exception
				}
			}
		}
	}
	public MySurfaceView(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
		sfh=getHolder();
		sfh.addCallback(this);
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0)
	{
		// TODO Auto-generated method stub
		screenH=this.getHeight();
		screenW=this.getWidth();
		paint=new Paint();
		initGame();//裡面的GameMenu構造函數要用到screenH和screenW，必須寫在這個後面
		
		thread=new MyThread();
		flag=true;
		thread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0)
	{
		// TODO Auto-generated method stub
		flag=false;
	}

	public void initGame()
	{
		//加载位图资源
		bmpbackground=BitmapFactory.decodeResource(getResources(), R.drawable.background);
		bmpboom=BitmapFactory.decodeResource(getResources(), R.drawable.boom);
		bmpboos_boom=BitmapFactory.decodeResource(getResources(), R.drawable.boos_boom);
		bmpboosbullet=BitmapFactory.decodeResource(getResources(), R.drawable.boosbullet);
		bmpbullet=BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
		bmpbullet_enemy=BitmapFactory.decodeResource(getResources(), R.drawable.bullet_enemy);
		bmpbutton=BitmapFactory.decodeResource(getResources(), R.drawable.button);
		bmpbutton_press=BitmapFactory.decodeResource(getResources(), R.drawable.button_press);
		bmpenemy_duck=BitmapFactory.decodeResource(getResources(), R.drawable.enemy_duck);
		bmpenemy_fly=BitmapFactory.decodeResource(getResources(), R.drawable.enemy_fly);
		bmpenemy_pig=BitmapFactory.decodeResource(getResources(), R.drawable.enemy_pig);
		bmpgamelost=BitmapFactory.decodeResource(getResources(), R.drawable.gamelost);
		bmpgamewin=BitmapFactory.decodeResource(getResources(), R.drawable.gamewin);
		bmphp=BitmapFactory.decodeResource(getResources(), R.drawable.hp);
		bmpicon=BitmapFactory.decodeResource(getResources(), R.drawable.icon);
		bmpmenu=BitmapFactory.decodeResource(getResources(), R.drawable.menu);
		bmpplayer=BitmapFactory.decodeResource(getResources(), R.drawable.player);
		
		gameMenu=new GameMenu(bmpmenu, bmpbutton, bmpbutton_press);
		gameBg=new GameBg(bmpbackground);
		player=new Player(bmpplayer, bmphp,context);
		
		vcEnemies=new Vector<>();
		random=new Random();
		
		vcEnemyBullets=new Vector<>();
		vcPlayerBullets=new Vector<>();
		vcBooms=new Vector<>();
		
		boss=new Boss(bmpenemy_pig);
	}
	

	public void myDraw()
	{
		try
		{
			canvas=sfh.lockCanvas();
			if(canvas!=null)
			{
				switch (GameStatus)
				{
				case GAME_MENU:
					gameMenu.draw(canvas, paint);
					break;
				case GAMEING:
					gameBg.draw(canvas, paint);
					player.draw(canvas, paint);
					//if(isBoss==false)
					//{
						for(int i=0;i<vcEnemies.size();i++)
							vcEnemies.elementAt(i).draw(canvas, paint);
					
						for(int i=0;i<vcEnemyBullets.size();i++)
							vcEnemyBullets.elementAt(i).draw(canvas, paint);
						for(int i=0;i<vcPlayerBullets.size();i++)
							vcPlayerBullets.elementAt(i).draw(canvas, paint);
						
						
						for(int i=0;i<vcBooms.size();i++)
							vcBooms.elementAt(i).draw(canvas, paint);
						if(isBoss)
							boss.draw(canvas, paint);
					break;
				case GAME_WIN:
					canvas.save();
					canvas.scale((float)screenW/(float)bmpgamewin.getWidth(), (float)screenH/(float)bmpgamewin.getHeight());
					canvas.drawBitmap(bmpgamewin, 0, 0, paint);
					canvas.restore();
					break;
				case GAME_LOST:
					canvas.save();
					canvas.scale((float)screenW/(float)bmpgamelost.getWidth(), (float)screenH/(float)bmpgamelost.getHeight());
					canvas.drawBitmap(bmpgamelost, 0, 0, paint);
					canvas.restore();
					break;

				default:
					break;
				}
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}finally{
			if(canvas!=null)
			{
				sfh.unlockCanvasAndPost(canvas);
			}
		}
		
	}
	private void logic()
	{
		switch (GameStatus)
		{
		case GAME_MENU:
			break;
		case GAMEING:
			gameBg.logic();
			//怪物们的逻辑
			for(int i=0;i<vcEnemies.size();i++)
			{
				vcEnemies.elementAt(i).logic();
				if(vcEnemies.elementAt(i).isDead)
				vcEnemies.remove(i);
			}
			count++;
			if(count%createEnemyTime==0)//生成怪物
			{
				for(int i=0;i<enemyArray[emenyArrayIndex].length;i++)
				{
					if(enemyArray[emenyArrayIndex][i]==1)//FLY
					{
						vcEnemies.add(new Enemy(bmpenemy_fly, 1, random.nextInt(screenW-50), -50));
					}
					else if(enemyArray[emenyArrayIndex][i]==2)//DUCKL
					{
						vcEnemies.add(new Enemy(bmpenemy_duck, 2, -50, random.nextInt(30)));
					}
					else if(enemyArray[emenyArrayIndex][i]==3)//DUCKR
					{
						vcEnemies.add(new Enemy(bmpenemy_duck, 3, screenW, random.nextInt(30)));
					}
				}
				emenyArrayIndex++;
				if(emenyArrayIndex==enemyArray.length-1)
				{
					isBoss=true;
				}
			}
			if(isBoss)
				boss.logic();
			//player的逻辑
			for(int i=0;i<vcEnemies.size();i++)//先判断碰撞
			{
				if(player.isCollsionWith(vcEnemies.elementAt(i)))
				{
					player.setplayerHp(player.getplayerHp()-1);
				}
			}
			player.logic();
			//子弹的逻辑
			count_EnemyBullet++;
			if(count_EnemyBullet%40==0)//生成怪物的子弹
			{
				for(int i=0;i<vcEnemies.size();i++)
				{
					Enemy enemy=vcEnemies.elementAt(i);
					int type=enemy.type;
					if(type==Enemy.TYPE_DUCKL||type==Enemy.TYPE_DUCKR)
					{
						vcEnemyBullets.add(new Bullet(bmpbullet_enemy, enemy.x+10, enemy.y+20, Bullet.BULLET_DUCK));
					}
					else if(type==Enemy.TYPE_FLY)
					{
						vcEnemyBullets.add(new Bullet(bmpbullet_enemy, enemy.x+10, enemy.y+20, Bullet.BULLET_FLY));
					}
				}
			}
			for(int i=0;i<vcEnemyBullets.size();i++)
			{
				vcEnemyBullets.elementAt(i).logic();
				if(vcEnemyBullets.elementAt(i).isDead)
					vcEnemyBullets.remove(i);
			}
			if(isBoss)//如果是boss關就生成boss子彈
			{
				if(count_EnemyBullet%20==0)
				{
					vcEnemyBullets.add(new Bullet(bmpboosbullet, boss.bossX+25, boss.bossY+40, Bullet.BUTTLE_BOSS));
				}
			}
			count_PlayerBullet++;//生成主角的子弹
			if(count_PlayerBullet%20==0)
			{
				vcPlayerBullets.add(new Bullet(bmpbullet, player.x+10, player.y-20, Bullet.BULLET_PLAYER));
			}
			for(int i=0;i<vcPlayerBullets.size();i++)
			{
				vcPlayerBullets.elementAt(i).logic();
				if(vcPlayerBullets.elementAt(i).isDead)
					vcPlayerBullets.remove(i);
			}
			for(int i=0;i<vcEnemyBullets.size();i++)//判断主角是否中弹
			{
				if(player.isCollsionWith(vcEnemyBullets.elementAt(i)))
				{
					player.setplayerHp(player.getplayerHp()-1);
				}
			}
			for(int i=0;i<vcPlayerBullets.size();i++)//判断怪物是否中弹
			{
				for(int j=0;j<vcEnemies.size();j++)
				{
					if(vcEnemies.elementAt(j).isCollsionWith(vcPlayerBullets.elementAt(i)))
					{
						//Boom
						vcBooms.add(new Boom(bmpboom, vcEnemies.elementAt(j).x, vcEnemies.elementAt(j).y, 7));
					}
				}
			}
			for(int i=0;i<vcPlayerBullets.size();i++)//判斷Boss是否中彈
			{
				if(boss.isCollsionWith(vcPlayerBullets.elementAt(i)))
				{
					boss.bossHp--;
					vcBooms.add(new Boom(bmpboos_boom, boss.bossX+40, boss.bossY+40, 5));
					break;
				}
			}
			if(boss.bossHp==0)
			{
				isBoss=false;
				GameStatus=GAME_WIN;
				vcBooms.add(new Boom(bmpboos_boom, boss.bossX, boss.bossY, 5));
			}
			for(int i=0;i<vcBooms.size();i++)
			{
				vcBooms.elementAt(i).logic();
				if(vcBooms.elementAt(i).isPlayEnd)
					vcBooms.remove(i);
			}
			//最後檢測主角生命值
			if(player.getplayerHp()<=0)
				GameStatus=GAME_LOST;
			
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO Auto-generated method stub
		switch (GameStatus)
		{
		case GAME_MENU:
			gameMenu.onTouchEvent(event);
			break;
		case GAMEING:
			player.onTouchEvent(event);
			break;

		default:
			break;
		}
		return true;
	}


	
}
