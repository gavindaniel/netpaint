package controller_view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import model.PaintObject;

public class Server {

	private static ServerSocket serverSocket;
	private static List<ObjectOutputStream> outputStreams = new Vector<>();
	private static Vector<PaintObject> allPaintObjects;
	
	public static void main(String[] args) throws IOException {
		serverSocket = new ServerSocket(4001);
		allPaintObjects = new Vector<PaintObject>();
		// System.out.println("Server started on port " + SERVER_PORT);

		// Setup the server to accept many clients
		while (true) {
			Socket socket = serverSocket.accept();
			ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream ouputToClient = new ObjectOutputStream(socket.getOutputStream());
      
			// TODO 1: Maintain a list of output streams so this Server can write to all
			outputStreams.add(ouputToClient);

			// System.out.println("Accepted a new connection from " + socket.getInetAddress());

			// Start the loop that reads any Client's writeObject in the background in a 
			// different Thread so this program can also wait for new Client connections.
			// This thread allows the Server to wait for each client's writing of a String message.
			// TODO 2: Start a new ClientHandler in a new Thread
			ClientHandler clientHandler = new ClientHandler(inputFromClient);
			Thread thread = new Thread(clientHandler);
			thread.start();
		}
	}

	private static class ClientHandler implements Runnable {

		private ObjectInputStream input;

		public ClientHandler(ObjectInputStream input) {
			this.input = input;
		}
 
		@Override
		public void run() {
			// TODO 3: Complete this run method with a while(true) loop
			// to read any new messages from the server. When a new read
			// happens, write the new message to all Clients
			while (true) {
				PaintObject newObject = null;
				try {
					newObject = (PaintObject) input.readObject();
					System.out.println("Object from Client : " + newObject);
					allPaintObjects.add(newObject);
				} catch (IOException ioe) {
					System.out.println("***IOException caught***");
				} catch (ClassNotFoundException cnfe) {
					System.out.println("***CNFException caught***");
				}
				writePaintObjectToClients(allPaintObjects);
			}
		}

		// TODO 4: Complete this message to write message to all output streams
		private void writePaintObjectToClients(Vector<PaintObject> newObject) {
			for (ObjectOutputStream stream : outputStreams ){
				try {
					stream.reset();
//					System.out.println("Vector Size sent to Clients : " + all.size());
					stream.writeObject(newObject);
				} catch (IOException ioe) {
				} 
			}
		}
	}
}