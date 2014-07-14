
package org.freegame.levels;

import java.util.ArrayList;
import java.util.Random;

import org.freegame.attacktis.AtackTris;
import org.freegame.attacktis.MySurface;
import org.freegame.models.SceneBase;
import org.freegame.models.block;
import org.freegame.models.cursor;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * @author kuno
 *
 */
public class GameLevel extends SceneBase {

	public ArrayList<block> blocs;
    public ArrayList<block> figure;
    public ArrayList<block> casc=new ArrayList<block>();
    public block[][] table;//the grid for the blocs in the game
    public cursor point;
    public Random r;
    public long move,init,time,paused,ttime,linextra;
    public long btime=120000;//couter timer of the game
    public int playerpoints=0,cascada=0;
    public int tmove=300;  //time to move blocks, this will decreasing with time
    public boolean flagc=false;
    
  //block option
  	public int num_blocks=6;
  	public int block_size;
  	public int block_height;
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#loadScene()
	 */
	@Override
	public void loadScene() {
		block_size=MySurface.sWidth/num_blocks;
		block_height=MySurface.sHeight/block_size;
		linextra=System.currentTimeMillis();
		ttime=System.currentTimeMillis();
        blocs=new ArrayList<block>();
        figure=new ArrayList<block>();
        createinitialtable();
        r=new Random();
        isLoading=false;
	}

	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#stepScene()
	 */
	@Override
	public void stepScene() {
		 genblock();
         movebloc();
	}
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#paintLoading(android.graphics.Canvas)
	 */
	@Override
	protected void paintLoading(Canvas c) {
		Paint p=new Paint();
    	p.setColor(Color.BLACK);
    	p.setTextSize(30);
    	p.setTextAlign(Paint.Align.CENTER);
    	c.drawText("Loading...", MySurface.sWidth/2,MySurface.sHeight/2, p);
	}
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#drawScene(android.graphics.Canvas)
	 */
	@Override
	protected void drawScene(Canvas c) {		
		  Paint p=new Paint();	  
		  for(int i=1;i<num_blocks+1;i++){
			  for(int j=block_height-1;j>=0;j--){
				 if(table[i][j]!=null&&table[i][j].color!=6){
					 p.setStyle(Paint.Style.FILL);
					 p.setColor(table[i][j].color);
					 c.drawRect((i-1)*block_size, (j+1)*block_size,(i)*block_size ,j*block_size, p);
					 p.setStyle(Paint.Style.STROKE);
					 p.setColor(Color.BLACK); 
					 p.setStrokeWidth(2);
					 c.drawRect((i-1)*block_size, (j+1)*block_size,(i)*block_size ,j*block_size, p);
					
				 }
			  }
		  }
	           
	     /*      
	            if(!point.status)gp.drawImage((BufferedImage)cache.get("cursor"),(point.x-1)*33+10,point.y*33+20, this);//paint cursor
	            else gp.drawImage((BufferedImage)cache.get("cursors"),(point.x-1)*33+10,point.y*33+20, this);
	            gp.drawRect(5,10, 210, 545);
	            gp.drawString("Seconds remaining="+(int)((btime-time)/1000),100,570);
	            long elapsed = System.currentTimeMillis() - start;// total time elpased for create the image
	            gp.drawString("tardo:"+elapsed+" mls", 15, 570);
	            gp.drawString("puntos:"+playerpoints, 220, 40);
	            gp.drawString("uc:"+cascada, 220, 70);
	            gp.dispose();//draw in Buffered Image        
	            Graphics g=this.getGraphics();//draw in screen
	            g.drawImage(paint,0,0, this);   */     
		  time=System.currentTimeMillis()-init;
          if(time>btime){
              tmove-=50;//increasing speed of falling blocks
              if(tmove<60)tmove=60;
              btime-=2000;
              if(btime<0){btime=500;}
              init=System.currentTimeMillis();
          }
	  
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
			onPause();
		}
	}
	 /**
     * this method generate the blocs at the begining
     * */
    private void createinitialtable(){
        table=new block[num_blocks+2][block_height+1];
      //this line to creates a invisible floor
        for(int i=0;i<num_blocks+2;i++)table[i][block_height]=new block(this);
        // this line creates a invisible wall at left
        for(int i=0;i<block_height+1;i++)table[0][i]=new block(this);
        // this line creates a invisible wall at righs
        for(int i=0;i<block_height+1;i++)table[num_blocks+1][i]=new block(this);
        move=System.currentTimeMillis();
        Log.i("aacktris", "initial table created");
    }
    /**
     * metod for create blocs when no one is falling
     * */
    private void genblock() {
        if(checbloc()){
            int nx=r.nextInt(num_blocks-1)+1; //create coordenate x:range(1:5)
            int nc=getColorWithRandom(r.nextInt(5)+1); //create color
            table[nx][0]=new block(nc,this);
            table[nx][0].color=nc;
            nc=getColorWithRandom(r.nextInt(5)+1);
            table[nx+1][0]=new block(nc,this);
            table[nx+1][0].color=nc;
        }
     }
     public void genbloctime(){
         int nx=r.nextInt(num_blocks-1)+1;
         int nc=getColorWithRandom(r.nextInt(5)+1);
         table[nx][0]=new block(nc,this);
         table[nx][0].color=nc;
         nc=getColorWithRandom(r.nextInt(5)+1);
         table[nx+1][0]=new block(nc,this);
         table[nx+1][0].color=nc;
     }
     private boolean checbloc(){///check if all the blocs are static
        for(int i=1;i<num_blocks+1;i++){
			  for(int j=block_height-1;j>=0;j--){  
				 if(table[i][j]!=null&&!table[i][j].isStatic&&!table[i][j].arrived){
					 return false;
				 }
			}
		} 
        return true;
     }
    private void movebloc(){
    	if(System.currentTimeMillis()-move>tmove){
    		for(int i=1;i<num_blocks+1;i++){
	    		for(int j=block_height-1;j>=0;j--){
	    			if(table[i][j]!=null)table[i][j].down(i, j);
				}
	    	}
	    	move=System.currentTimeMillis();
    	}
     }
    /**
     * this method change the status of the machine state to state loose
     * */
    public void lose(){
        long timescore=System.currentTimeMillis()-ttime;
        playerpoints+=(int)timescore/60000;
    } 
    /**
     * this method restart the current play
     * */
    public void restartGame(){
        playerpoints=0;
        tmove=300;
        btime=120000;
        createinitialtable();
    }
    /**
     * this method remove the sent block
     * */
    public void remove(int x, int y){//remover blocs
       table[x][y]=null;
    }
    /**
     *  metod for find tetris figures has recursivity
     * */
     public void checfigure1(int x,int y){
       
        figure.add(table[x][y]);
       // if((figure.size()>3)){return;}
        if(table[x][y-1]!=null)
        if(table[x][y-1].color==table[x][y].color && !figure.contains(table[x][y-1])){ ///chek up blocks
          //  dir--;         
            checfigure1(x,y-1);
        }
        if(table[x+1][y]!=null)
        if(table[x+1][y].color==table[x][y].color && !figure.contains(table[x+1][y])){//chek right blocks
       //     dir--;
            checfigure1(x+1,y);
        }
        if(table[x][y+1]!=null)
        if(table[x][y+1].color==table[x][y].color && !figure.contains(table[x][y+1])){///check down block
           // dir--;                  
            checfigure1(x,y+1);
        }
        if(table[x-1][y]!=null)
        if(table[x-1][y].color==table[x][y].color && !figure.contains(table[x-1][y])){///check Left block
        //    dir--;                        
            checfigure1(x-1,y);
        }  
        if(figure.size()>3){
           removefigure(); 
       }else{
           figure=new ArrayList<block>();
           
       }
   }
   public void removefigure(){
         /*   boolean bloccasc=false;
            for(int i=0;i<figure.size();i++){
                block b=figure.get(i);
                block d=get(b.x,b.y-1);
                if(casc.contains(b)){casc.remove(b);bloccasc=true;}
                if(d!=null&&!figure.contains(d)&&!casc.contains(d))
                    casc.add(d);
                	remove(b);
    	        }
    	        if(bloccasc)cascada++;
    	        else cascada=0;
    	        figure=new ArrayList<block>();
    	        playerpoints++;*/
  	} //remove blocks from figures created
      
    public void change(int bx,int by,int px,int py){   //change bolcs position
        /*    block a=null;
            block n=null;
            for(int i=0;i<blocs.size();i++){
                block b=blocs.get(i);
                if(b.x==bx&&b.y==by){a=b;}
                if(b.x==px&&b.y==py){n=b;}
            }
            int c=a.color;
            a.color=n.color;
            n.color=c;
            table[a.x][a.y].color=a.color;
            table[n.x][n.y].color=n.color;
            if(!this.lineexplode(a.y)){checfigure1(a,0);}
            if(!this.lineexplode(n.y)){checfigure1(n,0);}*/
    }//change blocs position
    
    /**
	 * fling event to move
	 * */
	public void fling(MotionEvent event1,MotionEvent event2, float velx, float vely){
		if(Math.abs(velx)>Math.abs(vely)){///horizontal fling
			if(velx<0){//TO left fling
				Log.i("fling", "left");
			}else{//TO right fling
				Log.i("fling", "right");
			}
		}else{///vertical fling
			if(vely<0){
				Log.i("fling", "up");
			}else{
				Log.i("fling", "down");
			}
		}
	}
	 /**
     * method to add line of blocks if pass any time
     * */
  /*  private void addline(){
        for(int i=0;i<blocs.size();i++){
            block b=blocs.get(i);
            if(!b.stat){
                b.y--;
                table[b.x][b.y].color=b.color;
            }
        }   
        for(int i=1;i<7;i++){
            int c=getColorWithRandom(r.nextInt(5)+1);
            block b=new block(i,15,c,this);
            b.arrived=true;
            blocs.add(b);
            table[i][15].color=c;
        }
	}*/
	 /**
     * this method return colors
     * //1 red,2 blue,3 pink,4 green,5 yellow
     * */
    public int getColorWithRandom(int number){
    	switch(number){
    	case 1:
    		return 0xffff0000;
    	case 2:
    		return 0xff0000ff;
    	case 3:
    		return 0xffff00ff;
    	case 4:
    		return 0xff00ff00;
    	case 5:
    		return 0xffffff00;
    	default:
    		return 0xff000000;
    	}
    }

	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#onPause()
	 */
	@Override
	public void onPause() {
		PausedGame paused=new PausedGame();
		AtackTris.mthis.AddSceneToStack(paused);
	}

	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
	}
}
