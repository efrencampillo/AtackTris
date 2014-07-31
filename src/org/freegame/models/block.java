package org.freegame.models;

import org.freegame.game.GameActor;
import org.freegame.levels.GameLevel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


/**  
 * class Block
 * this class has the behaviour to down every turn
 * */
public class Block extends GameActor{

    public boolean isStatic=false;
    public boolean arrived=false;// blocstat 0=static,1=falling
    public int color;
    public int dir=1; //number of direction to find figures
    GameLevel game;
/**
 * construct for be falling
 * */
    public Block(int C,GameLevel g,int blocksize){
    	super(0,0,blocksize,blocksize);
        color=C;
        isStatic=false;
        game=g;
        arrived=false;
    }
    /*
     * construct for Block downing
     * */
    public Block(GameLevel g){
    	super(0,0,0,0);
        color=6;
        isStatic=true;
        game=g;
        arrived=true;
                
    }
    /**
     * Move down for a Block
     */
    public void down(int x,int y){  ///
            if(game.table[x][y+1]==null){ /// if can fall
                arrived=false;
                Block tmp=game.table[x][y];
                game.table[x][y]=null;
                if(y<15)y++;
                game.table[x][y]=tmp;
            }else{ //if cannot fall
            	arrived=true;
               /* if(isStatic){
                    stat=false;
                    if(y==0)game.lose();///this line mark if the bloc  stops at the top for loose
                    else{
                       if(!game.lineexplode(y)){//this line check if there figures around
                            game.checfigure1(this,0);
                            //
                       }
                   }
                    arrived=true;
                }*/
            }

    }
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneActor#onClick()
	 */
	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneActor#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas c) {
		Paint p=new Paint();	
		p.setStyle(Paint.Style.FILL);
		p.setColor(color);
		c.drawRect(x,y,x+width ,y+height, p);
		p.setStyle(Paint.Style.STROKE);
		p.setColor(Color.BLACK); 
		p.setStrokeWidth(2);
		c.drawRect(x,y,x+width ,y+height, p);
	}
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneActor#update()
	 */
	@Override
	public void update() {}
}
