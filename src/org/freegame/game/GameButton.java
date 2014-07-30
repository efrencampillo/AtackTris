
package org.freegame.game;

import org.freegame.attacktis.AtackTris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * @author kuno
 *
 */
public class GameButton extends GameActor {
	Runnable action=null;//action to do
	public String text="";//text to show
	public int textSize=25;
	public GameButton(int px,int py, int lx/*length in x*/,int ly/*length in y*/){
		super(px,py,lx,ly);	
		DisplayMetrics metrics = AtackTris.mthis.getResources().getDisplayMetrics();
		float density=metrics.density;
		height*=density;
	}
	
	/**
	 * this method is to set th button action
	 * */
	public void setAction(Runnable newaction){
		action=newaction;
	}
	

	/* (non-Javadoc)
	 * @see org.freegame.models.SceneActor#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas c) {
		Paint p=new Paint();
		//TODO fix colors and put style
		//filled
		p.setColor(Color.WHITE);
		p.setStyle(Paint.Style.FILL);
		c.drawRect(x,y,x+width,y+height,p);
		//border
		p.setColor(Color.BLACK);
		p.setStyle(Paint.Style.STROKE);
		p.setStrokeWidth(3);
		c.drawRect(x,y,x+width,y+height,p);
		
		//p.setColor(Color.RED);
		p.setStyle(Paint.Style.FILL);
		p.setTextSize(textSize);
		p.setTextAlign(Paint.Align.CENTER);
		c.drawText(""+text, x+width/2, y+height/2+(textSize/2), p);
	}
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneActor#update()
	 */
	@Override
	public void update() {}
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneActor#onClick()
	 */
	@Override
	public void onClick() {
		if(action!=null)new Thread(action).start();
	}
}
