import java.io.*;
import java.net.*;
import javax.swing.*; 
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ClientScreen extends JPanel implements ActionListener, KeyListener{
	private Player player;
	private String hostName;
	private int portNumber;
	//private Socket serverSocket;
	private BufferedReader in;
	private Thread readerThread;
	private MyReader myReader;
	private ObjectOutputStream outObj;
	private ObjectInputStream inObj;
	private PrintWriter out;
	private String view;
	private int ID, x,y;
	private DLList<Player> playersList;
	private DLList<Enemy> enemiesList;
	private DLList<Item> itemsList;
	private JButton startB, restartB;
	private int itemsColected;
	private BufferedImage itemPic, enemyPic, bakround, bakround2, playerpic;
	public ClientScreen(){
		this.setLayout(null);
		
		
		//player=new Player(25, 25);
		x=(int)(Math.random()*700)+1;
		y=(int)(Math.random()*500)+1;
		playersList = new DLList<Player>();
		enemiesList = new DLList<Enemy>();
		itemsList = new DLList<Item>();
		player=new Player(x, y);
		playersList.add(player);
		addKeyListener(this);
		setFocusable(true);
		myReader = new MyReader("User", out);
		readerThread = new Thread(myReader);
	   	view="start";
		itemsColected =0;
		
		//Buttons
		startB = new JButton("Start");
		startB.setBounds(600, 300, 100, 20);
        startB.addActionListener(this);
        this.add(startB);
		
		restartB = new JButton("restart");
		restartB.setBounds(600, 300, 100, 20);
        restartB.addActionListener(this);
        this.add(restartB);  
		
		try{
			bakround =ImageIO.read(new File("image/ocean.png"));
			enemyPic =ImageIO.read(new File("image/lol.png"));
			itemPic =ImageIO.read(new File("image/fishfood.png"));
			bakround2 =ImageIO.read(new File("image/kk.jpg"));
			playerpic =ImageIO.read(new File("image/plankton.png"));
        }catch(IOException e) {}
	}
	public Dimension getPreferredSize(){
        //Sets the size of the panel
        return new Dimension(1350,650);
    }
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//System.out.println("lolol" + playersList.size());
		if(view.equals("start")){
			startB.setVisible(true);
			restartB.setVisible(false);
			
			if(playersList!=null && playersList.size()>0){
				for(int i=0; i<playersList.size(); i++){
					if(playersList.get(i)!=null){
						int tempX1=playersList.get(i).getX();
						int tempY1=playersList.get(i).getY();
						g.drawImage(playerpic, tempX1,tempY1, 50,50,null);
					}
				}
				
				int tempX=playersList.get(ID).getX();
				int tempY=playersList.get(ID).getY();
				g.drawString("You are in ", tempX-10, tempY-10);
				
				g.drawString("players connected: "+playersList.size(), 600, 15);
				Font a = new Font ("Arial", Font.PLAIN, 30);
				g.drawString("Use the WASD keys to navegate the ocean and find the krusty krab recipe, you must evade the evil protectors of it", 400, 200);
				
			}
		}
		if(view.equals("1")){
			startB.setVisible(false);
			restartB.setVisible(false);
			g.drawImage(bakround,0,0,1350,650,null);
			Font a = new Font ("Arial", Font.PLAIN, 90);
			g.drawString("find all the kraby pattys or you will not be able to indulge in the secret formula", 400, 100);
			if(playersList!=null && playersList.size()>0){
				for(int i=0; i<playersList.size(); i++){
					if(playersList.get(i)!=null){
						int tempX1=playersList.get(i).getX();
						int tempY1=playersList.get(i).getY();
						g.drawImage(playerpic, tempX1,tempY1, 50,50,null);
					}
				}
				
				int tempX=playersList.get(ID).getX();
				int tempY=playersList.get(ID).getY();
				
				g.drawString("You are in ", tempX-10, tempY-10);
				g.drawString("players connected: "+playersList.size(), 600, 15);
				if(enemiesList!=null && enemiesList.size()>0){
					for(int i=0; i<enemiesList.size(); i++){
						int tempX1=enemiesList.get(i).getX();
						int tempY1=enemiesList.get(i).getY();
						g.drawImage(enemyPic, tempX1,tempY1, 50,50,null);
						//enemiesList.get(i).drawMe(g);
						//System.out.println("printing the enemy");
					}
				}
				if(itemsList!=null && itemsList.size()>0){
					for(int i=0; i<itemsList.size(); i++){
						if(itemsList.get(i).getVisible()){
							int tempX1=itemsList.get(i).getX();
							int tempY1=itemsList.get(i).getY();
							g.drawImage(itemPic, tempX1,tempY1, 50,50,null);
						//System.out.println("printing the items");
						}
					}
				}
			}
		}
		if(view.equals("2")){
			startB.setVisible(false);
			restartB.setVisible(false);
			g.drawImage(bakround,0,0,1350,650,null);
			Font a = new Font ("Arial", Font.PLAIN, 60);
			g.drawString("ONLY A FEW MORE!!! you must finish your mission and be able to find what you are trully looking for", 400, 100);
			if(playersList!=null && playersList.size()>0){
				for(int i=0; i<playersList.size(); i++){
					if(playersList.get(i)!=null){
						int tempX1=playersList.get(i).getX();
						int tempY1=playersList.get(i).getY();
						g.drawImage(playerpic, tempX1,tempY1, 50,50,null);
					}
				}
			}
				
				int tempX=playersList.get(ID).getX();
				int tempY=playersList.get(ID).getY();
				g.drawString("You are in ", tempX-10, tempY-10);
				g.drawString("players connected: "+playersList.size(), 600, 15);
				if(enemiesList!=null && enemiesList.size()>0){
					for(int i=0; i<enemiesList.size(); i++){
						int tempX1=enemiesList.get(i).getX();
						int tempY1=enemiesList.get(i).getY();
						g.drawImage(enemyPic, tempX1,tempY1, 50,50,null);
					}
				}
				if(itemsList!=null && itemsList.size()>0){
					for(int i=0; i<itemsList.size(); i++){
						if(itemsList.get(i).getVisible()){
							int tempX1=itemsList.get(i).getX();
							int tempY1=itemsList.get(i).getY();
							g.drawImage(itemPic, tempX1,tempY1, 50,50,null);
							//System.out.println("printing the items");
						}
					}
				}
		}
		if(view.equals("end")){
			startB.setVisible(false);
			restartB.setVisible(true);
			Font a = new Font ("Arial", Font.PLAIN, 90);
			g.drawString("you did it!!! now you will be able to have all thhe krabby pattys you want, and for FREE!!!", 400, 100);
		}

	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startB){
			try{
				outObj.reset();
				outObj.writeObject("1");
			}catch (IOException ex){
				System.out.println("Error listening for a connection");
				System.out.println(ex.getMessage());
			}	
			repaint();	
		}
		if(e.getSource()== restartB){
			try{
				outObj.reset();
				outObj.writeObject("start");
			}catch (IOException ex){
				System.out.println("Error listening for a connection");
				System.out.println(ex.getMessage());
			}	
			repaint();	
		}
	 
	}
	public void keyPressed(KeyEvent e){
		//System.out.println(e.getKeyCode() + "lololol");
		if(e.getKeyCode() == 80){
			//System.out.println("im pressing p the view is " + view );
			try{
				outObj.reset();
				if(view.equals("start")){
					outObj.writeObject("1");
					System.out.println("changing the view " + view);
				}
				else if(view.equals("1")){
					outObj.writeObject("2");
					System.out.println("changing the view " + view);
				}
				else if(view.equals("2")){
					outObj.writeObject("end");
					System.out.println("changing the view " + view);
				}
			}
			 catch (IOException ex){
				System.out.println("Error listening for a connection");
				System.out.println(ex.getMessage());
			}	
			//System.out.println(" final player's x "+ playersList.get(ID).getX());
			repaint(); 
		}
		else if (e.getKeyCode()==87){//W
			playersList.get(ID).moveUp(); 
			try{
				outObj.reset();
				outObj.writeObject(playersList.get(ID));
			}
			 catch (IOException ex){
				System.out.println("Error listening for a connection");
				System.out.println(ex.getMessage());
			}	
			repaint(); 
		}
		else if (e.getKeyCode()==83){//S
			playersList.get(ID).moveDown(); 
			try{
				outObj.reset();
				outObj.writeObject(playersList.get(ID));
			}
			 catch (IOException ex){
				System.out.println("Error listening for a connection");
				System.out.println(ex.getMessage());
			}	
			repaint(); 
		}else if (e.getKeyCode()==65){//A
			playersList.get(ID).moveLeft(); 
			try{
				outObj.reset();
				outObj.writeObject(playersList.get(ID));
			}
			 catch (IOException ex){
				System.out.println("Error listening for a connection");
				System.out.println(ex.getMessage());
			}	
			repaint(); 
		}else if (e.getKeyCode()==68){//D
			//System.out.println("player's x "+ playersList.get(ID).getX());
			playersList.get(ID).moveRight();
			//System.out.println("player's x "+ playersList.get(ID).getX());
			try{
				outObj.reset();
				outObj.writeObject(playersList.get(ID));
			}
			 catch (IOException ex){
				System.out.println("Error listening for a connection");
				System.out.println(ex.getMessage());
			}	
			//System.out.println(" final player's x "+ playersList.get(ID).getX());
			repaint(); 
		}
	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	
	@SuppressWarnings("unchecked")
	public void poll() throws IOException {
        hostName = "localhost";
        portNumber = 1024;
        Socket serverSocket = new Socket(hostName, portNumber);
        //out = new PrintWriter(serverSocket.getOutputStream(), true);
		inObj = new ObjectInputStream(serverSocket.getInputStream());
        //in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
		outObj = new ObjectOutputStream(serverSocket.getOutputStream());
        try {
			outObj.writeObject(player);
			ID=(int) inObj.readObject();
			//System.out.println("the ID is " + ID);
			while( true ){
				Object p= (Object) inObj.readObject();
				/*if(p instanceof DLList){
					//System.out.println("player's x "+ playersList.get(ID).getX());
					playersList = (DLList<Player>) p; 
					//System.out.println("player's x "+ playersList.get(ID).getX());
				}
				else if(p instanceof String){
					String msg = (String)p; 
					view=msg;
				}
				else if(p instanceof SLList){
					enemiesList=(SLList<Enemy>)p;
				}
				else if(p instanceof Integer){
					//tasks=(int)p;
				}
				repaint(); */
				if(p instanceof Pair){
					Pair myPair = (Pair)p;
					if(((String)(myPair.getKey())).equals("players")){
						playersList  = (DLList<Player>) myPair.getValue();
					}
					else if(((String)(myPair.getKey())).equals("items")){
						itemsList  = (DLList<Item>) myPair.getValue();
						System.out.println(itemsList.size() + "is items size");
					}
					else if(((String)(myPair.getKey())).equals("enemy")){
						enemiesList  = (DLList<Enemy>) myPair.getValue();
						//System.out.println(enemiesList.size() + "is enemy size");
						//System.out.println("view is " + view );
					}
				}
				else if(p instanceof String){
					String msg = (String)p; 
					view=msg;
					//System.out.println("the new view is " + msg);
				}
				else if(p instanceof Integer){
					itemsColected=(int)p;
				}
				repaint();
			}
          
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Error connecting to " + hostName);
			System.exit(1);
		} catch (ClassNotFoundException e) {
            System.err.println("Class does not exist" + e);
            System.exit(1);
        }
		
		
		
	}

	



}