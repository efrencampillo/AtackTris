
package org.freegame.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * @author kuno
 *
 */
public abstract class SceneActor {
	public int x;
	public int y;
	public int width;
	public int height;
	public Bitmap ActualTexture=null;
	public SceneActor(int px, int py, int lwidht, int lheight){
		x=px;y=py;
		width=lwidht;
		height=lheight;
	}
	public abstract void onClick();
	public abstract void draw(Canvas c);
	public abstract void update();
	
}
