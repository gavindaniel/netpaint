package quizzes;
// Take Home Quiz: Client/Server
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		try {
// Connect to a Server and get the two streams from the server
//			System.out.println("Client started");
			Socket server = new Socket("localhost", 4000);
// Do some I/O with the server
			ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(server.getInputStream());
// Do some I/O with the server
			String userInput = "";
			String upperInput = "";
			while ( !upperInput.equals("QUIT") ){
				System.out.print("Enter a message: ");
				Scanner sc = new Scanner(System.in);
				userInput = sc.nextLine();
				upperInput = userInput.toUpperCase();
				output.writeObject(userInput);
				String messageFromServer = (String) input.readObject();
				System.out.println(messageFromServer);
			}
			System.out.println("You entered the magic word");
// Close the connection to the server
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

