package org.freegame.attacktis;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.freegame.models.GameButton;
import org.freegame.models.block;
import org.freegame.models.cursor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class AtackTris extends Activity {
	
	public int game_status=-1;//status game:0 init game//1 game start//2 end game // 3 pausess
    public ArrayList<block> blocs;
    public ArrayList<block> figure;
    public ArrayList<block> casc=new ArrayList<block>();
    
    public block[][] table;//the grid for the blocs in the game
    public cursor point;
    public Random r;
    public MySurface mrender;
    GestureDetector detector;
    MotionEvent last_click;
    //BufferedImage paint,show,blac;
    public long move,init,time,paused,ttime,linextra;
    public long btime=120000;//couter timer of the game
    public int playerpoints=0,cascada=0;
    public int tmove=300;  //time to move blocks, this will decreasing with time
    public boolean flagc=false;
    
    ArrayList<GameButton> buttons;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		point=new cursor(this);
    	r=new Random();
		mrender=new MySurface(this);
		buttons=new ArrayList<GameButton>();
		setContentView(mrender);
    	detector=new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            	if(game_status==1)mrender.fling(event1,event2, velocityX,velocityY);
                return true;
            }
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
            	last_click=e;
            	return true;
            }
            @Override
            public boolean onDown(MotionEvent e) {
                  return true;
            }
        });
	}
  
    public void mainloop(){ //main loop of the game painter
            switch(game_status){
            	case -1:
            		mrender.paintLoading();
            		linextra=System.currentTimeMillis();
                    blocs=new ArrayList<block>();
                    figure=new ArrayList<block>();
                    ttime=System.currentTimeMillis();
                    GameButton start=new GameButton(mrender.sWidth/3,mrender.sHeight/3,mrender.sWidth/3,50);
            		start.text="Start Game";
            		start.ref_level=0;
            		start.setAction(new Runnable(){
						@Override
						public void run() {
							createinitialtable();
							game_status=1;
						}});
            		buttons.add(start);
            		
            		GameButton quit=new GameButton(mrender.sWidth/3,mrender.sHeight*2/3,mrender.sWidth/3,50);
            		quit.text="Exit";
            		quit.ref_level=0;
            		quit.setAction(new Runnable(){
						@Override
						public void run() {
							finish();
						}});
            		buttons.add(quit);
            		
            		GameButton resume=new GameButton(mrender.sWidth/3,mrender.sHeight/3,mrender.sWidth/3,50);
            		resume.text="Resume Game";
            		resume.ref_level=3;
            		resume.setAction(new Runnable(){
						@Override
						public void run() {
							game_status=1;
						}});
            		buttons.add(resume);
            		
            		GameButton restart=new GameButton(mrender.sWidth/3,mrender.sHeight/2,mrender.sWidth/3,50);
            		restart.text="Restart Game";
            		restart.ref_level=3;
            		restart.setAction(new Runnable(){
						@Override
						public void run() {
							Restart();
							game_status=1;
						}});
            		buttons.add(restart);
            		
            		GameButton quitlevel=new GameButton(mrender.sWidth/3,mrender.sHeight*2/3,mrender.sWidth/3,50);
            		quitlevel.text="Exit Game";
            		quitlevel.ref_level=3;
            		quitlevel.setAction(new Runnable(){
						@Override
						public void run() {
							Restart();
							game_status=0;
						}});
            		buttons.add(quitlevel);
            		
            		//TODO load images
                    game_status++;
            		break;
                case 0://begining MainMenu game
                	
                	mrender.paintMainMenu(buttons);
                    break;
                case 1://Paint Level Game playing
                    genblock();
                    movebloc();
                    mrender.paintGameLevel();               
                    time=System.currentTimeMillis()-init;
                    if(time>btime){
                        tmove-=50;//increasing speed of falling blocks
                        if(tmove<60)tmove=60;
                        btime-=2000;
                        if(btime<0){btime=500;}
                        init=System.currentTimeMillis();
                    }
                    break;
                case 2: //loose
                	mrender.paintend(buttons);
                    break;
                case 3:// pause
                	mrender.paintpaused(buttons);
                    break;
            }
            checkButtonClick(game_status);
    }
    /**
     * this method validates button from main menu
     * */
    private void checkButtonClick(int status){
    	if(last_click==null)return;
    	for(GameButton btn:buttons){
    		if(btn.ref_level!=status)continue;
	    	int cx=(int) last_click.getX();
	    	int cy=(int) last_click.getY();
	    	if(cx>btn.x&&cx<(btn.w+btn.x)){
				if(cy>btn.y&&cy<(btn.h+btn.y)){
					btn.onclick();
					Log.i("foundbutton","onclick: "+btn);
					break;
				}
			}
    	}
    	last_click=null;
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
     * method to remove a line of blocks from explosion 
     * */
  /*  public boolean lineexplode(int inex){
        boolean ex=true;
        for(int i=inex;i<17;i++){
            if(table[1][i]!=null){
                block b=table[1][i];
                if(b!=null){
                    for(int j=2;j<7;j++){
                        if(b.color!=table[j][i].color){ex=false;break;}
                    }
                }else{
                    ex=false;
                }
                if(ex)break;
            }
        }
        if(ex){
           for(int i=inex;i<16;i++){
            for(int j=1;j<8;j++){
                table[j][i]=null;
            }
           }
        }
        return ex;
    }*/
    /**
     * this method change the status of the machine state to state loose
     * */
    public void lose(){
        long timescore=System.currentTimeMillis()-ttime;
        playerpoints+=(int)timescore/60000;
        game_status=2;
    } 
    
    /**
     * this method generate the blocs at the begining
     * */
    private void createinitialtable(){
        table=new block[mrender.num_blocks+2][mrender.block_height+1];
      //this line to creates a invisible floor
        for(int i=0;i<mrender.num_blocks+2;i++)table[i][mrender.block_height]=new block(this);
        // this line creates a invisible wall at left
        for(int i=0;i<mrender.block_height+1;i++)table[0][i]=new block(this);
        // this line creates a invisible wall at righs
        for(int i=0;i<mrender.block_height+1;i++)table[mrender.num_blocks+1][i]=new block(this);
        move=System.currentTimeMillis();
        Log.i("aacktris", "initial table created");
    }
    /**
     * metod for create blocs when no one is falling
     * */
    private void genblock() {
        if(checbloc()){
            int nx=r.nextInt(mrender.num_blocks-1)+1; //create coordenate x:range(1:5)
            int nc=getColorWithRandom(r.nextInt(5)+1); //create color
            table[nx][0]=new block(nc,this);
            table[nx][0].color=nc;
            nc=getColorWithRandom(r.nextInt(5)+1);
            table[nx+1][0]=new block(nc,this);
            table[nx+1][0].color=nc;
        }
     }
     public void genbloctime(){
         int nx=r.nextInt(mrender.num_blocks-1)+1;
         int nc=getColorWithRandom(r.nextInt(5)+1);
         table[nx][0]=new block(nc,this);
         table[nx][0].color=nc;
         nc=getColorWithRandom(r.nextInt(5)+1);
         table[nx+1][0]=new block(nc,this);
         table[nx+1][0].color=nc;
     }
     private boolean checbloc(){///check if all the blocs are static
        for(int i=1;i<mrender.num_blocks+1;i++){
			  for(int j=mrender.block_height-1;j>=0;j--){  
				 if(table[i][j]!=null&&!table[i][j].isStatic&&!table[i][j].arrived){
					 return false;
				 }
			}
		} 
        return true;
     }
    private void movebloc(){
    	if(System.currentTimeMillis()-move>tmove){
    		for(int i=1;i<mrender.num_blocks+1;i++){
	    		for(int j=mrender.block_height-1;j>=0;j--){
	    			if(table[i][j]!=null)table[i][j].down(i, j);
				}
	    	}
	    	move=System.currentTimeMillis();
    	}
     }
    /* (non-Javadoc)
     * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	detector.onTouchEvent(event);
    	return super.onTouchEvent(event);
    }
    /*private void loadresources(){ //puttin image in a cache
	       BufferedImage im;
	        try{
	            paint=(BufferedImage)((JPanel)this.getContentPane()).createImage((int)(this.getContentPane().getWidth()),(int)(this.getContentPane().getHeight()) );
	            im=ImageIO.read(new File("res/red.png"));
	            cache.put("1", im);
	            im=ImageIO.read(new File("res/blue.png"));
	            cache.put("2", im);
	            im=ImageIO.read(new File("res/pink.png"));
	            cache.put("3", im);
	            im=ImageIO.read(new File("res/green.png"));
	            cache.put("4", im);
	            im=ImageIO.read(new File("res/yellow.png"));
	            cache.put("5", im);
	            im=ImageIO.read(new File("res/cursor.png"));
	            cache.put("cursor", im);
	            im=ImageIO.read(new File("res/cursors.png"));
	            cache.put("cursors", im);
	            im=ImageIO.read(new File("res/start.png"));
	            cache.put("start", im);
	            im=ImageIO.read(new File("res/bg.png"));
	            cache.put("bg", im);
	            im=ImageIO.read(new File("res/gameover.png"));
	            cache.put("go", im);
	        }catch(Exception e){
	            JOptionPane.showMessageDialog(null, "Cannot read a image file", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	*/
    
    /**
     * this method restart the current play
     * */
    private void Restart(){
        playerpoints=0;
        tmove=300;
        btime=120000;
    }
    /**
     * this method remove the sent block
     * */
    public void remove(int x, int y){//remover blocs
       table[x][y]=null;
    }
   /* public void checcross(int x, int y){
        figure.add(table[x][y]);
        block d=null;
       
        d=table[x][y-1];
        if(d!=null&&d.color==table[x][y].color)figure.add(d);
        d=table[x-1][y];
        if(d!=null&&d.color==table[x][y].color)figure.add(d);
        d=table[x+1][y];
        if(d!=null&&d.color==table[x][y].color)figure.add(d);
        d=table[x][y+1];
        if(d!=null&&d.color==table[x][y].color)figure.add(d);
        
        if(figure.size()==5){//destroy the 2 lines
             for(int i=1;i<7;i++){
               d=get(i,y);
                 if(d!=null)remove(d);
             }
             for(int i=1;i<16;i++){
               d=get(b.x,i);
                 if(d!=null)remove(d);
             }
             //System.out.println("removio cruz");
          //   JOptionPane.showMessageDialog(null, "Cruz explosion");
        }
        figure=new ArrayList<block>();
     }
*/
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
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
    	if(game_status==1)game_status=3;
    	else if(game_status==2)game_status=0;
    	else if(game_status==3)game_status=1;
    	else super.onBackPressed();
    }
    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
    	if(game_status==1)game_status=3;
    	super.onPause();
    }
}
