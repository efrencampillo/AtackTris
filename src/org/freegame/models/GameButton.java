
package org.freegame.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * @author kuno
 *
 */
public class GameButton {
	public int x,y,w,h;///positions on screen
	Runnable action=null;//action to do
	public String text="";//text to show
	public int textSize=25;
	public int ref_level=0;
	public GameButton(int px,int py, int lx/*length in x*/,int ly/*length in y*/){
		x=px;y=py;w=lx;h=ly;
	}
	/**
	 * this method executes the action of the buttons if has
	 * */
	public void onclick(){
		if(action!=null)new Thread(action).start();
	}
	/**
	 * this method is to set th button action
	 * */
	public void setAction(Runnable newaction){
		action=newaction;
	}
	/**
	 * this method drwas the button
	 * */
	public void Draw(Canvas c){
		Paint p=new Paint();
		//TODO fix colors and put style
		//filled
		p.setColor(Color.WHITE);
		p.setStyle(Paint.Style.FILL);
		c.drawRect(x,y,x+w,y+h,p);
		//border
		p.setColor(Color.BLACK);
		p.setStyle(Paint.Style.STROKE);
		p.setStrokeWidth(3);
		c.drawRect(x,y,x+w,y+h,p);
		
		//p.setColor(Color.RED);
		p.setStyle(Paint.Style.FILL);
		p.setTextSize(textSize);
		p.setTextAlign(Paint.Align.CENTER);
		c.drawText(""+text, x+w/2, y+h/2+(textSize/2), p);
	}
}
