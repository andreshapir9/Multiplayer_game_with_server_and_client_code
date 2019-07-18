import java.io.Serializable;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

 
public class Game implements Serializable
{   
    private String gamestate;
	private ArrayList<Enemy> enemies;

     
    public Game(String s)
    {
		gamestate=s;
    }
      
   
	public String level()
	{
		return gamestate;
	}
	
	public void setLvl(String s)
	{
		gamestate=s;
	}
	
	public void drawMe(Graphics g)
	{
		if(enemies.size()>0)
		{
			for(Enemy each: enemies)
			{
				each.drawMe(g);
			}
		}
	}
}