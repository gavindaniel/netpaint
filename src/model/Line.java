package model;

import java.awt.Point;
import javafx.scene.paint.Color;
import model.PaintObject;

public class Line extends PaintObject {
	public Line (Color c, Point p1, Point p2){
		this.setColor(c);
		this.setPoint1(p1);
		this.setPoint2(p2);
	}
}
