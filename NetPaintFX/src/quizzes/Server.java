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
			String messageFromClient = "";
			String upperClient = "";
			while ( !upperClient.equals("QUIT") ){
				messageFromClient = (String) input.readObject();
				upperClient = messageFromClient.toUpperCase();
				System.out.println("Server read: " + messageFromClient);
				output.writeObject("Hi client, you wrote: " + messageFromClient);
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
