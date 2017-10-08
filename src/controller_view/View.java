package controller_view;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import java.awt.Color;
import model.Line;
import model.Oval;
import model.PaintObject;
import model.Picture;
import model.Rectangle;

public class View extends BorderPane implements Observer {

	private GridPane gridPane;
	private Canvas canvas;
	private Vector<PaintObject> allPaintObjects;
	private ToggleGroup group;
	private ColorPicker colorPicker;
	private PaintObject currObj;
	private int currClick;
	
	
	public View(){
		gridPane = createGridPane();
		canvas = createCanvas();
		allPaintObjects = createVectorOfPaintObjects();
		currClick = 1;
//		window.setCenter(canvas);
//		window.setBottom(gridPane);
		drawAllPaintObjects(allPaintObjects, canvas);
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
			return new Canvas(800, 550);
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
				po = new Picture(p1, p2, "doge.jpeg");
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
	public void drawAllPaintObjects(Vector<PaintObject> allPaintObjects, Canvas canvas) {
		clearCanvas(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		System.out.println("drawAllObject vector size : " + allPaintObjects.size());
		for (PaintObject po : allPaintObjects)
			po.draw(gc);
		
	}
	public void drawGhostObject(Vector<PaintObject> allPaintObjects, PaintObject current, Canvas canvas) {
		
//		System.out.println("ghostObject vector size : " + allPaintObjects.size());
		if (allPaintObjects.size() > 0){
			drawAllPaintObjects(allPaintObjects, canvas);
		}
		else {
			clearCanvas(canvas);
		}
	//	drawAllPaintObjects(allPaintObjects, canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
	//	for (PaintObject po : allPaintObjects) {
	//		po.draw(gc);
	//	}
		current.draw(gc);
	}
	private Vector<PaintObject> createVectorOfPaintObjects() {
		Vector<PaintObject> vectorPaintObjects = new Vector<>();
		return vectorPaintObjects;
	}
	public Vector<PaintObject> getVectorOfPaintObjects() {
		return allPaintObjects;
	}
	@Override
	public void update(Observable o, Object arg) {
		drawAllPaintObjects(allPaintObjects, canvas);
	}
	
	public GridPane getGridPane() {
		return gridPane;
	}
	public void setGridPane(GridPane gp){
		gridPane = gp;
	}
	public ToggleGroup getGroup() {
		return group;
	}
	public void setGroup(ToggleGroup g) {
		group = g;
	}
	public ColorPicker getColorPicker() {
		return colorPicker;
	}
	public void setColorPicker(ColorPicker cp){
		colorPicker = cp;
	}
	public int getCurrentClick() {
		return currClick;
	}
	public void setCurrentClick(int c) {
		currClick = c;
	}
	public PaintObject getCurrentObject() {
		return currObj;
	}
	public void setCurrentObject(PaintObject current) {
		currObj = current;
	}
	public Vector<PaintObject> getAllPaintObjects() {
		return allPaintObjects;
	}
	public void setAllPaintObjects(Vector<PaintObject> v) {
		allPaintObjects = v;
	}
	public Canvas getCanvas() {
		return canvas;
	}
}
