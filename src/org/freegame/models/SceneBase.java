
package org.freegame.models;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * @author kuno
 *
 */
public abstract class SceneBase {
	protected ArrayList<GameButton> buttons;
	protected boolean isLoading=true;
	boolean isInitialized=false;
	public SceneBase(){
		buttons=new ArrayList<GameButton>();
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
}
