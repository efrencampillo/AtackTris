
package org.freegame.levels;

import java.util.ArrayList;

import org.freegame.attacktis.AtackTris;
import org.freegame.game.GameActivity;
import org.freegame.game.GameSurface;
import org.freegame.models.GameButton;
import org.freegame.models.SceneBase;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * @author kuno
 *
 */
public class MainMenu extends SceneBase {

	
	/**
	 * builder
	 * 
	 * call the super to create arrays
	 * */
	public MainMenu(){
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#loadScene()
	 */
	@Override
	public void loadScene() {
		// TODO add buttons
		int width=GameSurface.sWidth;
		int height=GameSurface.sHeight;
		GameButton start=new GameButton(width/3,height/3,width/3,50);
 		start.text="Start Game";
 		start.setAction(new Runnable(){
				@Override
				public void run() {
					startGame();
				}});
 		buttons.add(start);
 		
 		GameButton quit=new GameButton(width/3,height*2/3,width/3,50);
 		quit.text="Exit";
 		quit.setAction(new Runnable(){
				@Override
				public void run() {
					System.exit(0);
				}});
 		buttons.add(quit);
 		isLoading=false;
	}
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#stepScene()
	 */
	@Override
	public void stepScene() {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#paintLoading(android.graphics.Canvas)
	 */
	@Override
	protected void paintLoading(Canvas c) {
		// TODO paint level while is loading
		
	}
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#drawScene(android.graphics.Canvas)
	 */
	@Override
	public void drawScene(Canvas c) {
		/*Paint p=new Paint();
    	p.setColor(Color.WHITE);
    	p.setTextSize(30);
    	p.setTextAlign(Paint.Align.CENTER);
    	tmpCanvas.drawText("Start Game", sWidth/2, sHeight/3,p );
    	p.setStyle(Paint.Style.STROKE);
    	tmpCanvas.drawRect(sWidth/3-20, sHeight/3-40,sWidth*2/3+20,sHeight/3+20,p );*/
    	for(GameButton btn:buttons){
    		btn.Draw(c);
    	}
    	//TODO paint more things
	}

	
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#destroyScene()
	 */
	@Override
	public void destroyScene() {
		// TODO destroy all

	}

	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#onKeyDown(int)
	 */
	@Override
	public void onKeyDown(int keydown) {
		System.exit(0);
	}

	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
	}
	public void startGame(){
    	GameLevel game=new GameLevel();
    	GameActivity.mthis.AddSceneToStack(game);
    }
}
