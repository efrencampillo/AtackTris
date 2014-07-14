package org.freegame.attacktis;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.freegame.levels.GameLevel;
import org.freegame.levels.MainMenu;
import org.freegame.models.GameButton;
import org.freegame.models.SceneBase;
import org.freegame.models.block;
import org.freegame.models.cursor;


import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class AtackTris extends Activity {
   
    public static MySurface mrender;
    GestureDetector detector;
    MotionEvent last_click;
    ArrayList<SceneBase> scenes;
    public static AtackTris mthis;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mthis=this;
		mrender=new MySurface(this);
		scenes= new ArrayList<SceneBase>();
		setContentView(mrender);
    	detector=new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            	//if(game_status==1)mrender.fling(event1,event2, velocityX,velocityY);
                return true;
            }
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
            	last_click=e;
            	return true;
            }
            @Override
            public boolean onDown(MotionEvent e) {
                  return true;
            }
        });
	}
  
    public void mainloop(Canvas c){ //main loop of the game painter
    	SceneBase currentScene=getCurrentScene();    	
    	if(last_click!=null){
    		currentScene.clicOn(last_click);
    		last_click=null;
    	}    	
    	currentScene.updateScene();
    	currentScene.paintScene(c);
    }
    
   
   
    /* (non-Javadoc)
     * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	detector.onTouchEvent(event);
    	return super.onTouchEvent(event);
    }    
    
   /**
    * this method return the actual level
    * */
	public SceneBase getCurrentScene(){
		if(scenes.size()>0){
	  		return scenes.get(scenes.size()-1);
	  	}else{
	  		SceneBase mainmenu=new MainMenu();
	  		scenes.add(mainmenu);
	  		return mainmenu;
	  	}
	}
	 /* (non-Javadoc)
     * @see android.app.Activity#onBackPressed()
     */ 
    @Override
    public void onBackPressed() {
    	SceneBase currScene=getCurrentScene();
    	currScene.onKeyDown(KeyEvent.KEYCODE_BACK);
    }
    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
    	SceneBase currScene=getCurrentScene();
    	currScene.onPause();
    	super.onPause();
    }
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
    	SceneBase currScene=getCurrentScene();
    	currScene.onResume();
    	super.onResume();
    }
    public void startGame(){
    	GameLevel game=new GameLevel();
    	scenes.add(game);
    }
    public void AddSceneToStack(SceneBase scene){
    	scenes.add(scene);
    }
    public void popSceneFromStack(){
    	if(scenes.size()>1){
    		scenes.remove(scenes.size()-1);
    	}
    }
}
