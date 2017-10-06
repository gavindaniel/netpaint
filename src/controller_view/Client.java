package controller_view;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
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
	private GraphicsContext gc;
	private Vector<PaintObject> allPaintObjects;
	private int currClick;
	private boolean objDone;
	private PaintObject currObj;
//	private Point p1;
 //   private Point p2;
	  
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
	BorderPane all = new BorderPane();
	
	gridPane = createGridPane();
	canvas = createCanvas();
	allPaintObjects = createVectorOfPaintObjects();
	objDone = true;
	currClick = 1;
	
	all.setCenter(canvas);
	all.setBottom(gridPane);
	allPaintObjects = createVectorOfPaintObjects();
    drawAllPaintObects(allPaintObjects, canvas);

 //   all.setCenter(new Label("Just a start"));
 //   all.setTop(new Label("top"));
 //   all.setBottom(new Label("bottom"));
    Scene scene = new Scene(all, 800, 650);

    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  
  private GridPane createGridPane() {
	    // TODO 1: Construct 2 RadioButton objects
	    RadioButton line = new RadioButton("Line");
	    RadioButton rect = new RadioButton("Rectangle");
	    RadioButton oval = new RadioButton("Oval");
	    RadioButton pic = new RadioButton("Picture");
	    
	    // TODO 2: Construct a ToggleGroup so only one can be selected
	    // and add the radio buttons to it
		  group = new ToggleGroup();
		  
		  line.setUserData("Line");
		  line.setToggleGroup(group);
		  rect.setUserData("Rectangle");
		  rect.setToggleGroup(group);
		  oval.setUserData("Oval");
		  oval.setToggleGroup(group);
		  pic.setUserData("Picture");
		  pic.setToggleGroup(group);
		  
		  line.setSelected(true);
	    
	    GridPane gridPane = new GridPane();
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    gridPane.setAlignment(Pos.CENTER);

	    gridPane.add(line, 0, 0);
	    gridPane.add(rect, 1, 0);
	    gridPane.add(oval, 2, 0);
	    gridPane.add(pic,  3, 0);

	    // TODO 3: Add the ColorPicker that will be needed when the color is changed
	    colorPicker = new ColorPicker();
	    colorPicker.setUserData("Color");
	    gridPane.add(colorPicker, 4, 0);

	    return gridPane;
	  }
  
	  private Canvas createCanvas() {
			canvas = new Canvas(800, 550);
		      
			canvas.setOnMouseClicked(new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent event) {
			      
				      double xLoc = event.getX();
				      double yLoc = event.getY();	      
				      String s = group.getSelectedToggle().getUserData().toString();
				      Color c = colorPicker.getValue(); //.toString();
		//		      System.out.println(c);
				      if (currClick == 1){
				    	  Point p1 = new Point();
				    	  p1.setLocation(xLoc, yLoc);
				//    	  System.out.println("Point 1: " + p1.toString());
				      	currObj = createObject(s, p1, p1, c);
				      	objDone = false;
				      	currClick = 2;
		//		      	System.out.println("Point 1: " + p.toString());
				      }
				      else if (currClick == 2){

				    	  Point p2 = new Point();
				    	  p2.setLocation(xLoc, yLoc);
				    	  Point p1 = currObj.getPoint1();
				    	  //currObj.setPoint2(p2);
				    	  currObj.updatePoints(p1,p2);
				    	  objDone = true;
		//		    	  System.out.println("Point 2: " + p.toString());
				      }
				      if (objDone){
	//			    	  System.out.println("Point 1: " + p1.toString());
	//			    	  System.out.println("Point 2: " + p2.toString());
	//			    	  currObj = createObject(s,p1,p2,c);
				    	  allPaintObjects.add(currObj);
				    	  objDone = false;
				    	  currClick = 1;
//				    	  System.out.println(allPaintObjects.size());
				      }
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
	  // TODO 4: Show the event handler that changes the Background color
	  // to the selected color.  Use setStyle with CSS style coding.
	  private class ColorChanger implements EventHandler<ActionEvent> {

	    @Override
	    public void handle(ActionEvent event) {
	      
	      
	    }
	  }
	  
	  private void drawAllPaintObects(Vector<PaintObject> allPaintObjects, Canvas canvas) {
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
			// TODO Auto-generated method stub
			drawAllPaintObects(allPaintObjects, canvas);
		}
}
