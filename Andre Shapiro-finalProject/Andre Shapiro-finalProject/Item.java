import java.io.Serializable;
import java.awt.Graphics;
import java.awt.Color;
 
public class Item implements Serializable
{
    int x;
    int y;
    int width;
    int height;
     
    boolean visible;
     
    public Item(int x, int y)
    {
         
        this.x= x;
        this.y= y;
		
        this.width = 100;
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
			g.setColor(Color.green);
			g.fillOval(x, y, 20, 20);
        }
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
}
