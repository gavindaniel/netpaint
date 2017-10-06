package controller_view;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javafx.application.Application;
//import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Line;
import model.Oval;
import model.PaintObject;
import model.Picture;
import model.Rectangle;

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
public class Client extends Application implements Observer {

	private ColorPicker colorPicker;
	private GridPane gridPane;
	private Canvas canvas;
	private ToggleGroup group;
	private Vector<PaintObject> allPaintObjects;
	private int currClick;
	private PaintObject currObj;
	  
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// initialize objects and variables
		BorderPane all = new BorderPane();
		gridPane = createGridPane();
		canvas = createCanvas();
		allPaintObjects = createVectorOfPaintObjects();
		currClick = 1;
		// add objects to BorderPane
		all.setCenter(canvas);
		all.setBottom(gridPane);
		drawAllPaintObects(allPaintObjects, canvas);
		// create the Scene with BorderPane
		Scene scene = new Scene(all, 800, 650);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
  
	private GridPane createGridPane() {
		// create RadioButtons, ColorPicker, Toggle Group, BorderPane
		GridPane gridPane = new GridPane();
		group = new ToggleGroup();
		RadioButton line = new RadioButton("Line");
	    RadioButton rect = new RadioButton("Rectangle");
	    RadioButton oval = new RadioButton("Oval");
	    RadioButton pic = new RadioButton("Picture");
	    colorPicker = new ColorPicker();
		// assign UserData for identification
		line.setUserData("Line");
		rect.setUserData("Rectangle");
		oval.setUserData("Oval");
		pic.setUserData("Picture");
		colorPicker.setUserData("Color");
		// assign RadioButtons to group
		line.setToggleGroup(group);
		rect.setToggleGroup(group);
		oval.setToggleGroup(group);
		pic.setToggleGroup(group);
		// set starting Toggled RadioButton
		line.setSelected(true);
	    // create lower GridPane for RadioButtons
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    gridPane.setAlignment(Pos.CENTER);
	    // add RadioButtons to GridPane
	    gridPane.add(line, 0, 0);
	    gridPane.add(rect, 1, 0);
	    gridPane.add(oval, 2, 0);
	    gridPane.add(pic,  3, 0);
	    gridPane.add(colorPicker, 4, 0);
	    // return the lower GridPane of Buttons
	    return gridPane;
	  }
  
	  private Canvas createCanvas() {
			canvas = new Canvas(800, 550);
			// mouse movement listener
			canvas.setOnMouseMoved(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					// get the location of the mouse 
					double x = event.getX();
					double y = event.getY();
					// check if there has already been one click
					if (currClick == 2){
						// create Points
						Point p1 = currObj.getPoint1();
						Point p2 = new Point();
						p2.setLocation(x, y);
						// udpate the current Object's Points
						currObj.updatePoints(p1, p2);
						allPaintObjects.set(allPaintObjects.size()-1, currObj);
						// draw the current object 
						drawAllPaintObects(allPaintObjects, canvas);
					}
				}	
			});
			// mouse click listener
			canvas.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {	
					// get location of mouse click
				    double xLoc = event.getX();
				    double yLoc = event.getY();	 
				    // get String of the selected RadioButton & Color of ColorPicker
				    String s = group.getSelectedToggle().getUserData().toString();
				    Color c = colorPicker.getValue(); 
				    // check if this was the first click
				    if (currClick == 1){
				    	// create Point 1
				    	Point p1 = new Point();
				    	p1.setLocation(xLoc, yLoc);
				    	// create Object with starting Point 1 & add to PaintObject Array
				    	currObj = createObject(s, p1, p1, c);
				    	allPaintObjects.add(currObj);
				    	// update which click will be next
				      	currClick = 2; 	
				    }
				    // check if this was the second click
				    else if (currClick == 2){
				    	// create Points 1 & 2
				    	Point p1 = currObj.getPoint1();
				    	Point p2 = new Point();
				    	p2.setLocation(xLoc, yLoc);
				    	// update the object's Points 
				    	currObj.updatePoints(p1,p2);
				    	allPaintObjects.set(allPaintObjects.size()-1, currObj);
				    	// update which click will be next 
				    	currClick = 1;
				    }
				    // update the canvas 
				    drawAllPaintObects(allPaintObjects, canvas);
				}
			});
			return canvas;
	}  
	public PaintObject createObject(String s, Point p1, Point p2, Color c){	
		PaintObject po = new Line(Color.WHITE, new Point(0,0), new Point(0,0));  
		switch (s){		
			case "Line":
				po = new Line(c, p1, p2);
				return po;
			case "Rectangle":
				po = new Rectangle(c, p1, p2);
				return po;
			case "Oval":
				po = new Oval(c, p1, p2);
				return po;
			case "Picture":
				po = new Picture(p1, p2, "doge.jpg");
				return po;
		}
		return po;
	}
	/*
	private class ColorChanger implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
 
		}
	}
	*/
	private void clearCanvas(Canvas canvas){
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, 800, 550);
	}
	private void drawAllPaintObects(Vector<PaintObject> allPaintObjects, Canvas canvas) {
		clearCanvas(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		for (PaintObject po : allPaintObjects)
			po.draw(gc);
	}
	private Vector<PaintObject> createVectorOfPaintObjects() {
		Vector<PaintObject> vectorPaintObjects = new Vector<>();
		return vectorPaintObjects;
	}
	@Override
	public void update(Observable o, Object arg) {
		drawAllPaintObects(allPaintObjects, canvas);
	}
}
