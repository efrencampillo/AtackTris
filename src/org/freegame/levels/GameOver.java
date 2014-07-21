
package org.freegame.levels;

import org.freegame.attacktis.AtackTris;
import org.freegame.game.GameSurface;
import org.freegame.models.GameButton;
import org.freegame.models.SceneActor;
import org.freegame.models.SceneBase;

import android.graphics.Canvas;

/**
 * @author kuno
 *
 */
public class GameOver extends SceneBase {

	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#loadScene()
	 */
	@Override
	public void loadScene() {
		GameButton restart=new GameButton(GameSurface.sWidth/3,GameSurface.sHeight/2,GameSurface.sWidth/3,50);
		restart.text="Restart Game";
		restart.setAction(new Runnable(){
			@Override
			public void run() {
				AtackTris.mthis.popSceneFromStack();
				GameLevel game=(GameLevel)AtackTris.mthis.getCurrentScene();
				game.restartGame();
			}});
		actors.add(restart);
		
		GameButton quitlevel=new GameButton(GameSurface.sWidth/3,GameSurface.sHeight*2/3,GameSurface.sWidth/3,50);
		quitlevel.text="Exit Game";
		quitlevel.setAction(new Runnable(){
			@Override
			public void run() {
				AtackTris.mthis.popSceneFromStack();
				AtackTris.mthis.popSceneFromStack();
			}});
		actors.add(quitlevel);
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
	 * @see org.freegame.models.SceneBase#destroyScene()
	 */
	@Override
	public void destroyScene() {
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
		
	}

	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#onKeyDown(int)
	 */
	@Override
	public void onKeyDown(int keydown) {
		// TODO Auto-generated method stub

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
