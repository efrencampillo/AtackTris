
package org.freegame.levels;

import org.freegame.attacktis.AtackTris;
import org.freegame.game.GameSurface;
import org.freegame.models.GameButton;
import org.freegame.models.SceneBase;

import android.graphics.Canvas;
import android.view.KeyEvent;

/**
 * @author kuno
 *
 */
public class GameLevelPaused extends SceneBase {

	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#loadScene()
	 */
	@Override
	public void loadScene() {
		GameButton resume=new GameButton(GameSurface.sWidth/3,GameSurface.sHeight/3,GameSurface.sWidth/3,50);
		resume.text="Resume Game";
		resume.setAction(new Runnable(){
			@Override
			public void run() {
				AtackTris.mthis.popSceneFromStack();
			}});
		buttons.add(resume);	
		GameButton restart=new GameButton(GameSurface.sWidth/3,GameSurface.sHeight/2,GameSurface.sWidth/3,50);
		restart.text="Restart Game";
		restart.setAction(new Runnable(){
			@Override
			public void run() {
				AtackTris.mthis.popSceneFromStack();
				GameLevel game=(GameLevel)AtackTris.mthis.getCurrentScene();
				game.restartGame();
			}});
		buttons.add(restart);
		
		GameButton quitlevel=new GameButton(GameSurface.sWidth/3,GameSurface.sHeight*2/3,GameSurface.sWidth/3,50);
		quitlevel.text="Exit Game";
		quitlevel.setAction(new Runnable(){
			@Override
			public void run() {
				AtackTris.mthis.popSceneFromStack();
				AtackTris.mthis.popSceneFromStack();
			}});
		buttons.add(quitlevel);
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
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#drawScene(android.graphics.Canvas)
	 */
	@Override
	protected void drawScene(Canvas c) {
		for(GameButton btn:buttons)btn.Draw(c);
	}

	

	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#destroyScene()
	 */
	@Override
	public void destroyScene() {
		// TODO Auto-generated method stub

	}

	
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#onKeyDown(int)
	 */
	@Override
	public void onKeyDown(int keydown) {
		if(keydown==KeyEvent.KEYCODE_BACK){
			//TODO pop scene
		}
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

}
