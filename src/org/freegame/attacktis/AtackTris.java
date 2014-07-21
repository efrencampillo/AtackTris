package org.freegame.attacktis;



import org.freegame.game.GameActivity;
import org.freegame.models.SceneBase;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class AtackTris extends GameActivity {
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
  
    /* (non-Javadoc)
     * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	SceneBase currentScene=getCurrentScene();    
    	currentScene.touchEvent(event);
    	return super.onTouchEvent(event);
    }    
    
   
	 /* (non-Javadoc)
     * @see android.app.Activity#onBackPressed()
     */ 
    @Override
    public void onBackPressed() {
    	SceneBase currScene=getCurrentScene();
    	currScene.onKeyDown(KeyEvent.KEYCODE_BACK);
    }
    
}
