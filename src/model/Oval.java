package model;

import java.awt.Point;
import javafx.scene.paint.Color;
import model.PaintObject;

public class Oval extends PaintObject {
	public Oval (Color c, Point p1, Point p2){
		this.setColor(c);
		this.setPoints(p1, p2);
	}
	
}
