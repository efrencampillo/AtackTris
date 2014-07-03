package org.freegame.models;

import org.freegame.attacktis.AtackTris;

public class cursor {
    int x,y;//position of the cursor
    int sx,sy;
    boolean status=false;   //false to move  ;  true for change
    AtackTris game;
    block ini,fin;
    
    /**
     * contructor of the class
     * */
    public cursor( AtackTris g){
        x=3;y=11;
        game=g;
    }
    /**
     * this method chance the references from the blocks
     * */
    public void blocchange(){
      /*  if(game.table[x][y]!=null){
            for(int i=0;i<game.blocs.size();i++){
                block b=game.blocs.get(i);
                if(b.x==x&&b.y==y){
                    fin=b;
                    status=false;
                    break;
                }
            }
            game.change(ini.x, ini.y,fin.x,fin.y);
            ini=null;fin=null;
        }
       */
    }
    /**
     * move up
     * */
    public void up(){
        if(!status){
           y--;
           restric();
        }else{
           y--;
           restric();
        }
    }
    /**
     * move down
     * */
    public void down(){
        if(!status){
           y++;
           restric();
        }else{
            y++;
           restric();
        }
    }
    /**
     * move left
     * */
    public void left(){
        if(!status){
           x--;
           restric();
       }else{
            x--;
           restric();
        }
    }
    /**
     * move right
     * */
    public void rigth(){
        if(!status){
           x++;
           restric();
        }else{
             x++;
           restric();
        }
    }
    
    /**
     * selects the actual block
     * */
    public void select(){
      /*  if(game.table[x][y]!=null){
            for(int i=0;i<game.blocs.size();i++){
                block b=game.blocs.get(i);
                if(b.x==x&&b.y==y){
                    status=true;
                    ini=b;
                    break;
                }
                
            }
        }*/
    }
    public void restric(){
        if(x>6)x=6;
        if(x<1)x=1;
        if(y<0)y=0;
        if(y>15)y=15;
    }
}
