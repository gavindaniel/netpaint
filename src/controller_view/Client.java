package controller_view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
  * A GUI for Netpaint that has all paint objects drawn upon a Canvas.
  * This file also represents the controller as it controls how paint objects
  * are drawn and sends new paint objects to the server. All Client objects
  * also listen to the server to read the Vector of PaintObjects and
  * repaint every time any client adds a new one. 
  * 
  * @author Gavin Daniel
  * 
 */
public class Client extends Application {

	
	private BorderPane window;
	  
	
	public static void main(String[] args) {
		launch(args);
		
		// from quiz
/*
		try {
			// Connect to a Server and get the two streams from the server
//						System.out.println("Client started");
						Socket server = new Socket("localhost", 4000);
			// Do some I/O with the server
						ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
						ObjectInputStream input = new ObjectInputStream(server.getInputStream());
			// Do some I/O with the server
						Boolean flag = false;
						String userInput = "";
//						String upperInput = "";
						while ( !flag ){
							System.out.print("Enter a message: ");
							Scanner keyboard = new Scanner(System.in);
							userInput = keyboard.nextLine();
							output.writeObject(userInput);
							String messageFromServer = (String) input.readObject();
							System.out.println(messageFromServer);	
							userInput = userInput.replaceAll(" ", "");
							if (userInput.toUpperCase().equals("QUIT")) {
								flag = true;
								keyboard.close();
							}
						}
						System.out.println("You entered the magic word");
			// Close the connection to the server
						server.close();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
*/
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// initialize objects and variables
		primaryStage.setTitle("NetPaint");
		window = new BorderPane();
		Scene scene = new Scene(window, 800, 650);
		new View(window);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
