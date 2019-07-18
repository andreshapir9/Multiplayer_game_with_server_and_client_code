import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.io.Serializable;

public class Player implements Serializable{
	private BufferedImage p1;
	private int x,y,height, width, score, level, lives;
	boolean visible = true;
	private DLList<Integer> life = new DLList<Integer>();
	public Player(int x, int y){
		this.x = x;
        this.y = y;
		lives = 3;
		width = 32;
		height = 32;
		level =1;

		//this.playerNumber = playerNumber;
		try{
			p1=ImageIO.read(new File("1.png"));
		}
		catch(IOException e){
			
		}
		for(int i=0;i<5;i++){
			life.add(1);
		}
	}
	public void drawMe(Graphics g){
		//g2d=(Graphics2D)g;
		//g.drawImage(p1,x*32,y*32, 32,32,null);
		if(visible == true)
		{
			g.setColor(Color.black);
			g.fillOval(x, y, 32, 32);
		}
	
	
	}
	public void addScore(){
		score++;
	}
	public int getScore(){
		return score;
	}
	public void subLife(){
		life.remove(0);
	}
	public int getLife(){
		return life.size();
	}
	public void updateLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void setX(int x)
	{
		this.x=x;
	}
	
	public void setY(int y)
	{
		this.y=y;
	}
    public int getX()
    {               
        return x;
    }
     
    public int getY()
    {
        return y;
    }
	public boolean equals(Player p)
	{
		if(this.toString().equals(p.toString()))
		{
			return true;
		}
		return false;
	}
	public int getLives()
	{
		return lives;
	}
	 
	public void reset()
	{
		visible = true;
		x = 100;
		y = 100;
	}
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	public int hashCode(){
		return 20*x+y;
	}
	public boolean checkCollision(Item e)
    {
        if( visible == true && e.getVisible() == true )
        {
            int eX = e.getX();
            int eY = e.getY();
            int eWidth = e.getWidth();
            int eHeight = e.getHeight();
            if( eX+eWidth >= x && eX <= x + width 
				&& eY+eHeight >= y && eY <= y + height || eX < 0)
            {
				return true;
            } 
        } 
		return false; 
         
    }
	public boolean checkCollision(Enemy e){
        if( visible == true && e.getVisible() == true ) {
            int eX = e.getX();
            int eY = e.getY();
            int eWidth = e.getWidth();
            int eHeight = e.getHeight();
             
			 if(e.getX()>50){
				  if( eX+eWidth >= x && eX <= x + width 
				&& eY+eHeight >= y && eY <= y + height || eX < 0){
				   // System.out.println("hit enemy and ship");
					//e.setVisible(false);
					//e.moveTo(700, 400);
					reset();
					return true;
				}
			 }
        }  
		return false; 
         
    }
	public void moveUp(){
		//if(visible == true){
			y -=5;
		//}
    }
    public void moveDown(){
		//if(visible == true){
			y +=5;
		//}
    }
	
	 public void moveLeft(){
		//if(visible == true){
			x -=5;
		//}
    }
    public void moveRight() {
		//if(visible == true){
			x +=5;
		//}
	
    }



}