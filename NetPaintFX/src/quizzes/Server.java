package quizzes;
//Take Home Quiz: Client/Server
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) {
		try {
//			System.out.println("Server started");
			ServerSocket server = new ServerSocket(4000);
			Socket connection = server.accept();
// Make both connection steams available
			ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
// Do some I/O.
			Boolean flag = false;
			String messageFromClient = "";
			while ( !flag ){
				messageFromClient = (String) input.readObject();
				System.out.println("Server read: " + messageFromClient);
				output.writeObject("Hi client, you wrote: " + messageFromClient);
				messageFromClient = messageFromClient.toUpperCase().replace(" ", "");
				if (messageFromClient.equals("QUIT")) flag = true;
			}
// Close the connection
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
