package model;

import java.awt.Point;
import javafx.scene.paint.Color;
import model.PaintObject;

public class Rectangle extends PaintObject {
	public Rectangle (Color c, Point p1, Point p2){
		this.setColor(c);
		this.setOrigin(p1);
		this.updatePoints(p1, p2);
	}
}