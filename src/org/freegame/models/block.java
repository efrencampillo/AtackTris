package org.freegame.models;

import org.freegame.attacktis.AtackTris;
import org.freegame.levels.GameLevel;


/**
 * class Block
 * this class has the behaviour to down every turn
 * */
public class Block {

    public boolean isStatic=false;
    public boolean arrived=false;// blocstat 0=static,1=falling
    public int color;
    public int dir=1; //number of direction to find figures
    GameLevel game;
/**
 * construct for be falling
 * */
    public Block(int C,GameLevel g){
        color=C;
        isStatic=false;
        game=g;
        arrived=false;
    }
    /*
     * construct for Block downing
     * */
    public Block(GameLevel g){
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
}
