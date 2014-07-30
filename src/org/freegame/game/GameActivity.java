
package org.freegame.game;

import java.util.ArrayList;

import org.freegame.attacktis.AtackTris;
import org.freegame.levels.MainMenu;
import org.freegame.utils.ImageManager;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;

/**
 * @author kuno
 *
 */
public class GameActivity extends Activity {
	public GameSurface mrender;
	protected ArrayList<GameSceneBase> scenes;
	public static GameActivity mthis;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mrender=new GameSurface(this);
		scenes= new ArrayList<GameSceneBase>();
		setContentView(mrender);
		mthis=this;
		ImageManager.Initialize(this);
	}
	/* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
    	GameSceneBase currScene=getCurrentScene();
    	currScene.onPause();
    	super.onPause();
    }
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
    	GameSceneBase currScene=getCurrentScene();
    	currScene.onResume();
    	super.onResume();
    }
    /**
     * this method return the actual level
     * */
 	public GameSceneBase getCurrentScene(){
 		if(scenes.size()>0){
 	  		return scenes.get(scenes.size()-1);
 	  	}else{
 	  		GameSceneBase mainmenu=new MainMenu();
 	  		scenes.add(mainmenu);
 	  		return mainmenu;
 	  	}
 	}
 	/**
 	 * this method put the scene to the stack of scenes
 	 * */
 	public void AddSceneToStack(GameSceneBase scene){
 		scene.deepIndex=scenes.size();
    	scenes.add(scene);
    }
 	/**
 	 * this method remove the last scene from the stack if is not the last one
 	 */
    public void popSceneFromStack(){
    	if(scenes.size()>1)scenes.remove(scenes.size()-1);
    }
    public GameSceneBase getScene(int index){
    	if(index<=scenes.size())
    		return scenes.get(index);
    	return null;
    }
    
    /**
     * this method os called by the gamesurface to update the game
     * */
    public void mainloop(Canvas c){ //main loop of the game painter
    	GameSceneBase currentScene=getCurrentScene();    	  	
    	currentScene.updateScene();
    	currentScene.paintScene(c);
    }
}
