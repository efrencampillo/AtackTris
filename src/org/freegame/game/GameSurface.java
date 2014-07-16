
package org.freegame.game;

import java.util.ArrayList;

import org.freegame.attacktis.AtackTris;
import org.freegame.models.GameButton;
import org.freegame.models.Block;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * @author kuno
 *
 */
public class GameSurface extends SurfaceView implements Runnable, 
	SurfaceHolder.Callback{
	 
	Thread mythread;//thread that calls paint
	Context context;//activity reference
	int frameRate=40;//frame rate from the render 
	int sleeptime=(1000/frameRate); //time to wait each frame
	volatile boolean isRunning=false;// variable to control the rendering loop
	
	Bitmap buffer;
	public static int sWidth=0;
	public static int sHeight=0;
	Canvas drawCanvas;
	Canvas tmpCanvas;
	
	
	
	
	/**
	 * @param surfaceTexture
	 */
	public GameSurface(Context ctx) {
		super(ctx);
		context=ctx;
		getHolder().addCallback(this);
	}
	
	
	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
	 */
	@Override
	public void surfaceChanged(SurfaceHolder sholder, int format, int width, int height) {
		sWidth=width;
		sHeight=height;
		isRunning = true;
		buffer=Bitmap.createBitmap(sWidth, sHeight, Config.ARGB_8888);
		mythread = new Thread(this);
		mythread.start();
	}
	
	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceCreated(SurfaceHolder surface) {
		//Log.i("Surface","Created");
	}
	
	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder surface) {
		 boolean retry = true;
		 isRunning = false;
		 while(retry){
		    try {
		    	mythread.join();
		    	retry = false;
		    }catch(InterruptedException e){
		    	e.printStackTrace();
		    }
		 }
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		 while(isRunning) {
		      try {
		    	 drawCanvas= getHolder().lockCanvas(null);
		         Thread.sleep(sleeptime);
		         synchronized(getHolder()) {
		         	if(drawCanvas!=null){
		         		tmpCanvas = new Canvas(buffer);
		         		tmpCanvas.drawColor(Color.GRAY);
		         		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);	
						((AtackTris)context).mainloop(tmpCanvas);
						drawCanvas.drawBitmap(buffer, 0, 0, paint);
					}
					getHolder().unlockCanvasAndPost(drawCanvas);
				 }
		      } catch(Exception e) {
				e.printStackTrace();
		      }
	      }
	}
	
	    /*  public void paintend(ArrayList<GameButton> buttons){
	       Graphics2D gp=(Graphics2D)paint.getGraphics();
	         gp.drawImage((BufferedImage)cache.get("go"),0,0, this);//paint background
	         gp.setColor(Color.white);
	         gp.drawString("Final Points:"+playerpoints,140, 300);
	         gp.dispose();
	        Graphics g=this.getGraphics();
	        g.drawImage(paint,0,0, this);
	    	for(GameButton btn:buttons){
	    		btn.Draw(tmpCanvas);
	    	}
	    } *///paint game over and the final score   
	    
}
