package model;

import java.awt.Point;

import java.awt.Color;
import model.PaintObject;

public class Line extends PaintObject {
	public Line (Color c, Point p1, Point p2){
		this.setColor(c);
		this.setOrigin(p1);
		this.setPoint1(p1);
		this.setPoint2(p2);
	}
}
