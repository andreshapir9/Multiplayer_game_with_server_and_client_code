import java.net.*;
import java.io.*;
public class ServerThread implements Runnable{
  private Socket clientSocket;
  private Manager manager;
  private PrintWriter out;
  private String hostName = "localhost";
  private Player player;
  private ObjectOutputStream outObj;
  private ObjectInputStream inObj;
  private Game game;
  private boolean started;
  private int ID;
  public ServerThread(Socket cs, Manager lcm, Game game){
    clientSocket = cs;
    manager = lcm;
	this.game = game;
	outObj=null;
	started=false;
  }
  public void sendMsg(String msg){
    if(out!= null){
      out.println(msg);
    }
  }
  public void sendP(Pair<String, DLList<Player>> list){
	try{
		outObj.reset();
		outObj.writeObject(list);
	}
	 catch (IOException ex){
		System.out.println("Error listening for a connection");
		System.out.println(ex.getMessage());
	}
  }
  public void sendP2(DLList<Player> list){
	try{
		outObj.reset();
		outObj.writeObject(list);
	}
	 catch (IOException ex){
		System.out.println("Error listening for a connection");
		System.out.println(ex.getMessage());
	}
  }
  public void sendE(Pair<String, DLList<Enemy>> list){
	try{
		outObj.reset();
		outObj.writeObject(list);
	}
	 catch (IOException ex){
		System.out.println("Error listening for a connection");
		System.out.println(ex.getMessage());
	}
  }
  public void sendI( Pair<String,DLList<Item>>list){
	try{
		outObj.reset();
		outObj.writeObject(list);
	}
	 catch (IOException ex){
		System.out.println("Error listening for a connection");
		System.out.println(ex.getMessage());
	}
  }
  public void sendTasks(int itemsCollected){
  try{
	outObj.reset();
	outObj.writeObject(itemsCollected);
  }
	 catch (IOException ex){
		System.out.println("Error listening for a connection");
		System.out.println(ex.getMessage());
	}
  }
  public void sendLevel(String level){
	try{
		outObj.reset();
		outObj.writeObject(level);
	}
	 catch (IOException ex){
		System.out.println("Error listening for a connection");
		System.out.println(ex.getMessage());
	}
  }
  public void setStarted(boolean started){
	  this.started = started;
  }
public void run(){
  		System.out.println(Thread.currentThread().getName() + ": connection opened.");
		try{
			outObj = new ObjectOutputStream(clientSocket.getOutputStream());
			inObj = new ObjectInputStream(clientSocket.getInputStream());

			  Player player = (Player) inObj.readObject(); 
			  ID=manager.addPlayer(player);
			  outObj.reset();
			  outObj.writeObject(ID);
			  manager.broadcast(player, ID);
			  

			
			while (true){
				Object p= (Object) inObj.readObject();
				//Pair myPair = (Pair) in.readObject();
				if(p instanceof Player){
					player = (Player) p; 
					manager.broadcast(player, ID);
				}
				else if(p instanceof String){
					String msg = (String)p; 
					manager.setLevel(msg);
		
					if(msg.equals("1")){
						manager.setLevel(msg);
						manager.lvl1();
							Thread t2= new Thread(manager);
							t2.start();
							System.out.println("lolol");
						if(!started){
							started=true;
						}
						manager.broadcastEnemy();
						//System.out.println("lololol");
					}
					if(msg.equals("2")){
						manager.setLevel(msg);
						manager.lvl2();
						Thread t2= new Thread(manager);
						t2.start();
						manager.broadcastEnemy();
					}
				}
            }
			
		} catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(1);
        }catch (ClassNotFoundException e) {
            System.err.println("Class does not exist" + e);
            System.exit(1);
        }
		System.out.println(Thread.currentThread().getName() + ": connection closed.");
		return;
  }
}