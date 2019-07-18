import java.net.*;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Manager implements Runnable{
	public static int numClients = 0;
  private DLList<Player> playerList;
  private DLList<ServerThread> threadList;
  private DLList <Enemy> enemieList;
  private DLList<Boolean> directions;
  private DLList<Item> itemsList;
  private Game game;
  private String view;
  private int itemsCollected;
  private boolean hasStarted;
  
  private Pair<String, DLList<Player>> playerInfo;
  private Pair<String, DLList<Enemy>> enemyInfo;
  private Pair<String, DLList<Item>> itemInfo;

	public Manager(Game game){
		this.threadList = new DLList<ServerThread>();
		this.playerList = new DLList<Player>();
		enemieList = new DLList<Enemy>();
		directions = new DLList<Boolean>();
		itemsList = new DLList<Item>();
		this.game = game;
		itemsCollected =0;
		hasStarted = false;
		view = "start";
		playerInfo = new Pair<String,DLList<Player>>("players", playerList );
		enemyInfo = new Pair<String,DLList<Enemy>>("enemy", enemieList );
		itemInfo = new Pair<String,DLList<Item>>("items", itemsList );
		
		
	}
	public synchronized void addThread(ServerThread st){
		this.threadList.add(st);
		numClients++;
	}
	/*public void addPlayer(Player p){
		this.playerList.add(p);
	}*/
	public synchronized int addPlayer(Player p){
		playerList.add(p);
		return playerList.size()-1;
	}
	public synchronized void addEnemy(Enemy e){
		enemieList.add(e);
		directions.add(true);

			broadcastEnemy();
			//System.out.println("add enemy");
	}
	public synchronized void addItem(Item i){
		itemsList.add(i);
		if(itemsList.size()>=3){
			broadcastItem();
		}
	}
	public synchronized void setLevel(String s){
		view = s;
		for( int i = 0; i < this.threadList.size(); i++ ){
			if(threadList.get(i) != null){
				threadList.get(i).sendLevel(s);
			}
			
		}
	}
	
	
	
	
	
	public void removeThread(ServerThread st){
		this.threadList.remove(st);
	}
	/*public synchronized void broadcast(Player player, int ID){
		playerList.set(ID, player);
		//playerInfo.set("players", playerList);
		for( int i = 0; i < this.threadList.size(); i++ ){
			if(threadList.get(i) != null){
				threadList.get(i).sendP2(playerList);
			}
			
		}
	}*/
	public synchronized void broadcast(Player player, int ID){
		playerList.set(ID, player);
		playerInfo.set("players", playerList);
		for( int i = 0; i < this.threadList.size(); i++ ){
			if(threadList.get(i) != null){
				threadList.get(i).sendP(playerInfo);
			}
			
		}
	}
	public synchronized void broadcastThreads(){
		for( int i = 0; i < this.threadList.size(); i++ ){
			if(threadList.get(i) != null){
				threadList.get(i).setStarted(hasStarted);
			}
			
		}
	}
	public synchronized void broadcastEnemy(){
		enemyInfo.set("enemy", enemieList);
		for( int i = 0; i < this.threadList.size(); i++ ){
			if(threadList.get(i) != null){
				threadList.get(i).sendE(enemyInfo);
				//System.out.println("sending info  number of enemys is " + enemieList.size());
			}
			
		}
	}
	public synchronized void broadcastItem(){
		itemInfo.set("items", itemsList);
		for( int i = 0; i < this.threadList.size(); i++ ){
			if(threadList.get(i) != null){
				threadList.get(i).sendI(itemInfo);
			}
			
		}
	}
	public synchronized void broadcastTasks(){
		for( int i = 0; i < this.threadList.size(); i++ ){
			if(threadList.get(i) != null){
				threadList.get(i).sendTasks(itemsCollected);
			}
			
		}
	}
	public boolean hasStarted(){
		return hasStarted;
	}
	public String level(){
		return view;
	}
	public void playSound(){
		try{
			URL url = this.getClass().getClassLoader().getResource("Sound/smw_coin.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(url));
			clip.start();
		 }
		 catch (Exception exc)
		 {
			 exc.printStackTrace(System.out);
		 }
	}
	public void playSound2(){
		try{
			URL url = this.getClass().getClassLoader().getResource("Sound/smw_lost_a_life.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(url));
			clip.start();
		 }
		 catch (Exception exc)
		 {
			 exc.printStackTrace(System.out);
		 }
	}
	public void playSound3(){
		try{
			URL url = this.getClass().getClassLoader().getResource("Sound/ilikeit.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(url));
			clip.start();
		 }
		 catch (Exception exc)
		 {
			 exc.printStackTrace(System.out);
		 }
	}
	public synchronized void remove(int id) {
		playerList.set(id, null);
		threadList.set(id, null);
		directions.set(id, null);
  }
  public void lvl1(){
    enemieList=new DLList<Enemy>();
	itemsList=new DLList<Item>();
	directions=new DLList<Boolean>();
	if(!hasStarted)
	{
		hasStarted=true;
		broadcastThreads();
		playSound3();
	}
	Enemy temp=new Enemy(700, 500);
	Enemy temp2=new Enemy(300, 150);
	Enemy temp3=new Enemy(700, 350);
	for(int i=0;i<playerList.size() * 3;i++){
		int x=(int)(Math.random()*700)+1;
		int y=(int)(Math.random()*500)+1;
		Item item1=new Item(x, y);
		this.addItem(item1);
	}
	

	this.addEnemy(temp);
	this.addEnemy(temp2);
	this.addEnemy(temp3);
	itemsCollected=0;
	broadcastEnemy();
	broadcastTasks();
	broadcastItem();
	//System.out.println("enemy size " + enemieList.size());
  }
  
   public void lvl2(){
    enemieList=new DLList<Enemy>();
	itemsList=new DLList<Item>();
	directions=new DLList<Boolean>();
	for(int i=0;i<playerList.size() * 3;i++){
		int x=(int)(Math.random()*700)+1;
		int y=(int)(Math.random()*500)+1;
		Enemy enemy = new Enemy(x, y);
		this.addEnemy(enemy);
	}
	for(int i=0;i<playerList.size() * 3;i++){
		int x=(int)(Math.random()*700)+1;
		int y=(int)(Math.random()*500)+1;
		Item item1=new Item(x, y);
		this.addItem(item1);
	}
	itemsCollected=0;
	broadcastTasks();
	broadcastEnemy();
	broadcastItem();
  }
  public void run(){
	  while(true){
		  try {
				Thread.sleep(10);
			} catch(InterruptedException ex) {
				return;
			}
			
			if(enemieList.size()>0){
				for(int i=0; i<enemieList.size(); i++){
					if(directions.get(i)){
						enemieList.get(i).right();
						if(enemieList.get(i).getX()>1300){
							directions.set(i, false);
							enemieList.get(i).setDirection("left");
							
						}
					}
					if(!directions.get(i)){
						enemieList.get(i).left();
						if(enemieList.get(i).getX()<-5){
							directions.set(i, true);
							enemieList.get(i).setDirection("right");
						}
					}
				}
			}
			  
			if(view.equals("1") ||view.equals("2")){
				if(enemieList.size()>0){	
					for(int i=0; i<enemieList.size(); i++){
						for(int j=0; j<playerList.size(); j++ ){
							if(playerList.get(j).checkCollision(enemieList.get(i))){
								playSound2();
								
								playerList.get(j).reset();
								
								broadcast(playerList.get(j), j);
							}
						}
					}
				}
				
				if(itemsList.size()>0){	
					for(int i=0; i<itemsList.size(); i++){
						for(int j=0; j<playerList.size(); j++ ){
							if(playerList.get(j).checkCollision(itemsList.get(i))){
								playSound();
								itemsCollected++;
								broadcastTasks();
								itemsList.get(i).setVisible(false);
								broadcastItem();
							}
						}
					}
				}
				
				if(itemsCollected>=playerList.size()*3){
					if(view.equals("1")){
						setLevel("2");
						lvl2();
						System.out.println("level 2");
					}
					else if(view.equals("2")){
						setLevel("end");
					}
				}
			}
		broadcastEnemy();
	  }
  }
	
	
}