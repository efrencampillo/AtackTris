
package org.freegame.models;

import java.util.ArrayList;

import org.freegame.attacktis.AtackTris;

import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * @author kuno
 *
 */
public abstract class SceneBase {
	protected ArrayList<GameButton> buttons;
	protected boolean isLoading=true;
	boolean isInitialized=false;
 	GestureDetector detector;
	public SceneBase(){
		buttons=new ArrayList<GameButton>();
		AtackTris.mthis.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				detector=new GestureDetector(AtackTris.mthis,new GestureDetector.SimpleOnGestureListener(){
		            @Override
		            public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
		            	onflingScene(event1,event2, velocityX,velocityY);
		                return true;
		            }
		            @Override
		            public boolean onSingleTapUp(MotionEvent e) {
		            	clicOn(e);
		            	return true;
		            }
		            @Override
		            public boolean onDown(MotionEvent e) {
		                  return true;
		            }
		        });
			}});      
	}
	public void updateScene(){
		if(isLoading)
			loadScene();
		else
			stepScene();
		
	}
	public abstract void loadScene();
	public abstract void stepScene();
	public abstract void destroyScene();
	protected abstract void paintLoading(Canvas c);
	protected abstract void drawScene(Canvas c);
	public void paintScene(Canvas c){
		if(isLoading){
			paintLoading(c);
		}else{
			drawScene(c);
		}
	}
	
	public abstract void onKeyDown(int keydown);
	public abstract void onPause();
	public abstract void onResume();
	public void clicOn(MotionEvent evt) {
		int cx=(int) evt.getX();
		int cy=(int) evt.getY();
		for(GameButton btn:buttons){
			if(cx>btn.x&&cx<(btn.w+btn.x)){
				if(cy>btn.y&&cy<(btn.h+btn.y)){
					btn.onclick();
				}
			}
		}
	}
	protected void onflingScene(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY){};
	public void touchEvent(MotionEvent event){
		detector.onTouchEvent(event);
	}
}
