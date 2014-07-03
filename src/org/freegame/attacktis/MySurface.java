/* *************************************************************************
 *
 *   Copyright (c)  2013 by Jaguar Labs.
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 *   This software is furnished under license and may be used and copied
 *   only in accordance with the terms of its license and with the
 *   inclusion of the above copyright notice. This software and any other
 *   copies thereof may not be provided or otherwise made available to any
 *   other party. No title to and/or ownership of the software is hereby
 *   transferred.
 *
 *   The information in this software is subject to change without notice and
 *   should not be construed as a commitment by JaguarLabs.
 *
 * @(#)Id: 
 * Last Revised By   : 
 * Last Checked In   : 02/07/2014
 * Last Version      :
 *
 * Original Author   : kuno
 * Origin            : 
 * Notes             :
 *
 * *************************************************************************/
package org.freegame.attacktis;

import java.util.ArrayList;

import org.freegame.models.GameButton;
import org.freegame.models.block;


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
public class MySurface extends SurfaceView implements Runnable, 
	SurfaceHolder.Callback{
	 
	Thread mythread;//thread that calls paint
	Context context;//activity reference
	int frameRate=40;//frame rate from the render 
	int sleeptime=(1000/frameRate); //time to wait each frame
	volatile boolean isRunning=false;// variable to control the rendering loop
	
	Bitmap buffer;
	public int sWidth=0;
	public int sHeight=0;
	Canvas drawCanvas;
	Canvas tmpCanvas;
	
	/**
	 * @param surfaceTexture
	 */
	public MySurface(Context ctx) {
		super(ctx);
		context=ctx;
		getHolder().addCallback(this);
	}
	/**
	 * fling event to move
	 * */
	public void fling(MotionEvent event1,MotionEvent event2, float velx, float vely){
		if(Math.abs(velx)>Math.abs(vely)){///horizontal fling
			if(velx<0){//TO left fling
				Log.i("fling", "left");
			}else{//TO right fling
				Log.i("fling", "right");
			}
		}else{///vertical fling
			if(vely<0){
				Log.i("fling", "up");
			}else{
				Log.i("fling", "down");
			}
		}
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
		Log.i("Surface","Created");
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
						((AtackTris)context).mainloop();
						drawCanvas.drawBitmap(buffer, 0, 0, paint);
					}
					getHolder().unlockCanvasAndPost(drawCanvas);
				 }
		      } catch(Exception e) {
				e.printStackTrace();
		      }
	      }
	}
	
	/**
	 * */
	  public void paintGameLevel(){
	           // long start = System.currentTimeMillis();//for count time to paint
		  block[][] board=((AtackTris)context).table;
		  int bw=((AtackTris)context).board_width;
		  int bh=((AtackTris)context).board_heigth;
		  Paint p=new Paint();
		  
		  for(int i=0;i<bw;i++){
			  for(int j=bh-1;j>0;j--){
				 if(board[i][j]!=null&&board[i][j].color!=6){
					 p.setStyle(Paint.Style.FILL);
					 p.setColor(board[i][j].color);
					 tmpCanvas.drawRect(i*50, j*50,(i+1)*50 ,(j-1)*50, p);
					 p.setStyle(Paint.Style.STROKE);
					 p.setColor(Color.BLACK); 
					 p.setStrokeWidth(2);
					 tmpCanvas.drawRect(i*50, j*50,(i+1)*50 , (j-1)*50, p);
					
				 }
			  }
		  }
	           
	     /*      
	            if(!point.status)gp.drawImage((BufferedImage)cache.get("cursor"),(point.x-1)*33+10,point.y*33+20, this);//paint cursor
	            else gp.drawImage((BufferedImage)cache.get("cursors"),(point.x-1)*33+10,point.y*33+20, this);
	            gp.drawRect(5,10, 210, 545);
	            gp.drawString("Seconds remaining="+(int)((btime-time)/1000),100,570);
	            long elapsed = System.currentTimeMillis() - start;// total time elpased for create the image
	            gp.drawString("tardo:"+elapsed+" mls", 15, 570);
	            gp.drawString("puntos:"+playerpoints, 220, 40);
	            gp.drawString("uc:"+cascada, 220, 70);
	            gp.dispose();//draw in Buffered Image        
	            Graphics g=this.getGraphics();//draw in screen
	            g.drawImage(paint,0,0, this);   */                   
	    }
	    public void paintMainMenu(GameButton start){
	    	/*Paint p=new Paint();
	    	p.setColor(Color.WHITE);
	    	p.setTextSize(30);
	    	p.setTextAlign(Paint.Align.CENTER);
	    	tmpCanvas.drawText("Start Game", sWidth/2, sHeight/3,p );
	    	p.setStyle(Paint.Style.STROKE);
	    	tmpCanvas.drawRect(sWidth/3-20, sHeight/3-40,sWidth*2/3+20,sHeight/3+20,p );*/
	    	start.Draw(tmpCanvas);
	    	//TODO paint more things
	    }
	    public void paintpaused(){
	   /*     Graphics2D gp=(Graphics2D)paint.getGraphics();
	        //Graphics gp=this.getGraphics();
	        gp.clearRect(0,0,this.getWidth(),this.getHeight());
	        gp.setColor(Color.red);
	        gp.drawString("paused",100, 100);
	        gp.drawString("Presione 'P' para  seguir jugando",100, 150);
	        gp.drawString("Presione 'R' para Reiniciar el nivel",100, 200);
	        gp.drawString("Presione 'S' para salir del juego",100, 250);
	        gp.dispose();
	        Graphics g=this.getGraphics();//draw in screen
	        g.drawImage(paint,0,0, this);
*/
	    }
	    public void paintend(){
	   /*    Graphics2D gp=(Graphics2D)paint.getGraphics();
	         gp.drawImage((BufferedImage)cache.get("go"),0,0, this);//paint background
	         gp.setColor(Color.white);
	         gp.drawString("Final Points:"+playerpoints,140, 300);
	         gp.dispose();
	        Graphics g=this.getGraphics();
	        g.drawImage(paint,0,0, this);*/
	    } //paint game over and the final score   
	    public void paintLoading(){
	    	Paint p=new Paint();
	    	p.setColor(Color.BLACK);
	    	p.setTextSize(30);
	    	p.setTextAlign(Paint.Align.CENTER);
	    	tmpCanvas.drawText("Loading...", sWidth/2,sHeight/2, p);
	    }
}
