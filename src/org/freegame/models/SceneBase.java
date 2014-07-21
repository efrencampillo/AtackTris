
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
	protected ArrayList<SceneActor> actors;
	protected boolean isLoading=true;
	boolean isInitialized=false;
 	GestureDetector detector;
	public SceneBase(){
		actors=new ArrayList<SceneActor>();
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
		else{
			for(SceneActor act:actors)act.update();
			stepScene();
		}
			
		
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
			for(SceneActor act:actors)act.draw(c);
		}
	}
	
	public abstract void onKeyDown(int keydown);
	public abstract void onPause();
	public abstract void onResume();
	public void clicOn(MotionEvent evt) {
		int cx=(int) evt.getX();
		int cy=(int) evt.getY();
		for(SceneActor btn:actors){
			if(cx>btn.x&&cx<(btn.width+btn.x)){
				if(cy>btn.y&&cy<(btn.height+btn.y)){
					btn.onClick();
				}
			}
		}
	}
	protected void onflingScene(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY){};
	public void touchEvent(MotionEvent event){
		detector.onTouchEvent(event);
	}
}
