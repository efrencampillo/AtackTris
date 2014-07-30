
package org.freegame.levels;

import org.freegame.attacktis.AtackTris;
import org.freegame.game.GameButton;
import org.freegame.game.GameSurface;
import org.freegame.game.GameActor;
import org.freegame.game.GameSceneBase;

import android.graphics.Canvas;

/**
 * @author kuno
 *
 */
public class GameOver extends GameSceneBase {

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
		   /*  public void paintend(ArrayList<GameButton> buttons){
	       Graphics2D gp=(Graphics2D)paint.getGraphics();
	         gp.drawImage((BufferedImage)cache.get("go"),0,0, this);//paint background
	         gp.setColor(Color.white);
	         gp.drawString("Final Points:"+playerpoints,140, 300);
	         gp.dispose();
	        Graphics g=this.getGraphics();
	        g.drawImage(paint,0,0, this);
	    	for(GameButton btn:buttons){
	    		btn.Draw(tmpCanvas);
	    	}
	    } *///paint game over and the final score   
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
