import java.net.*;
import java.io.*;

public class Server {
  public static void main(String[] args) throws IOException {
    int portNumber = 1024;
    ServerSocket serverSocket = null;
	Game game=new Game("start");
	Manager manager = new Manager(game);
	
	try{
		serverSocket= new ServerSocket(portNumber);
	}catch(IOException ioe){
		System.err.println("I/O error with server socket at port #"+portNumber);
	}
    {
      System.out.println("Waiting for a connection");
	  while(!manager.hasStarted()){
		  Socket clientSocket = null;
		  try{
			  
			 clientSocket = serverSocket.accept();
			 ServerThread st = new ServerThread(clientSocket, manager, game);
			 manager.addThread(st);
			 Thread thread = new Thread(st);
			 thread.start();
			} catch(IOException ioe){
				System.err.println("I/O error with client acceptance at port #"+portNumber);
			}
	  }
    }
  }
}
