package controller_view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import java.awt.Color;
import javafx.stage.Stage;
import model.ColorTypeConverter;
import model.PaintObject;
import controller_view.Server;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

/**
 * A GUI for Netpaint that has all paint objects drawn upon a Canvas. This file
 * also represents the controller as it controls how paint objects are drawn and
 * sends new paint objects to the server. All Client objects also listen to the
 * server to read the Vector of PaintObjects and repaint every time any client
 * adds a new one.
 * 
 * @author Gavin Daniel
 * 
 */
public class Client extends Application {

	private BorderPane window;
	private static final String Address = "localhost";
	private Socket socket;
	private ObjectOutputStream outputToServer;
	private ObjectInputStream inputFromServer;
	// graphical view of list of allPaintObjects
	private View mainView;
	// private Vector<PaintObject> allPaintObjects;

	// sentMessages is the model, or the list of messages to be shown in messages
	private static ObservableList<PaintObject> sentPaintObjects;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Set up this app to connect to a server
		openConnection();

		// initialize objects and variables
		primaryStage.setTitle("NetPaint");
		window = new BorderPane();
		Scene scene = new Scene(window, 800, 650);

		mainView = new View();
		mainView.getCanvas().setOnMouseMoved(new MouseMoveListener());
		mainView.getCanvas().setOnMouseClicked(new MouseClickListener());

		Vector<PaintObject> allPaintObjects = new Vector<PaintObject>();
		sentPaintObjects = FXCollections.observableArrayList();
		// allPaintObjects.addAll(sentPaintObjects);
		mainView.setAllPaintObjects(allPaintObjects);
		mainView.drawAllPaintObjects(mainView.getAllPaintObjects(), mainView.getCanvas());
		window.setCenter(mainView.getCanvas());
		window.setBottom(mainView.getGridPane());

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void openConnection() {
		// Our server is on our computer, but make sure to use the same port.
		try {
			socket = new Socket(Address, 4001);
			outputToServer = new ObjectOutputStream(socket.getOutputStream());
			inputFromServer = new ObjectInputStream(socket.getInputStream());

			// SeverListener will have a while(true) loop
			ServerListener listener = new ServerListener();
			// TODO 6: Start a new Thread that reads from the server
			// Note: Need setDaemon when started with a JavaFX App, or it crashes.
			Thread thread = new Thread(listener);
			thread.setDaemon(true);
			thread.start();

		} catch (IOException e) {
		}
	}

	private class MouseClickListener implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			// get location of mouse click
			double xLoc = event.getX();
			double yLoc = event.getY();
			PaintObject currObj = mainView.getCurrentObject();
			// get String of the selected RadioButton & Color of ColorPicker
			String s = mainView.getGroup().getSelectedToggle().getUserData().toString();
			javafx.scene.paint.Color fxColor = mainView.getColorPicker().getValue();
			java.awt.Color awtColor = ColorTypeConverter.Fx2Awt(fxColor);
			// check if this was the first click
			if (mainView.getCurrentClick() == 1) {
				// create Point 1
				Point p1 = new Point();
				p1.setLocation(xLoc, yLoc);
				// create Object with starting Point 1 & add to PaintObject Array
				currObj = mainView.createObject(s, p1, p1, awtColor);
				mainView.setCurrentObject(currObj);
				// update which click will be next
				mainView.setCurrentClick(2);
				System.out.println("Click 1");
			}
			// check if this was the second click
			else if (mainView.getCurrentClick() == 2) {
				// create Points 1 & 2
				Point p1 = currObj.getPoint1();
				Point p2 = new Point();
				p2.setLocation(xLoc, yLoc);
				// update the object's Points
				currObj.updatePoints(p1, p2);
				System.out.println("Click 2");
				// update which click will be next
				mainView.setCurrentClick(1);
				try {
					System.out.println("Object sending to Server : " + currObj);
					outputToServer.writeObject(currObj);
				} catch (IOException ioe) {
				}
			}
		}
	}

	private class MouseMoveListener implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			// get the location of the mouse
			double x = event.getX();
			double y = event.getY();
			PaintObject currObj = mainView.getCurrentObject();
			// check if there has already been one click
			if (mainView.getCurrentClick() == 2) {
				// create Points
				Point p1 = currObj.getPoint1();
				Point p2 = new Point();
				p2.setLocation(x, y);
				// update the current Object's Points
				currObj.updatePoints(p1, p2);
				// update the View
				mainView.setCurrentObject(currObj);
				mainView.drawGhostObject(mainView.getAllPaintObjects(), currObj, mainView.getCanvas());
				// draw the current object
			}
		}
	}

	// Listen for the Server to read a modified Vector<String>
	// Because JavaFX is not Thread-safe. This must be started in a new Thread,
	// or better now as a Task or Service from the javafx.concurrent Package.
	private class ServerListener extends Task<Object> {

		@Override
		public void run() {
			// TODO 7: Wait for writes from the server where readObject blocks.
			try {
				while (true) {
					Vector<PaintObject> newObject = (Vector<PaintObject>) inputFromServer.readObject();
					sentPaintObjects.setAll(newObject);
					Vector<PaintObject> all = new Vector<PaintObject>();
					for (int i = 0; i < sentPaintObjects.size(); i++) {
						all.add((PaintObject) sentPaintObjects.get(i));
					}
					mainView.setAllPaintObjects(all);
					mainView.drawAllPaintObjects(mainView.getAllPaintObjects(), mainView.getCanvas());
				}
			} catch (IOException ioe) {
			} catch (ClassNotFoundException cnfe) {
			}

		}

		@Override
		protected Object call() throws Exception {
			// Not using this call, but we need to override it to compile
			return null;
		}
	}

}
