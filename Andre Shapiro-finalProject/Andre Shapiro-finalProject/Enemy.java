import java.io.Serializable;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
 
public class Enemy implements Serializable, Runnable
{
    int x;
    int y;
    int width;
    int height;
	private BufferedImage enemyPic;
	String direction;
     
    boolean visible;
     
    public Enemy(int x, int y)
    {
         
        this.x= x;
        this.y= y;
		
		direction="right";
		
        this.width = 75;
        this.height = 75;
         
        this.visible = true;
    }
	
	public void right()
	{
		x++;
	}
	
	public void left()
	{
		x--;
	}
 
    public void drawMe(Graphics g)
    {
        if( visible )
        {
			g.setColor(Color.red);
			g.fillOval(x, y, 20, 20);
        }
    }
	
	public String direction()
	{
		return direction;
	}
     
	public void setDirection(String s)
	{
		direction=s;
	}
	public int getX()
    {
        return this.x;
    }
     
    public int getY()
    {
        return this.y;
    }
     
    public int getWidth()
    {
        return width;
    }
     
    public int getHeight()
    {
        return height;
    }
	  public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
	 public boolean getVisible()
    {
        return visible;
    }
	 public void moveTo(int x, int y)
    {
		this.x = x;
		this.y = y;
    }
	public void reset()
	{
		visible=true;
	}
	public void run(){
		boolean upOrDown = true;
		while(true){
			if(getX() < 0){
				upOrDown = true;
			}
			else if(getX()> 650){
				upOrDown = false;
			}
			if(upOrDown == true){
				x+= 5;
			}
			else{
				x-=5;
			}
		}
	}
}
