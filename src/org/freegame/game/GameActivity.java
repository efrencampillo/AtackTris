
package org.freegame.game;

import java.util.ArrayList;

import org.freegame.attacktis.AtackTris;
import org.freegame.levels.MainMenu;
import org.freegame.models.SceneBase;
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
	protected ArrayList<SceneBase> scenes;
	public static GameActivity mthis;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mrender=new GameSurface(this);
		scenes= new ArrayList<SceneBase>();
		setContentView(mrender);
		mthis=this;
		ImageManager.Initialize(this);
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
 	/**
 	 * this method put the scene to the stack of scenes
 	 * */
 	public void AddSceneToStack(SceneBase scene){
 		scene.deepIndex=scenes.size();
    	scenes.add(scene);
    }
 	/**
 	 * this method remove the last scene from the stack if is not the last one
 	 */
    public void popSceneFromStack(){
    	if(scenes.size()>1)scenes.remove(scenes.size()-1);
    }
    public SceneBase getScene(int index){
    	if(index<=scenes.size())
    		return scenes.get(index);
    	return null;
    }
    
    /**
     * this method os called by the gamesurface to update the game
     * */
    public void mainloop(Canvas c){ //main loop of the game painter
    	SceneBase currentScene=getCurrentScene();    	  	
    	currentScene.updateScene();
    	currentScene.paintScene(c);
    }
}
