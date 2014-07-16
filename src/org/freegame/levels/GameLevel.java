
package org.freegame.levels;

import java.util.ArrayList;
import java.util.Random;

import org.freegame.attacktis.AtackTris;
import org.freegame.game.GameSurface;
import org.freegame.models.SceneBase;
import org.freegame.models.Block;

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

	public ArrayList<Block> blocs;
    public ArrayList<Block> figure;
    public ArrayList<Block> casc=new ArrayList<Block>();
    public Block[][] table;//the grid for the blocs in the game

    public Random r;
    public long move,init,time,paused,ttime,linextra;
    public long btime=120000;//couter timer of the game
    public int playerpoints=0,cascada=0;
    public int tmove=300;  //time to move blocks, this will decreasing with time
    public boolean flagc=false;
    
  //Block option
  	public int num_blocks=6;
  	public int block_size;
  	public int num_block_vert;
  	
 
  	
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#loadScene()
	 */
	@Override
	public void loadScene() {
		block_size=GameSurface.sWidth/num_blocks;
		num_block_vert=GameSurface.sHeight/block_size;
		linextra=System.currentTimeMillis();
		ttime=System.currentTimeMillis();
        blocs=new ArrayList<Block>();
        figure=new ArrayList<Block>();
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
    	c.drawText("Loading...", GameSurface.sWidth/2,GameSurface.sHeight/2, p);
	}
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#drawScene(android.graphics.Canvas)
	 */
	@Override
	protected void drawScene(Canvas c) {		
		  Paint p=new Paint();	  
		  for(int i=1;i<num_blocks+1;i++){
			  for(int j=num_block_vert-1;j>=0;j--){
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
        table=new Block[num_blocks+2][num_block_vert+1];
      //this line to creates a invisible floor
        for(int i=0;i<num_blocks+2;i++)table[i][num_block_vert]=new Block(this);
        // this line creates a invisible wall at left
        for(int i=0;i<num_block_vert+1;i++)table[0][i]=new Block(this);
        // this line creates a invisible wall at righs
        for(int i=0;i<num_block_vert+1;i++)table[num_blocks+1][i]=new Block(this);
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
            table[nx][0]=new Block(nc,this);
            table[nx][0].color=nc;
            nc=getColorWithRandom(r.nextInt(5)+1);
            table[nx+1][0]=new Block(nc,this);
            table[nx+1][0].color=nc;
        }
     }
     public void genbloctime(){
         int nx=r.nextInt(num_blocks-1)+1;
         int nc=getColorWithRandom(r.nextInt(5)+1);
         table[nx][0]=new Block(nc,this);
         table[nx][0].color=nc;
         nc=getColorWithRandom(r.nextInt(5)+1);
         table[nx+1][0]=new Block(nc,this);
         table[nx+1][0].color=nc;
     }
     private boolean checbloc(){///check if all the blocs are static
        for(int i=1;i<num_blocks+1;i++){
			  for(int j=num_block_vert-1;j>=0;j--){  
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
	    		for(int j=num_block_vert-1;j>=0;j--){
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
     * this method remove the sent Block
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
        if(table[x][y+1].color==table[x][y].color && !figure.contains(table[x][y+1])){///check down Block
           // dir--;                  
            checfigure1(x,y+1);
        }
        if(table[x-1][y]!=null)
        if(table[x-1][y].color==table[x][y].color && !figure.contains(table[x-1][y])){///check Left Block
        //    dir--;                        
            checfigure1(x-1,y);
        }  
        if(figure.size()>3){
           removefigure(); 
       }else{
           figure=new ArrayList<Block>();
           
       }
   }
   public void removefigure(){
         /*   boolean bloccasc=false;
            for(int i=0;i<figure.size();i++){
                Block b=figure.get(i);
                Block d=get(b.x,b.y-1);
                if(casc.contains(b)){casc.remove(b);bloccasc=true;}
                if(d!=null&&!figure.contains(d)&&!casc.contains(d))
                    casc.add(d);
                	remove(b);
    	        }
    	        if(bloccasc)cascada++;
    	        else cascada=0;
    	        figure=new ArrayList<Block>();
    	        playerpoints++;*/
  	} //remove blocks from figures created
      
    public void change(int bx,int by,int px,int py){   //change bolcs position
        /*    Block a=null;
            Block n=null;
            for(int i=0;i<blocs.size();i++){
                Block b=blocs.get(i);
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
     * method to add line of blocks if pass any time
     * */
  /*  private void addline(){
        for(int i=0;i<blocs.size();i++){
            Block b=blocs.get(i);
            if(!b.stat){
                b.y--;
                table[b.x][b.y].color=b.color;
            }
        }   
        for(int i=1;i<7;i++){
            int c=getColorWithRandom(r.nextInt(5)+1);
            Block b=new Block(i,15,c,this);
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
		GameLevelPaused paused=new GameLevelPaused();
		AtackTris.mthis.AddSceneToStack(paused);
	}

	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.freegame.models.SceneBase#onflingScene(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	protected void onflingScene(MotionEvent event1, MotionEvent event2,
			float velocityX, float velocityY) {
		int px=(int)event1.getRawX();
		int py=(int)event1.getRawY();
		int indexX= (int)(px+block_size)/block_size;
		int indexY= (int)(py+block_size)/block_size-1;
		
		if(Math.abs(velocityX)>Math.abs(velocityY)){///horizontal fling
			if(velocityX<0){//TO left fling
				moveBlockTo(indexX,indexY,0);
			}else{//TO right fling
				moveBlockTo(indexX,indexY,2);
			}
		}else{///vertical fling
			if(velocityY<0){
				moveBlockTo(indexX,indexY,1);
			}else{
				moveBlockTo(indexX,indexY,3);
			}
		}
		//TODO put behavoiur here
	}
	
	private void moveBlockTo(int x, int y, int dir){
		Log.i("touched", "index:"+x+","+y+ " dir "+dir);
		Block changeblock=table[x][y];
		if(changeblock==null)return;
		switch(dir){
		case 0:
			if(x<2)return;
			if(table[x-1][y]==null)return;
			table[x][y]=table[x-1][y];
			table[x-1][y]=changeblock;
			break;
		case 1:
			if(y<2)return;
			if(table[x][y-1]==null)return;
			table[x][y]=table[x][y-1];
			table[x][y-1]=changeblock;
			break;
		case 2:
			if(x>num_blocks-1)return;
			if(table[x+1][y]==null)return;
			table[x][y]=table[x+1][y];
			table[x+1][y]=changeblock;
			break;
		case 3:
			if(y>num_block_vert-2)return;
			if(table[x][y+1]==null)return;
			table[x][y]=table[x][y+1];
			table[x][y+1]=changeblock;
			break;
		}
		
		//Block b= findBlock(x,y);
	}
}
