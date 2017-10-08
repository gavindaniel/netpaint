package controller_view;
//Take Home Quiz: Client/Server
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.PaintObject;

public class Server {
	
	private static PaintObject inputFromClient;
	
	public static void main(String[] args) {
		try {
//			System.out.println("Server started");
			ServerSocket server = new ServerSocket(4000);
			Socket connection = server.accept();
			
//Make both connection steams available
			ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
//Do some I/O.
			Boolean flag = false;
			
			while ( !flag ){
				connection = server.accept();
				inputFromClient = (PaintObject) input.readObject();
				ClientHandler clientHandler = new ClientHandler(inputFromClient);
				Thread t = new Thread(clientHandler);
				t.start();
				/*
				 	messageFromClient = (String) input.readObject();
					System.out.println("Server read: " + messageFromClient);
					output.writeObject("Hi client, you wrote: " + messageFromClient);
					messageFromClient = messageFromClient.toUpperCase().replace(" ", "");
				*/
//				if (messageFromClient.equals("QUIT")) flag = true;
			}
//Close the connection
			connection.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static class ClientHandler implements Runnable {

		public ClientHandler(PaintObject po){
			
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
}

