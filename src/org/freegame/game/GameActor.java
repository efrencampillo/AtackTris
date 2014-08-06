
package org.freegame.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * @author kuno
 *
 */
public abstract class GameActor {
	public int x;
	public int y;
	public int width;
	public int height;
	public Bitmap ActualTexture=null;
	public boolean isVisible=true;
	public GameActor(int px, int py, int lwidht, int lheight){
		x=px;y=py;
		width=lwidht;
		height=lheight;
	}
	public abstract void onClick();
	public void draw(Canvas c){
		if(isVisible)draw(c);
	}
	protected abstract void drawActor(Canvas c);
	public abstract void update();
	
}
